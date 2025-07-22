package wotc.data.mappers;

import wotc.models.Collection;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CollectionMapper implements RowMapper<Collection> {
    @Override
    public Collection mapRow(ResultSet resultSet, int i) throws SQLException {
        Collection collection = new Collection();
        collection.setCollectionId(resultSet.getInt("collection_id"));
        collection.setUserId(resultSet.getInt("user_id"));
        collection.setName(resultSet.getString("collection_name"));

        return collection;
    }
}
