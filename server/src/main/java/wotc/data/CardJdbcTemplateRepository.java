package wotc.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wotc.data.mappers.CardMapper;
import wotc.models.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Repository
public class CardJdbcTemplateRepository implements CardRepository{

    private final JdbcTemplate jdbcTemplate;
    private final File directory;
    private final String filename = "scryfall-cards.JSON";

    public CardJdbcTemplateRepository(@Value("${cardDataFilePath:./data/scryfall/}") String directory, JdbcTemplate jdbcTemplate) {
        this.directory = new File(directory);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Card> findAll() {
        final String sql = "SELECT " +
            "c.card_id, " +
            "c.card_name, " +
            "c.mana_cost, " +
            "c.color_identity, " +
            "c.card_text, " +
            "c.set, " +
            "c.image_uri, " +
            "ct.card_type AS card_type, " +
            "r.rarity AS card_rarity " +
            "FROM card c " +
            "JOIN card_type ct ON c.card_type_id = ct.card_type_id " +
            "JOIN rarity r ON c.rarity_id = r.rarity_id " +
            "LIMIT 1000;";
        return jdbcTemplate.query(sql, new CardMapper());
    }

    @Override
    public Card findById(String cardId) {
        final String sql = "SELECT " +
                "c.card_id, " +
                "c.card_name, " +
                "c.mana_cost, " +
                "c.color_identity, " +
                "c.card_text, " +
                "c.set, " +
                "c.image_uri, " +
                "ct.card_type AS card_type, " +
                "r.rarity AS card_rarity " +
                "FROM card c " +
                "JOIN card_type ct ON c.card_type_id = ct.card_type_id " +
                "JOIN rarity r ON c.rarity_id = r.rarity_id " +
                "WHERE card_id = ?;";
        return jdbcTemplate.query(sql, new CardMapper(), cardId).stream().findFirst().orElse(null);
    }

    @Override
    public boolean insert(Card card) {
        final String sql = "INSERT INTO card (card_id, card_type_id, rarity_id, card_name, mana_cost, color_identity, `set`, image_uri) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(sql,
                card.getCardId(),
                card.getCardType().getId(),
                card.getCardRarity().getId(),
                card.getName(),
                card.getManaCost(),
                getColorIdentityString(card.getCardColors()),
                card.getCardSet(),
                card.getImageUri()
        ) > 0;
    }


    @Transactional
    @Override
    public boolean updateDatabase(List<Card> cards) {
        final String deleteCollectedSql = "DELETE FROM collected_card;";
        final String deleteSql = "DELETE FROM card;";
        final String insertSql = "INSERT INTO card ( " +
                "card_id, card_type_id, rarity_id, card_name, mana_cost, color_identity, card_text, `set`, image_uri" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        jdbcTemplate.update(deleteCollectedSql);
        jdbcTemplate.update(deleteSql);

        for (Card card : cards) {
            jdbcTemplate.update(insertSql,
                    card.getCardId(),
                    card.getCardType().getId(),
                    card.getCardRarity().getId(),
                    card.getName(),
                    card.getManaCost(),
                    getColorIdentityString(card.getCardColors()),
                    card.getCardText(),
                    card.getCardSet(),
                    card.getImageUri()
            );
        }
        return true;
    }

    private String getColorIdentityString(List<CardColor> colors) {
        if (colors == null || colors.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (CardColor color : colors) {
            sb.append(color.getAbbreviation());
        }
        return sb.toString();
    }

    @Transactional
    @Override
    public boolean runScryfallUpdate() throws Exception {
        boolean actionCompleted = false;

        // Get Scryfall's bulk data download links
        ScryfallBulkResponse scryfallBulkResponse = getDownloadLinks();

        // Get the Default as it supports multiple sets but maintains a smaller smile size through only having EN cards
        Optional<ScryfallBulkData> defaultCards = scryfallBulkResponse.getData().stream().filter(data ->
                data.getType().equals("default_cards")).findFirst();

        if (defaultCards.isPresent()) {
            String cardDataUrl = defaultCards.get().getDownload_uri();

            Path outputPath = Paths.get(directory + "/" + filename);

            // Attempt to download the bulk json file then populate local database
            downloadFile(cardDataUrl, outputPath);

            if(populateLocalDatabase()) {
                System.out.println("Database updated, please double check in MySQL Workbench");
                actionCompleted = true;
            } else {
                System.out.println("Something went wrong");
            }
        }

        // Delete the bulk download file once we're done with it
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (!file.isDirectory()) {
                file.delete();
            }
        }

        return actionCompleted;
    }

