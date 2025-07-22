package wotc.data.mappers;

import org.springframework.jdbc.core.RowMapper;
import wotc.models.CollectedCard;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CollectedCardMapper implements RowMapper<CollectedCard> {
    @Override
    public CollectedCard mapRow(ResultSet resultSet, int i) throws SQLException {
        CollectedCard collectedCard = new CollectedCard();
        collectedCard.setCollectedCardId(resultSet.getInt("collected_card_id"));
        collectedCard.setCardId(resultSet.getInt("card_id"));
        collectedCard.setCollectionId(resultSet.getInt("collection_id"));
        collectedCard.setQuantity(resultSet.getInt("quantity"));
        collectedCard.setCondition(resultSet.getString("condition"));
        collectedCard.setInUse(resultSet.getBoolean("in_use"));

        return collectedCard;
    }
}
