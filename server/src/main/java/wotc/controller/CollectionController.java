package wotc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wotc.domain.CollectionService;
import wotc.domain.Result;
import wotc.models.CollectedCard;
import wotc.models.Collection;

import java.util.List;

@RestController
@RequestMapping("/api/collection")
public class CollectionController {

    private final CollectionService service;

    public CollectionController(CollectionService service) {
        this.service = service;
    }

    @GetMapping("/{userId}")
    public Collection findCollectionByUser(@PathVariable int userId) {
        return service.findCollectionByUser(userId);
    }

    @GetMapping("/cards/{collectionId}")
    public List<CollectedCard> findCollectedCards(@PathVariable int collectionId) {
        // return service.findCollectedCardsByCollection(collectionId);
        return null;
    }

    @PostMapping("/cards/{collectionId}")
    public ResponseEntity<Object> addCollectedCard(@RequestBody CollectedCard cc, @PathVariable int collectionId) {
        //Result<CollectedCard> result = service.addCollectedCard(cc);
        System.out.println("Are we getting into the method at all?");
        Result<CollectedCard> result = new Result<>();

        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }

        return ErrorResponse.build(result);
    }

    @PutMapping("/cards/card/{collectedCardId}")
    public ResponseEntity<Object> editCollectedCard(@RequestBody CollectedCard cc, @PathVariable int collectedCardId) {
        if (collectedCardId != cc.getCollectedCardId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        // Result<CollectedCard> result = service.editCollectedCard(cc);
        Result<CollectedCard> result = new Result<>();

        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/cards/card/{collectedCardId}")
    public ResponseEntity<Object> deleteCollectedCard(@PathVariable int collectedCardId) {
        // Result<CollectedCard> result = service.deleteCollectedCardById(collectedCardId);
        Result<CollectedCard> result = new Result<>();

        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }
}
