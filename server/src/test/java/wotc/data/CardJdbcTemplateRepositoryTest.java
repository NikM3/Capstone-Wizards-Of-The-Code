package wotc.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wotc.models.Card;
import wotc.models.CardColor;
import wotc.models.CardRarity;
import wotc.models.CardType;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CardJdbcTemplateRepositoryTest {

    @Autowired
    CardJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAllCards() {
        List<Card> cards = repository.findAll();
        assertNotNull(cards);
        assertTrue(cards.size() > 0);
    }

    @Test
    void shouldFindByChandrasOutrage() {
        Card card = repository.findById("1");
        assertNotNull(card);
        assertEquals("1", card.getCardId());
        assertEquals("Chandra's Outrage", card.getName());
        assertEquals("4", card.getManaCost());
    }

    @Test
    void shouldUpdateDatabaseWithNewCard() {
        Card card = new Card();
        card.setCardId("999");
        card.setName("Test Card");
        card.setManaCost("3");
        card.setCardType(CardType.INSTANT);
        card.setCardRarity(CardRarity.RARE);
        card.setCardColors(Arrays.asList(CardColor.RED, CardColor.BLUE));
        card.setCardSet("Test Set");
        card.setImageUri("test-uri");

        boolean result = repository.updateDatabase(List.of(card));
        assertTrue(result);

        List<Card> cards = repository.findAll();
        Card actual = cards.stream()
                .filter(c -> Objects.equals(c.getCardId(), "999"))
                .findFirst()
                .orElse(null);

        assertNotNull(actual);
        assertEquals("Test Card", actual.getName());
        assertEquals("3", actual.getManaCost());
        assertEquals(CardType.INSTANT, actual.getCardType());
        assertEquals(CardRarity.RARE, actual.getCardRarity());
        assertEquals("Test Set", actual.getCardSet());
        assertEquals("test-uri", actual.getImageUri());
        assertEquals(2, actual.getCardColors().size());
        assertTrue(actual.getCardColors().contains(CardColor.RED));
        assertTrue(actual.getCardColors().contains(CardColor.BLUE));
    }

    @Test
    void shouldRollbackOnInvalidCardData() {
        Card goodCard = new Card();
        goodCard.setCardId("999");
        goodCard.setName("Good Card");
        goodCard.setManaCost("3");
        goodCard.setCardType(CardType.CREATURE);
        goodCard.setCardRarity(CardRarity.COMMON);
        goodCard.setCardColors(List.of(CardColor.GREEN));
        goodCard.setCardSet("Good Set");
        goodCard.setImageUri("good-uri");

        Card badCard = new Card();
        badCard.setCardId("1000");
        badCard.setName("Bad Card");
        badCard.setManaCost("4");
        badCard.setCardType(null); // Invalid: will cause exception
        badCard.setCardRarity(CardRarity.UNCOMMON);
        badCard.setCardColors(List.of(CardColor.BLACK));
        badCard.setCardSet("Bad Set");
        badCard.setImageUri("bad-uri");

        assertThrows(RuntimeException.class,
                () -> repository.updateDatabase(List.of(goodCard, badCard)));

        // confirm nothing was inserted
        List<Card> cards = repository.findAll();
        assertTrue(cards.stream().noneMatch(c -> Objects.equals(c.getCardId(), "999")));
        assertTrue(cards.stream().noneMatch(c -> Objects.equals(c.getCardId(), "1000")));
    }

    /* This test takes two minutes to run, I do not advise uncommenting, but I'm not your boss */
//    @Test
//    void shouldUpdateFromScryfall() throws Exception {
//        assertTrue(repository.runScryfallUpdate());
//    }
}