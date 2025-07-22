package wotc.data.mappers;

import org.springframework.jdbc.core.RowMapper;
import wotc.data.CardRepository;
import wotc.models.Card;
import wotc.models.CardColor;
import wotc.models.CardRarity;
import wotc.models.CardType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CardMapper implements RowMapper<Card> {

    @Override
    public Card mapRow(ResultSet rs, int i) throws SQLException {
        Card card = new Card();
        card.setCardId(rs.getInt("card_id"));

        // card_type_id
        String cardTypeStr = rs.getString("card_type");
        if (cardTypeStr != null){
            card.setCardType(CardType.findByName(cardTypeStr));
        }

        // rarity_id
        String rarityStr = rs.getString("card_rarity");
        if (rarityStr != null){
            card.setCardRarity(CardRarity.findByName(rarityStr));
        }

        card.setName(rs.getString("card_name"));
        card.setManaCost(rs.getString("mana_cost"));

        // color_identity
        String colorIdentity = rs.getString("color_identity");
        if (colorIdentity != null && !colorIdentity.isBlank()) {
            List<CardColor> colors = colorIdentity.toUpperCase()
                    .chars()
                    .mapToObj(c -> CardColor.findByAbbreviation(String.valueOf((char) c)))
                    .collect(Collectors.toList());
            card.setCardColors(colors);
        }

        card.setCardSet(rs.getString("set"));
        card.setImageUri(rs.getString("image_uri"));

        return card;
    }
}
