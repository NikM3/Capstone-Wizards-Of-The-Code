package wotc.data.mappers;

import org.springframework.jdbc.core.RowMapper;
import wotc.models.Role;
import wotc.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt("user_id"));
        user.setUsername(resultSet.getString("username"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password_hash_char"));
        user.setRestricted(resultSet.getBoolean("restricted"));
        user.setRole(Role.valueOf(resultSet.getString("role")));
        return user;
    }
}
