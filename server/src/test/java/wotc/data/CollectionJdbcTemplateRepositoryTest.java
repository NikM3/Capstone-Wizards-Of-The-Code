package wotc.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import wotc.models.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CollectionJdbcTemplateRepositoryTest {
    @Autowired
    private CollectionJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodSate;

    @BeforeEach
    void setup() {
        knownGoodSate.set();
    }

    @Test
    void shouldFindByUserId() {
        Collection myCollection = repository.findCollectionByUserId(2);
        assertEquals(1, myCollection.getCollectionId());
        assertEquals(2, myCollection.getUserId());
        assertEquals("test collection", myCollection.getName());
    }

    @Test
    void shouldNotFindCollectionWithNonExistentUser() {
        Collection myCollection = repository.findCollectionByUserId(1);
        assertNull(myCollection);
    }

    @Test
    void shouldAdd() {
        Collection collection = makeCollection();
        Collection actual = repository.add(collection);

        assertNotNull(actual);
        assertEquals(2, actual.getCollectionId());

        // null collection name
        collection.setName(null);
        actual = repository.add(collection);
        assertNotNull(actual);
        assertEquals(3, actual.getCollectionId());
    }

    @Test
    void shouldEdit() {
        Collection collection = repository.findCollectionByUserId(2);
        collection.setName("updated collection name");

        assertTrue(repository.editCollection(collection));
        assertEquals("updated collection name", repository.findCollectionByUserId(2).getName());
    }

    @Test
    void shouldDelete() {
        Collection collection = makeCollection();
        Collection actual = repository.add(collection);
        assertTrue(repository.deleteById(2));
        assertFalse(repository.deleteById(2));
    }

    @Test
    void shouldNotDeleteWithCollectionCardDependency() {
        assertFalse(repository.deleteById(1));
    }


    private Collection makeCollection() {
        Collection collection = new Collection();
        collection.setUserId(1);
        collection.setName("test collection 2");
        return collection;
    }
}