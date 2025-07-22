package wotc.domain;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wotc.data.UserRepository;
import wotc.models.User;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User findByUserName(String username) {
        return repository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Result<User> add(User user) {
        Result<User> result = validate(user);
        if (!result.isSuccess()) {
            return result;
        }

        if (user.getUserId() != 0) {
            result.addMessage("userId cannot be set for an `add` operation", ResultType.INVALID);
            return result;
        }

        user = repository.add(user);
        result.setPayload(user);
        return result;
    }

    public Result<User> delete(int userId) {
        Result<User> result = new Result<>();
        if (!repository.delete(userId)) {
            String msg = String.format("userId: %s, not found", userId);
            result.addMessage(msg, ResultType.NOT_FOUND);
            return result;
        }
        return result;
    }


    // User cannot be null
    // username cannot be null
    // email cannot be null
    // password cannot be null
    // role cannot be null
    private Result<User> validate(User user) {
        Result<User> result = new Result<>();
        if (user == null) {
            result.addMessage("user cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(user.getActualUsername())) {
            result.addMessage("username is required", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(user.getEmail())) {
            result.addMessage("email is required", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(user.getPassword())) {
            result.addMessage("password is required", ResultType.INVALID);
        }

        if (user.getRole() == null) {
            result.addMessage("role is required", ResultType.INVALID);
        }

        return result;
    }
}
