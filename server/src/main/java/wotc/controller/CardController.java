package wotc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wotc.domain.CardService;
import wotc.domain.Result;
import wotc.models.Card;

import java.util.List;

@RestController
@RequestMapping("/api/card")
public class CardController {

    private final CardService service;

    public CardController(CardService service) {
        this.service = service;
    }

    @GetMapping()
    public List<Card> findAll() {
        return service.findAll();
    }

    @GetMapping("/{cardId}")
    public Card findById(@PathVariable String cardId) {
        return service.findById(cardId);
    }

    @PostMapping()
    public ResponseEntity<Object> add(@RequestBody Card card) {
        Result<Card> result = service.add(card);

        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }

        return ErrorResponse.build(result);
    }

    @PostMapping("/database")
    public boolean runScryfallUpdate() throws Exception {
        return service.runScryfallUpdate();
    }

}
