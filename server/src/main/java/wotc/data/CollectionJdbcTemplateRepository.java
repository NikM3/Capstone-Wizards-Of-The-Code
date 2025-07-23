package wotc.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import wotc.data.mappers.CollectionMapper;
import wotc.models.CollectedCard;
import wotc.models.Collection;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class CollectionJdbcTemplateRepository implements CollectionRepository {

    private final JdbcTemplate jdbcTemplate;

    public CollectionJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection findCollectionByUserId(int userId) {
        final String sql = "select collection_id, user_id, collection_name "
                + "from collection "
                + "where user_id = ?;";
        return jdbcTemplate.query(sql, new CollectionMapper(), userId).stream()
                .findFirst().orElse(null);
    }

    @Override
    public Collection findCollectionByUserEmail(String userEmail) {
        final String sql = "select c.collection_id, c.user_id, c.collection_name "
                + "from collection c"
                + "inner join user u on c.user_id = u.user_id"
                + "where u.email = ?;";
        return jdbcTemplate.query(sql, new CollectionMapper(), userEmail).stream()
                .findFirst().orElse(null);
    }

    @Override
    public Collection add(Collection collection) {
        final String sql = "insert into collection (user_id, collection_name) "
                + "values(?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, collection.getUserId());
            ps.setString(2, collection.getName());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        collection.setCollectionId(keyHolder.getKey().intValue());
        return collection;
    }

    @Override
    public boolean editCollection(Collection collection) {
        // The only thing that should be updated is the collection's name
        final String sql = "update collection set "
                + "collection_name = ? "
                + "where collection_id = ?;";

        return jdbcTemplate.update(sql,
                collection.getName(), collection.getCollectionId()) > 0;
    }

    @Override
    public boolean deleteById(int collectionId) {
        // Check if it is being used
        final String sql = "select count(*) "
                + "from collected_card "
                + "where collection_id = ?;";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, collectionId);

        if (count > 0) {
            return false;
        }
        return jdbcTemplate.update("delete from collection where collection_id = ?;", collectionId) > 0;
    }
}
