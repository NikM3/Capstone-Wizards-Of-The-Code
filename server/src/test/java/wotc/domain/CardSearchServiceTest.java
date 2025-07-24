package wotc.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
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
    void fuzzySearch_returnsSingleCard() {
        // Arrange
        CardSearch cs = new CardSearch();
        cs.setId("card123");
        cs.setName("Lightning Bolt");
        cs.setType("Instant");

        SearchHit<CardSearch> hit = mock(SearchHit.class);
        when(hit.getContent()).thenReturn(cs);

        SearchHits<CardSearch> hits = mock(SearchHits.class);
        when(hits.getSearchHits()).thenReturn(List.of(hit));
        when(hits.getTotalHits()).thenReturn(1L);

        when(elasticsearchOperations.search(any(CriteriaQuery.class), eq(CardSearch.class))).thenReturn(hits);

        Card card = new Card();
        card.setCardId("card123");
        card.setName("Lightning Bolt");
        card.setCardType(CardType.INSTANT);
        card.setCardRarity(CardRarity.COMMON);

        when(cardRepository.findById("card123")).thenReturn(card);

        // Act
        PagedResult<Card> result = service.fuzzySearch("bolt", 0, 10, "name", "asc");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Lightning Bolt", result.getContent().get(0).getName());
    }

    @Test
    void fuzzySearch_returnsEmptyWhenNoMatch() {
        // Arrange
        SearchHits<CardSearch> emptyHits = mock(SearchHits.class);
        when(emptyHits.getSearchHits()).thenReturn(List.of());
        when(emptyHits.getTotalHits()).thenReturn(0L);

        when(elasticsearchOperations.search(any(CriteriaQuery.class), eq(CardSearch.class))).thenReturn(emptyHits);

        // Act
        PagedResult<Card> result = service.fuzzySearch("unknown", 0, 10, "name", "asc");

        // Assert
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        assertEquals(0, result.getTotalElements());
    }

    @Test
    void fuzzySearch_defaultsToNameWhenSortInvalid() {
        // Arrange
        CardSearch cs = new CardSearch();
        cs.setId("card001");
        cs.setName("Shock");
        cs.setType("Instant");

        SearchHit<CardSearch> hit = mock(SearchHit.class);
        when(hit.getContent()).thenReturn(cs);

        SearchHits<CardSearch> hits = mock(SearchHits.class);
        when(hits.getSearchHits()).thenReturn(List.of(hit));
        when(hits.getTotalHits()).thenReturn(1L);

        when(elasticsearchOperations.search(any(CriteriaQuery.class), eq(CardSearch.class))).thenReturn(hits);

        Card card = new Card();
        card.setCardId("card001");
        card.setName("Shock");
        card.setCardType(CardType.INSTANT);
        card.setCardRarity(CardRarity.COMMON);

        when(cardRepository.findById("card001")).thenReturn(card);

        // Act
        PagedResult<Card> result = service.fuzzySearch("shock", 0, 10, "invalid", "asc");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Shock", result.getContent().get(0).getName());
    }

    @Test
    void syncAllCardsToSearchIndex_indexesAllCards() {
        // Arrange
        Card card1 = new Card();
        card1.setCardId("card1");
        card1.setName("Lightning Bolt");
        card1.setCardType(CardType.INSTANT);

        Card card2 = new Card();
        card2.setCardId("card2");
        card2.setName("Grizzly Bears");
        card2.setCardType(CardType.CREATURE);

        when(cardRepository.findAll()).thenReturn(List.of(card1, card2));

        IndexOperations indexOps = mock(IndexOperations.class);
        when(elasticsearchOperations.indexOps(CardSearch.class)).thenReturn(indexOps);
        when(indexOps.exists()).thenReturn(false); 

        // Capture index requests
        ArgumentCaptor<CardSearch> documentCaptor = ArgumentCaptor.forClass(CardSearch.class);
        when(cardSearchRepository.save(documentCaptor.capture())).thenReturn(null);

        // Act
        service.syncAllCardsToSearchIndex();

        // Assert
        verify(indexOps).create(any());
        verify(indexOps).putMapping((Document) any());
        verify(cardSearchRepository, times(2)).save(any(CardSearch.class));

        List<CardSearch> savedDocuments = documentCaptor.getAllValues();
        assertEquals("card1", savedDocuments.get(0).getId());
        assertEquals("card2", savedDocuments.get(1).getId());
    }
}
