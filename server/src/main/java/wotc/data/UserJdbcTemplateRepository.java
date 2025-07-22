package wotc.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import wotc.data.mappers.UserMapper;
import wotc.models.User;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class UserJdbcTemplateRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    ;
    @Override
    public User findByUsername(String username) {
        final String sql = "select user_id, username, email, password_hash_char, restricted "
                + "from `user` "
                + "where username = ? and restricted = 0;";
        User user = jdbcTemplate.query(sql, new UserMapper(), username).stream()
                .findFirst().orElse(null);
        if(user != null) {
            // Pending
            // addRoles(user);
        }
        return user;
    }

    @Override
    public User findByEmail(String email) {
        final String sql = "select user_id, username, email, password_hash_char, restricted "
                + "from `user` "
                + "where email = ? and restricted = 0;";
        User user = jdbcTemplate.query(sql, new UserMapper(), email).stream()
                .findFirst().orElse(null);
        if(user != null) {
            // Pending
            //addRoles(user);
        }
        return user;
    }

    @Override
    public User add(User user) {
        final String sql = "insert into `user` (username, email, password_hash_char, restricted) "
                + "values(?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setBoolean(4, user.isRestricted()); // check
            return ps;
        }, keyHolder);
        if (rowsAffected <= 0) {
            return null;
        }
        user.setUserId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public boolean update(User user) {
        final String sql = "update `user` set username = ? where user_id = ?;";

        return jdbcTemplate.update(sql, user.getUsername(), user.getUserId()) > 0;
    }

    @Override
    public boolean delete(int userId) {
        // "not restricted" flips the boolean value. Example: 1 (true) -> 0 (false)
        final String sql = "update `user` set restricted = not restricted where user_id = ?;";
        return jdbcTemplate.update(sql, userId) > 0;
    }

    @Override
    public User updateRoles(User user) {
        return null;
    }
}
