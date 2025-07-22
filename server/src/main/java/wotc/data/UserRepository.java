package wotc.data;

import wotc.models.User;

public interface UserRepository {
    User findByUsername(String username);
    User findByEmail(String email);
    User add(User user);
    boolean update(User user);
    boolean delete(int email);
    User updateRoles(User user);
}