    private static ScryfallBulkResponse getDownloadLinks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.scryfall.com/bulk-data"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), ScryfallBulkResponse.class);
    }

    private static void downloadFile(String fileUrl, Path outputPath) throws IOException, InterruptedException {
        Files.createDirectories(outputPath.getParent());

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fileUrl))
                .build();

        HttpResponse<Path> response = client.send(request, HttpResponse.BodyHandlers.ofFile(outputPath));

        if (response.statusCode() == 200) {
            System.out.println("File saved to: " + response.body());
        } else {
            throw new IOException("Download failed with status: " + response.statusCode());
        }
    }

    private boolean populateLocalDatabase() throws IOException {
        final String sql = "insert into card (card_id, card_type_id, rarity_id, card_name, mana_cost, color_identity, card_text, `set`, image_uri) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "on duplicate key update " +
                "card_name = values(card_name), " +
                "mana_cost = values(mana_cost), " +
                "color_identity = values(color_identity), " +
                "card_text = values(card_text), " +
                "`set` = values(`set`), " +
                "image_uri = values(image_uri)";

        try {
            ObjectMapper mapper = new ObjectMapper();
            ArrayNode cardsJson = (ArrayNode) mapper.readTree(getJsonFromFile());

            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    JsonNode node = cardsJson.get(i);

                    Card card = makeCard(node, i);

                    ps.setString(1, card.getCardId());
                    ps.setInt(2, card.getCardType().getId());
                    ps.setInt(3, card.getCardRarity().getId());
                    ps.setString(4, card.getName());
                    ps.setString(5, card.getManaCost());
                    ps.setString(6, getColorIdentityString(card.getCardColors()));
                    ps.setString(7, card.getCardText());
                    ps.setString(8, card.getCardSet());
                    ps.setString(9, card.getImageUri());
                }

                @Override
                public int getBatchSize() {
                    return cardsJson.size();
                }
            });

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getJsonFromFile() throws IOException {
        Path path = Paths.get(directory.toString(), filename);
        return Files.readString(path, StandardCharsets.UTF_8);
    }

    private String parseText(JsonNode node, String key) {
        return node.hasNonNull(key) ? node.get(key).asText() : "";
    }

    private Card makeCard(JsonNode node, int index) throws SQLException {
        // cardId, setName, and rarity are always outside of card_faces and may be safely parsed
        String cardId = parseText(node, "id");
        String setName = parseText(node, "set_name");
        CardRarity rarity = CardRarity.findByName(parseText(node, "rarity"));

        // Remaining variables might be nested inside card_faces so double check if the initial is blank
        // Only worry about the front face, back faces will not be supported in our webapp
        String typeLine = parseText(node, "type_line");
        if (typeLine.isBlank() && node.has("card_faces")) {
            typeLine = parseText(node.get("card_faces").get(0), "type_line");
        }

        String cardName = parseText(node, "name");
        if (cardName.isBlank() && node.has("card_faces")) {
            cardName = parseText(node.get("card_faces").get(0), "name");
        }

        String manaCost = parseText(node, "mana_cost");
        if (manaCost.isBlank() && node.has("card_faces")) {
            manaCost = parseText(node.get("card_faces").get(0), "mana_cost");
        }

        String cardText = parseText(node, "oracle_text");
        if (cardText.isBlank() && node.has("card_faces")) {
            cardText = parseText(node.get("card_faces").get(0), "oracle_text");
        }

        // Make a list of CardColor, stored as an array in the JSON
        JsonNode colorsNode = node.get("color_identity");
        List<CardColor> colorIdentity = new ArrayList<>();
        if (colorsNode != null && colorsNode.isArray()) {
            for (JsonNode color : colorsNode) {
                try {
                    colorIdentity.add(CardColor.findByAbbreviation(color.asText()));
                } catch (IllegalArgumentException e) {
                    System.out.println("Could not find color value: " + color.asText());
                }
            }
        }

        // A card might not have an image
        String imageUrl = "https://files.mtg.wiki/thumb/Magic_card_back.jpg/429px-Magic_card_back.jpg";
        if (node.has("image_uris") && node.get("image_uris").has("normal")) {
            imageUrl = node.get("image_uris").get("normal").asText();
        }

        // Fail in a controlled manner if a required String is blank
        if (cardId.isBlank() || cardName.isBlank() || typeLine.isBlank() || setName.isBlank()) {
            throw new SQLException("Missing required fields for card at index " + index + "/n" + node.toPrettyString());
        }

        return new Card(cardId, cardName, manaCost, CardType.findByName(typeLine), colorIdentity, rarity, cardText, setName, imageUrl);
    }
}
