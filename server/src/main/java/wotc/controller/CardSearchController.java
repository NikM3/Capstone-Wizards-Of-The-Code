package wotc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wotc.data.CardSearchRepository;
import wotc.domain.CardSearchService;
import wotc.models.CardSearch;
import wotc.models.CardSearchResultDTO;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/sync")
public class CardSearchController {

    private final CardSearchService cardSearchService;
    private final CardSearchRepository cardSearchRepository;

    @Autowired
    public CardSearchController(CardSearchService cardSearchService, CardSearchRepository cardSearchRepository) {
        this.cardSearchService = cardSearchService;
        this.cardSearchRepository = cardSearchRepository;
    }

    @PostMapping
    public ResponseEntity<String> syncCards(){
        cardSearchService.syncAllCardsToSearchIndex();
        return ResponseEntity.ok("Cards indexed to Elasticsearch.");
    }

    @GetMapping("/search")
    public ResponseEntity<List<CardSearchResultDTO>> searchCards(@RequestParam String query) {
        List<CardSearchResultDTO> results = cardSearchRepository
                .findByName(query, query)
                .stream()
                .sorted(Comparator.comparing(CardSearch::getName))
                .map(card -> new CardSearchResultDTO(card.getName(), card.getType()))
                .toList();

        return ResponseEntity.ok(results);
    }
}
