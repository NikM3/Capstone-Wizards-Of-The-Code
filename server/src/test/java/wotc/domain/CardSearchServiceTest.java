package wotc.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;

import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import wotc.data.CardRepository;
import wotc.data.CardSearchRepository;
import wotc.models.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardSearchServiceTest {

    private CardRepository cardRepository;
    private CardSearchRepository cardSearchRepository;
    private ElasticsearchOperations elasticsearchOperations;
    private CardSearchService service;

    @BeforeEach
    void setUp() {
        cardRepository = mock(CardRepository.class);
        cardSearchRepository = mock(CardSearchRepository.class);
        elasticsearchOperations = mock(ElasticsearchOperations.class);
        service = new CardSearchService(cardRepository, cardSearchRepository, elasticsearchOperations);
    }

    @Test

    void fuzzySearch_returnsPagedCardsSortedByName() {
        // Arrange
        CardSearch cs = new CardSearch();
        cs.setId("card123");
        cs.setName("Lightning Bolt");
        cs.setType("Instant");

        SearchHit<CardSearch> mockHit = mock(SearchHit.class);
        when(mockHit.getContent()).thenReturn(cs);

        SearchHits<CardSearch> mockHits = mock(SearchHits.class);

        when(mockHits.getSearchHits()).thenReturn(List.of(mockHit));
        when(mockHits.getTotalHits()).thenReturn(1L);

        when(elasticsearchOperations.search(any(CriteriaQuery.class), eq(CardSearch.class)))
                .thenReturn(mockHits);

        Card fullCard = new Card();
        fullCard.setCardId("card123");
        fullCard.setName("Lightning Bolt");
        fullCard.setCardType(CardType.INSTANT);
        fullCard.setCardRarity(CardRarity.COMMON);

        when(cardRepository.findById("card123")).thenReturn(fullCard);

        // Act
        PagedResult<Card> result = service.fuzzySearch("light", 0, 10, "name", "asc");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(0, result.getPage());
        assertEquals(10, result.getSize());

        Card card = result.getContent().get(0);
        assertEquals("Lightning Bolt", card.getName());
        assertEquals(CardType.INSTANT, card.getCardType());
        assertEquals(CardRarity.COMMON, card.getCardRarity());
    }

    @Test
    void fuzzySearch_returnsMultipleCards() {
        // Arrange: create two mock CardSearch results
        CardSearch cs1 = new CardSearch();
        cs1.setId("card001");
        cs1.setName("Lightning Bolt");
        cs1.setType("Instant");

        CardSearch cs2 = new CardSearch();
        cs2.setId("card002");
        cs2.setName("Lightning Strike");
        cs2.setType("Instant");

        SearchHit<CardSearch> hit1 = mock(SearchHit.class);
        when(hit1.getContent()).thenReturn(cs1);

        SearchHit<CardSearch> hit2 = mock(SearchHit.class);
        when(hit2.getContent()).thenReturn(cs2);

        SearchHits<CardSearch> mockHits = mock(SearchHits.class);
        when(mockHits.getSearchHits()).thenReturn(List.of(hit1, hit2));
        when(elasticsearchOperations.search(any(CriteriaQuery.class), eq(CardSearch.class)))
                .thenReturn(mockHits);

        Card card1 = new Card();
        card1.setCardId("card001");
        card1.setName("Lightning Bolt");
        card1.setCardType(CardType.INSTANT);
        card1.setCardRarity(CardRarity.COMMON);

        Card card2 = new Card();
        card2.setCardId("card002");
        card2.setName("Lightning Strike");
        card2.setCardType(CardType.INSTANT);
        card2.setCardRarity(CardRarity.UNCOMMON);

        when(cardRepository.findById("card001")).thenReturn(card1);
        when(cardRepository.findById("card002")).thenReturn(card2);

        // Act
        PagedResult<Card> result = service.fuzzySearch("light", 0, 10, "name", "asc");

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("Lightning Bolt", result.getContent().get(0).getName());
        assertEquals("Lightning Strike", result.getContent().get(1).getName());
    }

    @Test
    void fuzzySearch_returnsEmptyWhenNoMatches() {
        // Arrange: mock Elasticsearch returning no hits
        SearchHits<CardSearch> emptyHits = mock(SearchHits.class);
        when(emptyHits.getSearchHits()).thenReturn(List.of());

        when(elasticsearchOperations.search(any(CriteriaQuery.class), eq(CardSearch.class)))
                .thenReturn(emptyHits);

        // Act
        PagedResult<Card> result = service.fuzzySearch("nonexistent", 0, 10, "name", "asc");

        // Assert
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        assertEquals(0, result.getTotalElements());
    }

    @Test
    void fuzzySearch_defaultsToNameSortingOnInvalidField() {
        // Arrange
        CardSearch cs = new CardSearch();
        cs.setId("card123");
        cs.setName("Lightning Bolt");
        cs.setType("Instant");

        SearchHit<CardSearch> mockHit = mock(SearchHit.class);
        when(mockHit.getContent()).thenReturn(cs);

        SearchHits<CardSearch> mockHits = mock(SearchHits.class);
        when(mockHits.getSearchHits()).thenReturn(List.of(mockHit));
        when(elasticsearchOperations.search(any(CriteriaQuery.class), eq(CardSearch.class)))
                .thenReturn(mockHits);

        Card fullCard = new Card();
        fullCard.setCardId("card123");
        fullCard.setName("Lightning Bolt");
        fullCard.setCardType(CardType.INSTANT);
        fullCard.setCardRarity(CardRarity.COMMON);

        when(cardRepository.findById("card123")).thenReturn(fullCard);

        // Act
        PagedResult<Card> result = service.fuzzySearch("light", 0, 10, "invalidField", "asc");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());

        Card resultCard = result.getContent().get(0);
        assertEquals("Lightning Bolt", resultCard.getName());

    }
}
