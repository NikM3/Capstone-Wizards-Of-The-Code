package wotc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wotc.domain.CardSearchService;
import wotc.models.CardSearchResultDTO;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "*")
public class CardSearchController {

    private final CardSearchService cardSearchService;

    public CardSearchController(CardSearchService cardSearchService) {
        this.cardSearchService = cardSearchService;
    }

    @GetMapping("/{query}")
    public ResponseEntity<List<CardSearchResultDTO>> searchCards(@PathVariable String query) {
        List<CardSearchResultDTO> results = cardSearchService.fuzzySearch(query);
        return ResponseEntity.ok(results);
    }
}
