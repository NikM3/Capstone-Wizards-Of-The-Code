package wotc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wotc.domain.CardSearchService;
import wotc.models.CardSearchResultDTO;

import java.util.List;

@RestController
@RequestMapping("/search")
public class CardSearchController {

    private final CardSearchService cardSearchService;

    @Autowired
    public CardSearchController(CardSearchService cardSearchService) {
        this.cardSearchService = cardSearchService;
    }

    @PostMapping("/sync")
    public ResponseEntity<String> syncCards() {
        cardSearchService.syncAllCardsToSearchIndex();
        return ResponseEntity.ok("Cards indexed to Elasticsearch.");
    }

    @GetMapping
    public ResponseEntity<List<CardSearchResultDTO>> searchCards(@RequestParam String query) {
        return ResponseEntity.ok(cardSearchService.fuzzySearch(query));
    }
}
