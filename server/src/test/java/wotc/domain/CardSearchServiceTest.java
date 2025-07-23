package wotc.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import wotc.data.CardRepository;
import wotc.data.CardSearchRepository;
import wotc.models.CardSearch;
import wotc.models.CardSearchResultDTO;

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
    void fuzzySearch_returnsExpectedDTOs() {
        // Arrange
        CardSearch cs = new CardSearch();
        cs.setName("Lightning Bolt");
        cs.setType("Instant");

        // Mock the SearchHit
        SearchHit<CardSearch> mockHit = mock(SearchHit.class);
        when(mockHit.getContent()).thenReturn(cs);

        // Mock the SearchHits
        SearchHits<CardSearch> mockHits = mock(SearchHits.class);
        when(mockHits.getSearchHits()).thenReturn(List.of(mockHit));

        // Stub elasticsearchOperations.search to return mockHits
        when(elasticsearchOperations.search((Query) any(), eq(CardSearch.class))).thenReturn(mockHits);

        // Act
        List<CardSearchResultDTO> results = service.fuzzySearch("light");

        // Assert
        assertEquals(1, results.size());
        assertEquals("Lightning Bolt", results.get(0).getName());
        assertEquals("Instant", results.get(0).getType());
    }
}
