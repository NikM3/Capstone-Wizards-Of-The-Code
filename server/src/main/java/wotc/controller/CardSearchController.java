package wotc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wotc.domain.CardSearchService;
import wotc.models.Card;
import wotc.models.PagedResult;

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
    public ResponseEntity<PagedResult<Card>> searchCards(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        if (query == null || query.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        PagedResult<Card> result = cardSearchService.fuzzySearch(query, page, size, sort, direction);
        return ResponseEntity.ok(result);
    }

}
