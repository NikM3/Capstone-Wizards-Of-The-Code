package wotc.domain;


import org.elasticsearch.common.recycler.Recycler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;
import wotc.data.CardRepository;
import wotc.data.CardSearchRepository;
import wotc.models.Card;
import wotc.models.CardSearch;

import jakarta.annotation.PostConstruct;
import wotc.models.PagedResult;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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


    // Index all cards from MySQL into Elasticsearch
    public void syncAllCardsToSearchIndex() {
        List<Card> cards = cardRepository.findAll();

        List<CardSearch> searchCards = cards.stream()
                .map(CardSearch::new)
                .toList();

        cardSearchRepository.saveAll(searchCards);
    }


    // Perform a fuzzy search by card name or type and return full Card objects
    public PagedResult<Card> fuzzySearch(String query, int page, int size, String sort, String direction) {
        // Allowed sort fields
        Set<String> SORTABLE_FIELDS = Set.of("name");

        // Default to name if field is invalid
        if (!SORTABLE_FIELDS.contains(sort)) {
            sort = "name";
        }

        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));

        Criteria criteria = new Criteria("name").fuzzy(query);

        CriteriaQuery searchQuery = new CriteriaQuery(criteria, pageable);

        SearchHits<CardSearch> hits = elasticsearchOperations.search(searchQuery, CardSearch.class);

        List<Card> cards = hits.getSearchHits().stream()
                .map(hit -> cardRepository.findById(hit.getContent().getId()))
                .filter(Objects::nonNull)
                .toList();

        long totalHits = hits.getTotalHits();

        return new PagedResult<>(cards, page, size, totalHits);
    }

    @PostConstruct
    public void createIndexWithCustomAnalyzer() {
        IndexOperations indexOps = elasticsearchOperations.indexOps(IndexCoordinates.of("cardsearch"));

        if (indexOps.exists()) {
            indexOps.delete();
        }

        // settings + mapping for autocomplete with edge_ngram
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
}
