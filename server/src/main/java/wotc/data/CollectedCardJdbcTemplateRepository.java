package wotc.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import wotc.data.mappers.CollectedCardMapper;
import wotc.models.CollectedCard;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class CollectedCardJdbcTemplateRepository implements CollectedCardRepository {

    private final JdbcTemplate jdbcTemplate;

    public CollectedCardJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CollectedCard> findCollectedCardsByCollection(int collectionId) {
        final String sql = "select collected_card_id, card_id, collection_id, quantity, `condition`, in_use "
                + "from collected_card "
                + "where collection_id = ?;";
        return jdbcTemplate.query(sql, new CollectedCardMapper(), collectionId);
    }

    @Override
    public CollectedCard findByCardId(int collectedCardId) {
        final String sql = "select collected_card_id, card_id, collection_id, quantity, `condition`, in_use "
                + "from collected_card "
                + "where collected_card_id = ?;";

        return jdbcTemplate.query(sql, new CollectedCardMapper(), collectedCardId).stream()
                .findFirst().orElse(null);
    }


    @Override
    public CollectedCard addCollectedCard(CollectedCard collectedCard) {
        final String sql = "insert into collected_card (card_id, collection_id, quantity, `condition`, in_use) "
                + "values(?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, collectedCard.getCardId());
            ps.setInt(2, collectedCard.getCollectionId());
            ps.setInt(3, collectedCard.getQuantity());
            ps.setString(4, collectedCard.getCondition());
            ps.setBoolean(5, collectedCard.isInUse());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        collectedCard.setCollectedCardId(keyHolder.getKey().intValue());
        return collectedCard;
    }

    @Override
    public boolean editCollectedCard(CollectedCard collectedCard) {
        // Quantity, condition, and in_use can be updated
        final String sql = "update collected_card set "
                + "quantity = ?, "
                + "`condition` = ?, "
                + "in_use = ? "
                + "where collected_card_id = ?;";
        return jdbcTemplate.update(sql,
                collectedCard.getQuantity(),
                collectedCard.getCondition(),
                collectedCard.isInUse(),
                collectedCard.getCollectedCardId()) > 0;
    }

    @Override
    public boolean deleteCollectedCard(int collectedCardId) {

        return jdbcTemplate.update("delete from collected_card where collected_card_id = ?;", collectedCardId) > 0;
    }
}
