package wotc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wotc.domain.CollectedCardService;
import wotc.domain.Result;
import wotc.domain.ResultType;
import wotc.models.CollectedCard;

import java.util.List;

@RestController
@RequestMapping("/api/collected/card")
public class CollectedCardController {

    private final CollectedCardService service;

    public CollectedCardController(CollectedCardService service) {
        this.service = service;
    }

    @GetMapping("/{collectionId}")
    public List<CollectedCard> findCollectedCards(@PathVariable int collectionId) {
        return service.findCollectedCardsByCollection(collectionId);
    }

    @PostMapping
    public ResponseEntity<Object> addCollectedCard(@RequestBody CollectedCard collectedCard) {
        Result<CollectedCard> result = service.addCollectedCard(collectedCard);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{collectedCardId}")
    public ResponseEntity<Object> editCollectedCard(@PathVariable int collectedCardId, @RequestBody CollectedCard collectedCard) {
        if (collectedCardId != collectedCard.getCollectedCardId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<CollectedCard> result = service.editCollectedCard(collectedCard);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("{collectedCardId}")
    public ResponseEntity<Void> deleteCollectedCard(@PathVariable int collectedCardId) {
        Result<CollectedCard> result = service.deleteCollectedCard(collectedCardId);

        if (result.getType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

        if (result.getType() == ResultType.INVALID) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
