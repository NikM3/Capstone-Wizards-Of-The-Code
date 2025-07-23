package wotc.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import wotc.data.CollectionRepository;
import wotc.models.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CollectionServiceTest {
    @Autowired
    CollectionService service;

    @MockitoBean
    CollectionRepository repository;

    @Test
    void shouldFindCollection() {
        Collection expected = makeCollection();
        when(repository.findCollectionByUser(1)).thenReturn(expected);
        Collection actual = service.findCollectionByUser(1);

        assertEquals(expected, actual);
    }

    @Test
    void shouldAdd() {
        Collection collectionIn = new Collection();
        collectionIn.setUserId(2);
        collectionIn.setName("test collection");
        Collection collectionOut = makeCollection();

        when(repository.add(collectionIn)).thenReturn(collectionOut);

        Result<Collection> result = service.add(collectionIn);

        assertEquals(ResultType.SUCCESS, result.getType());
        assertEquals(collectionOut, result.getPayload());
    }

    @Test
    void shouldAddWithoutCollectionName() {
        Collection collectionIn = new Collection();
        collectionIn.setUserId(2);
        Collection collectionOut = makeCollection();
        collectionOut.setName("");

        when(repository.add(collectionIn)).thenReturn(collectionOut);

        Result<Collection> result = service.add(collectionIn);

        assertEquals(ResultType.SUCCESS, result.getType());
        assertEquals(collectionOut.getName(), result.getPayload().getName());
    }

    @Test
    void shouldNotAddNull() {
        Result<Collection> result = service.add(null);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddWithoutUser() {
        Collection collection = new Collection();
        collection.setName("test collection");

        Result<Collection> result = service.add(collection);
        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().get(0).contains("user_id cannot be null"));
    }

    @Test
    void shouldNotAddWithId() {
        Collection collection = makeCollection();
        collection.setCollectionId(1);

        Result<Collection> result = service.add(collection);
        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().get(0).contains("collectionId cannot be set for an `add` operation"));
    }

    @Test
    void shouldUpdate() {
        Collection collectionIn = makeCollection();
        when(repository.editCollection(collectionIn)).thenReturn(true);

        Result<Collection> result = service.editCollection(collectionIn);

        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotUpdateNull() {
        Result<Collection> result = service.editCollection(null);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateWithoutUser() {
        Collection collection = makeCollection();
        collection.setUserId(0);

        Result<Collection> result = service.editCollection(collection);
        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().get(0).contains("user_id cannot be null"));
    }

    @Test
    void shouldNotUpdateWithoutId() {
        Collection collection = makeCollection();
        collection.setCollectionId(0);

        Result<Collection> result = service.editCollection(collection);
        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().get(0).contains("collectionId must be set for `update` operation"));
    }

    @Test
    void shouldNotUpdateNonExistentCollection() {
        Collection collectionIn = makeCollection();
        collectionIn.setCollectionId(20);
        when(repository.editCollection(collectionIn)).thenReturn(false);

        Result<Collection> result = service.editCollection(collectionIn);
        assertTrue(result.getMessages().get(0).contains(", not found"));
    }

    @Test
    void shouldDelete() {
        when(repository.deleteById(1)).thenReturn(true);

        Result<Collection> result = service.deleteById(1);
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotDeleteNonExistentCollection() {
        when(repository.deleteById(10)).thenReturn(false);

        Result<Collection> result = service.deleteById(10);
        assertTrue(result.getMessages().get(0).contains(", not found"));
    }


    Collection makeCollection() {
        return new Collection(1, 2, "test collection");
    }
}