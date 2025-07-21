package wotc.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wotc.models.User;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserJdbcTemplateRepositoryTest {
    final static int NEXT_ID = 4;
    @Autowired
    UserJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindByUsername() {
        User user = repository.findByUsername("test user2");
        assertEquals(2, user.getUserId());
        assertEquals("test user2", user.getUsername());
        assertEquals("user2@test.com", user.getEmail());
    }

    @Test
    void shouldFindByEmail() {
        User user = repository.findByEmail("user2@test.com");
        assertEquals(2, user.getUserId());
        assertEquals("test user2", user.getUsername());
        assertEquals("user2@test.com", user.getEmail());
    }

    @Test
    void shouldAdd() {
        User user = new User(NEXT_ID, "walter", "walterwhite@mail.com", "5608-8007-b13d-5f6eacc2f176", false,null);
        User actual = repository.add(user);
        assertNotNull(actual);
        assertEquals(NEXT_ID, actual.getUserId());
        assertEquals("walter", actual.getUsername());
        assertEquals("walterwhite@mail.com", actual.getEmail());
    }

    @Test
    void shouldUpdate() {
        User user = new User(NEXT_ID, "george", "walterwhite@mail.com", "5608-8007-b13d-5f6eacc2f176", false,null);
        assertTrue(repository.update(user));
        user.setUserId(25);
        assertFalse(repository.update(user));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.delete(1));
        assertNull(repository.findByEmail("user1@test.com"));
    }

    @Test
    void shouldUpdateRoles() {
        // Pending
    }
}