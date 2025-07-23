package wotc.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
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

    // Perform a fuzzy search by card name or type
    public List<CardSearchResultDTO> fuzzySearch(String query) {
        // Match either fuzzy name or fuzzy type
        Criteria criteria = new Criteria("name").fuzzy(query)
                .or(new Criteria("type").fuzzy(query));

        CriteriaQuery searchQuery = new CriteriaQuery(criteria);

        SearchHits<CardSearch> hits = elasticsearchOperations.search(searchQuery, CardSearch.class);

        return hits.getSearchHits().stream()
                .map(hit -> {
                    CardSearch cs = hit.getContent();
                    return new CardSearchResultDTO(cs.getName(), cs.getType());
                })
                .toList();
    }
}
