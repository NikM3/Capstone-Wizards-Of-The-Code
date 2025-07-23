package wotc.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wotc.data.mappers.CardMapper;
import wotc.models.Card;
import wotc.models.CardColor;
import wotc.models.CardRarity;
import wotc.models.CardType;

import java.util.List;


@Repository
public class CardJdbcTemplateRepository implements CardRepository{

    private final JdbcTemplate jdbcTemplate;

    public CardJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Card> findAll() {
        final String sql = "SELECT " +
            "c.card_id, " +
            "c.card_name, " +
            "c.mana_cost, " +
            "c.color_identity, " +
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

    @Transactional
    @Override
    public boolean updateDatabase(List<Card> cards) {
        final String deleteCollectedSql = "DELETE FROM collected_card;";
        final String deleteSql = "DELETE FROM card;";
        final String insertSql = "INSERT INTO card ( " +
                "card_id, card_type_id, rarity_id, card_name, mana_cost, color_identity, `set`, image_uri" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        jdbcTemplate.update(deleteCollectedSql);
        jdbcTemplate.update(deleteSql);

        for (Card card : cards) {
            jdbcTemplate.update(insertSql,
                    card.getCardId(),
                    getCardTypeId(card.getCardType()),
                    getRarityId(card.getCardRarity()),
                    card.getName(),
                    card.getManaCost(),
                    getColorIdentityString(card.getCardColors()),
                    card.getCardSet(),
                    card.getImageUri()
            );
        }
        return true;
    }

    // Helper methods
    private int getCardTypeId(CardType type) {
        switch (type) {
            case ARTIFACT:
                return 1;
            case CREATURE:
                return 2;
            case ENCHANTMENT:
                return 3;
            case LAND:
                return 4;
            case INSTANT:
                return 5;
            case SORCERY:
                return 6;
            case BATTLE:
                return 7;
            default:
                throw new IllegalArgumentException("Unknown CardType: " + type);
        }
    }

    private int getRarityId(CardRarity rarity) {
        switch (rarity) {
            case COMMON:
                return 1;
            case UNCOMMON:
                return 2;
            case RARE:
                return 3;
            case MYTHIC:
                return 4;
            default:
                throw new IllegalArgumentException("Unknown CardRarity: " + rarity);
        }
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
}
