package wotc.domain;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import wotc.data.CardRepository;
import wotc.data.CardSearchRepository;
import wotc.models.Card;
import wotc.models.CardSearch;
import wotc.models.CardSearchResultDTO;

import java.util.List;



@Service
public class CardSearchService {
    private final CardRepository cardRepository;
    private final CardSearchRepository cardSearchRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    @Autowired
    public CardSearchService(CardRepository cardRepository, CardSearchRepository cardSearchRepository, ElasticsearchOperations elasticsearchOperations) {
        this.cardRepository = cardRepository;
        this.cardSearchRepository = cardSearchRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    // Sync all existing cards form DB into Elasticsearch
    public void syncAllCardsToSearchIndex() {
        List<Card> cards = cardRepository.findAll();

        List<CardSearch> searchCards = cards.stream()
                .map(CardSearch::new)
                .toList();

        cardSearchRepository.saveAll(searchCards);
    }

    // Fuzzy search implementation
    public List<CardSearchResultDTO> fuzzySearch(String query) {
        // Build a fuzzy match query for the name field
        MatchQuery nameMatch = new MatchQuery.Builder()
                .field("name")
                .query(query)
                .fuzziness("AUTO")
                .build();

        // Build a fuzzy match query for the type field
        MatchQuery typeMatch = new MatchQuery.Builder()
                .field("type")
                .query(query)
                .fuzziness("AUTO")
                .build();

        // Wrap each match query in a Query object
        Query nameQuery = new Query.Builder()
                .match(nameMatch)
                .build();

        Query typeQuery = new Query.Builder()
                .match(typeMatch)
                .build();

        // Combine both queries into a BoolQuery
        // This allows for results to be either a matching name or type
        BoolQuery boolQuery = new BoolQuery.Builder()
                .should(nameQuery)
                .should(typeQuery)
                .build();

        // Wrap the BoolQuery into a final top-level Query object
        Query finalQuery = new Query.Builder()
                .bool(boolQuery)
                .build();

        // Build the NativeQuery that Spring can execute Elasticsearch on
        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery(finalQuery)
                .build();

        // Execute the search, returns search hits
        SearchHits<CardSearch> hits = elasticsearchOperations.search(searchQuery, CardSearch.class);

        // Map the search hits to DTOs with just the data we want to return
        return hits.getSearchHits().stream()
                .map(hit -> {
                    CardSearch cs = hit.getContent();
                    return new CardSearchResultDTO(cs.getName(), cs.getType());
                })
                .toList();
    }
}