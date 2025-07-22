package wotc.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wotc.models.CollectedCard;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CollectedCardJdbcTemplateRepositoryTest {
    @Autowired
    private CollectedCardJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindCollectedCardsByCollectionId() {
        List<CollectedCard> myCollection = repository.findCollectedCardsByCollection(1);

        assertNotNull(myCollection);
        assertTrue(myCollection.size() >= 4);
    }

    @Test
    void shouldNotFindCollectedCardsWithNonExistentCollection() {
        List<CollectedCard> myCollection = repository.findCollectedCardsByCollection(99);

        assertEquals(0, myCollection.size());
    }

    @Test
    void shouldFindCollectedCardById() {
        CollectedCard collectedCard = repository.findByCardId(4);

        assertEquals(4, collectedCard.getCollectedCardId());
        assertEquals(4, collectedCard.getCardId());
        assertEquals(1, collectedCard.getCollectionId());
        assertEquals(1, collectedCard.getQuantity());
        assertEquals("Near Mint", collectedCard.getCondition());
        assertTrue(collectedCard.isInUse());
    }

    @Test
    void shouldAdd() {
        CollectedCard collectedCard = makeCollectedCard();
        CollectedCard actual = repository.addCollectedCard(collectedCard);

        assertNotNull(actual);
        assertEquals(5, actual.getCollectedCardId());

        // null condition
        collectedCard.setCondition(null);
        actual = repository.addCollectedCard(collectedCard);
        assertNotNull(actual);
        assertEquals(6, actual.getCollectedCardId());
    }

    @Test
    void shouldEdit() {
        CollectedCard collectedCard = repository.findByCardId(1);
        collectedCard.setQuantity(2);
        collectedCard.setCondition("Heavily Played");
        collectedCard.setInUse(true);

        assertTrue(repository.editCollectedCard(collectedCard));

        CollectedCard updatedCard = repository.findByCardId(1);
        assertEquals(2, updatedCard.getQuantity());
        assertEquals("Heavily Played", updatedCard.getCondition());
        assertTrue(updatedCard.isInUse());
    }

    @Test
    void shouldDelete() {
        CollectedCard collectedCard = makeCollectedCard();
        CollectedCard actual = repository.addCollectedCard(collectedCard);
        assertTrue(repository.deleteCollectedCard(5));
        assertFalse(repository.deleteCollectedCard(5));
    }

    private CollectedCard makeCollectedCard() {
        CollectedCard collectedCard = new CollectedCard();
        collectedCard.setCardId(5);
        collectedCard.setCollectionId(1);
        collectedCard.setQuantity(1);
        collectedCard.setCondition("Damaged");
        collectedCard.setInUse(false);

        return collectedCard;
    }
}