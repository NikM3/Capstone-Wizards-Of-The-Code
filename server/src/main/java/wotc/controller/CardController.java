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

    @PostMapping()
    public ResponseEntity<Object> add(@RequestBody Card card) {
        Result<Card> result = service.add(card);

        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }

        return ErrorResponse.build(result);
    }

    public ResponseEntity<Object> updateDatabase() {
        // TODO: Start the process of pulling today's bulk download from Scryfall and update the local database
        // 1) Access the Scryfall API and do the bulk download
        // 2) Loop or wait on a Promise until the download is finished
        // 3) Drop the old table
        // 4) Repopulate with the downloaded data
        return null;
    }

}
