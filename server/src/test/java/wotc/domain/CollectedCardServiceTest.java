package wotc.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import wotc.data.CollectedCardRepository;
import wotc.data.CollectionRepository;
import wotc.models.CollectedCard;
import wotc.models.Collection;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CollectedCardServiceTest {
    @Autowired
    CollectedCardService service;

    @MockBean
    CollectedCardRepository repository;

    @Test
    void shouldFindCollectedCardByCollection() {
        List<CollectedCard> myCards = makeCollectedCards();
        when(repository.findCollectedCardsByCollection(1)).thenReturn(myCards);
        List<CollectedCard> actual = service.findCollectedCardsByCollection(1);

        assertEquals(myCards, actual);
    }

    @Test
    void shouldFindByCardId() {
        List<CollectedCard> myCards = makeCollectedCards();
        CollectedCard expected = myCards.get(0);
        when(repository.findByCardId(1)).thenReturn(expected);
        CollectedCard actual = service.findByCardId(1);

        assertEquals(expected, actual);
    }



    @Test
    void shouldAdd() {
        CollectedCard cardIn = new CollectedCard();
        cardIn.setCardId(1);
        cardIn.setCollectionId(1);
        cardIn.setQuantity(5);
        cardIn.setCondition("Lightly Played");
        cardIn.setInUse(false);
        CollectedCard cardOut = makeCollectedCards().get(0);

        when(repository.addCollectedCard(cardIn)).thenReturn(cardOut);

        Result<CollectedCard> result = service.addCollectedCard(cardIn);

        assertEquals(ResultType.SUCCESS, result.getType());
        assertEquals(cardOut, result.getPayload());
    }

    @Test
    void shouldAddWithoutCondition() {
        CollectedCard cardIn = new CollectedCard();
        cardIn.setCardId(1);
        cardIn.setCollectionId(1);
        cardIn.setQuantity(5);
        cardIn.setInUse(false);
        CollectedCard cardOut = makeCollectedCards().get(0);
        cardOut.setCondition("");

        when(repository.addCollectedCard(cardIn)).thenReturn(cardOut);

        Result<CollectedCard> result = service.addCollectedCard(cardIn);

        assertEquals(ResultType.SUCCESS, result.getType());
        assertEquals(cardOut, result.getPayload());
    }



    @Test
    void shouldNotAddNull() {
        Result<CollectedCard> result = service.addCollectedCard(null);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddWithoutCard() {
        CollectedCard cardIn = new CollectedCard();
        cardIn.setCollectionId(1);
        cardIn.setQuantity(5);
        cardIn.setCondition("Lightly Played");
        cardIn.setInUse(false);

        Result<CollectedCard> result = service.addCollectedCard(cardIn);
        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().get(0).contains("card_id cannot be null"));
    }

    @Test
    void shouldNotAddWithoutCollection() {
        CollectedCard cardIn = new CollectedCard();
        cardIn.setCardId(1);
        cardIn.setQuantity(5);
        cardIn.setCondition("Lightly Played");
        cardIn.setInUse(false);

        Result<CollectedCard> result = service.addCollectedCard(cardIn);
        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().get(0).contains("collection_id cannot be null"));
    }

    @Test
    void shouldNotAddWithoutQuantity() {
        CollectedCard cardIn = new CollectedCard();
        cardIn.setCardId(1);
        cardIn.setCollectionId(1);
        cardIn.setCondition("Lightly Played");
        cardIn.setInUse(false);

        Result<CollectedCard> result = service.addCollectedCard(cardIn);
        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().get(0).contains("Quantity cannot be less than or equal to 0"));
    }

    @Test
    void shouldNotAddWithCollectedCardId() {
        CollectedCard collectedCard = makeCollectedCards().get(0);

        Result<CollectedCard> result = service.addCollectedCard(collectedCard);
        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().get(0).contains("collectedCardId cannot be set for an `add` operation"));
    }

    @Test
    void shouldUpdate() {
        CollectedCard cardIn = makeCollectedCards().get(0);
        when(repository.editCollectedCard(cardIn)).thenReturn(true);

        Result<CollectedCard> result = service.editCollectedCard(cardIn);

        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotUpdateNull() {
        Result<CollectedCard> result = service.editCollectedCard(null);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateWithoutCard() {
        CollectedCard cardIn = new CollectedCard();
        cardIn.setCollectionId(1);
        cardIn.setQuantity(5);
        cardIn.setCondition("Lightly Played");
        cardIn.setInUse(false);

        Result<CollectedCard> result = service.editCollectedCard(cardIn);
        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().get(0).contains("card_id cannot be null"));
    }

    @Test
    void shouldNotUpdateWithoutCollection() {
        CollectedCard cardIn = new CollectedCard();
        cardIn.setCardId(1);
        cardIn.setQuantity(5);
        cardIn.setCondition("Lightly Played");
        cardIn.setInUse(false);

        Result<CollectedCard> result = service.editCollectedCard(cardIn);
        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().get(0).contains("collection_id cannot be null"));
    }

    @Test
    void shouldNotUpdateWithoutQuantity() {
        CollectedCard cardIn = new CollectedCard();
        cardIn.setCardId(1);
        cardIn.setCollectionId(1);
        cardIn.setCondition("Lightly Played");
        cardIn.setInUse(false);

        Result<CollectedCard> result = service.editCollectedCard(cardIn);
        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().get(0).contains("Quantity cannot be less than or equal to 0"));
    }

    @Test
    void shouldNotUpdateWithoutCollectedCardId() {
        CollectedCard collectedCard = makeCollectedCards().get(0);
        collectedCard.setCollectedCardId(0);

        Result<CollectedCard> result = service.editCollectedCard(collectedCard);
        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().get(0).contains("collectedCardId must be set for `update` operation"));
    }

    @Test
    void shouldNotUpdateNonExistentCollectedCard() {
        CollectedCard collectedCardIn = makeCollectedCards().get(0);
        collectedCardIn.setCollectedCardId(20);
        when(repository.editCollectedCard(collectedCardIn)).thenReturn(false);

        Result<CollectedCard> result = service.editCollectedCard(collectedCardIn);
        assertTrue(result.getMessages().get(0).contains(", not found"));
    }

    @Test
    void shouldDelete() {
        when(repository.deleteCollectedCard(1)).thenReturn(true);

        Result<CollectedCard> result = service.deleteCollectedCard(1);
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotDeleteNonExistentCollection() {
        when(repository.deleteCollectedCard(10)).thenReturn(false);

        Result<CollectedCard> result = service.deleteCollectedCard(10);
        assertTrue(result.getMessages().get(0).contains(", not found"));
    }



    List<CollectedCard> makeCollectedCards() {
        CollectedCard card1 = new CollectedCard(1, 1, 1, 5, "Lightly Played", false);
        CollectedCard card2 = new CollectedCard(2, 2, 1, 10, "Moderately Played", true);

        List<CollectedCard> myCards = new ArrayList<>();
        myCards.add(card1);
        myCards.add(card2);

        return myCards;
    }

}