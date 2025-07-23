package wotc.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import wotc.data.UserRepository;
import wotc.models.Collection;
import wotc.models.Role;
import wotc.models.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService service;

    @MockBean
    AuthenticationService authenticationService;

    @MockBean
    UserRepository repository;

    @Test
    void shouldFindAll() {
        User user = makeUser();
        List<User> expected = new ArrayList<>(List.of(user));
        when(repository.findAll()).thenReturn(expected);
        List<User> actual = service.findAll();
        assertEquals(expected.get(0), actual.get(0));
    }

    @Test
    void shouldFindUserByUserName() {
        User expected = makeUser();
        when(repository.findByUsername("Test user")).thenReturn(expected);
        User actual = service.findByUserName("Test user");

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindByEmail() {
        User expected = makeUser();
        when(repository.findByEmail("user@test.com")).thenReturn(expected);
        User actual = service.findByEmail("user@test.com");

        assertEquals(expected, actual);
    }

    @Test
    void shouldAddUser() {
        User userIn = makeUser();
        userIn.setUserId(0);
        User userOut = makeUser();

        when(repository.add(userIn)).thenReturn(userOut);

        Result<User> result = service.add(userIn);

        assertEquals(ResultType.SUCCESS, result.getType());
        assertEquals(userOut, result.getPayload());
    }

    @Test
    void shouldNotAddNull() {
        Result<User> result = service.add(null);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddWithoutUsername() {
        User user = makeUser();
        user.setUserId(0);
        user.setUsername("");
        Result<User> result = service.add(user);

        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().get(0).contains("username is required"));
    }

    @Test
    void shouldNotAddWithoutEmail() {
        User user = makeUser();
        user.setUserId(0);
        user.setEmail("");
        Result<User> result = service.add(user);

        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().get(0).contains("email is required"));
    }

    @Test
    void shouldNotAddWithoutPassword() {
        User user = makeUser();
        user.setUserId(0);
        user.setPassword("");
        Result<User> result = service.add(user);

        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().get(0).contains("password is required"));
    }

    @Test
    void shouldNotAddWithoutRole() {
        User user = makeUser();
        user.setUserId(0);
        user.setRole(null);
        Result<User> result = service.add(user);

        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().get(0).contains("role is required"));
    }

    @Test
    void shouldNotAddWithId() {
        User user = makeUser();

        Result<User> result = service.add(user);
        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().get(0).contains("userId cannot be set for an `add` operation"));
    }

    @Test
    void shouldUpdate() {
        User user = makeUser();
        when(repository.update(user)).thenReturn(true);

        Result<User> result = service.update(user);

        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotUpdateNull() {
        Result<User> result = service.update(null);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateWithoutUsername() {
        User user = makeUser();
        user.setUserId(0);
        user.setUsername("");
        Result<User> result = service.update(user);

        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().get(0).contains("username is required"));
    }

    @Test
    void shouldNotUpdateWithoutEmail() {
        User user = makeUser();
        user.setUserId(0);
        user.setEmail("");
        Result<User> result = service.update(user);

        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().get(0).contains("email is required"));
    }

    @Test
    void shouldNotUpdateWithoutPassword() {
        User user = makeUser();
        user.setUserId(0);
        user.setPassword("");
        Result<User> result = service.update(user);

        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().get(0).contains("password is required"));
    }

    @Test
    void shouldNotUpdateWithoutRole() {
        User user = makeUser();
        user.setUserId(0);
        user.setRole(null);
        Result<User> result = service.update(user);

        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().get(0).contains("role is required"));
    }

    @Test
    void shouldNotUpdateWithoutId() {
        User user = makeUser();
        user.setUserId(0);

        Result<User> result = service.update(user);
        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().get(0).contains("userId must be set for `update` operation"));
    }

    @Test
    void shouldNotUpdateNonExistentUser() {
        User user = makeUser();
        user.setUserId(20);
        when(repository.update(user)).thenReturn(false);

        Result<User> result = service.update(user);
        assertTrue(result.getMessages().get(0).contains(", not found"));
    }

    @Test
    void shouldDelete() {
        when(repository.delete(1)).thenReturn(true);

        Result<User> result = service.delete(1);
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotDeleteNonExistentUser() {
        when(repository.delete(10)).thenReturn(false);

        Result<User> result = service.delete(10);
        assertTrue(result.getMessages().get(0).contains(", not found"));
    }


    User makeUser() {
        return new User(1, "Test user", "user@test.com", "testPassword", false, Role.USER);
    }
}