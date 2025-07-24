package wotc.domain;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;
import wotc.data.CardRepository;
import wotc.data.CardSearchRepository;
import wotc.models.Card;
import wotc.models.CardSearch;
import wotc.models.PagedResult;

import java.util.*;

@Service
public class CardSearchService {

    private final CardRepository cardRepository;
    private final CardSearchRepository cardSearchRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    @Autowired
    public CardSearchService(CardRepository cardRepository,
                             CardSearchRepository cardSearchRepository,
                             ElasticsearchOperations elasticsearchOperations) {
        this.cardRepository = cardRepository;
        this.cardSearchRepository = cardSearchRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @PostConstruct
    public void init() {
        createIndexIfNotExists();
        syncAllCardsToSearchIndex();
    }

    public void createIndexWithCustomAnalyzer() {
        IndexOperations indexOps = elasticsearchOperations.indexOps(CardSearch.class);

        if (indexOps.exists()) {
            indexOps.delete();
        }

        Map<String, Object> settings = Map.of(
                "analysis", Map.of(
                        "filter", Map.of(
                                "autocomplete_filter", Map.of(
                                        "type", "edge_ngram",
                                        "min_gram", 1,
                                        "max_gram", 20
                                )
                        ),
                        "analyzer", Map.of(
                                "autocomplete", Map.of(
                                        "type", "custom",
                                        "tokenizer", "standard",
                                        "filter", List.of("lowercase", "autocomplete_filter")
                                )
                        )
                )
        );

        Map<String, Object> mappings = Map.of(
                "properties", Map.of(
                        "name", Map.of(
                                "type", "text",
                                "analyzer", "autocomplete",
                                "search_analyzer", "standard"
                        ),
                        "type", Map.of(
                                "type", "text"
                        )
                )
        );

        indexOps.create(settings);
        indexOps.putMapping(Document.from(mappings));
    }

    public PagedResult<Card> fuzzySearch(String query, int page, int size, String sort, String direction) {
        Set<String> SORTABLE_FIELDS = Set.of("name", "type", "id");
        String sortField = SORTABLE_FIELDS.contains(sort) ? sort : "name";

        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortField));

        Criteria criteria = new Criteria("name").contains(query)
                .or(new Criteria("type").contains(query));

        CriteriaQuery searchQuery = new CriteriaQuery(criteria, pageable);

        SearchHits<CardSearch> hits;
        try {
            hits = elasticsearchOperations.search(searchQuery, CardSearch.class);
        }  catch (Exception ex) {
            throw new RuntimeException("Search failed: " + ex.getMessage(), ex);
        }

        List<String> ids = hits.getSearchHits().stream()
                .map(hit -> hit.getContent().getId())
                .toList();

        List<Card> cards = ids.stream()
                .map(cardRepository::findById)
                .filter(Objects::nonNull)
                .toList();

        long totalHits = hits.getTotalHits();

        return new PagedResult<>(cards, page, size, totalHits);
    }

    public void syncAllCardsToSearchIndex() {
        try {
            createIndexWithCustomAnalyzer();
            List<Card> cards = cardRepository.findAll();

            for (Card card : cards) {
                cardSearchRepository.save(new CardSearch(card));
            }
            System.out.println("Successfully indexed " + cards.size() + " cards.");
        } catch (Exception ex) {
            System.err.println("Error during indexing: " + ex.getMessage());
            ex.printStackTrace();
        }

    }

    private void createIndexIfNotExists() {
        IndexOperations indexOps = elasticsearchOperations.indexOps(CardSearch.class);

        if (!indexOps.exists()){
            indexOps.create();
            indexOps.putMapping(indexOps.createMapping());
            System.out.println("Created cardsearch index and mapping");
        } else {
            System.out.println("Index cardsearch already exists");
        }
    }
}
