package wotc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wotc.domain.CollectionService;
import wotc.domain.Result;
import wotc.domain.ResultType;
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

    @GetMapping("/{collectionId}")
    public Collection findCollectionByCollectionId(@PathVariable int collectionId) {
        return service.findCollectionByCollectionId(collectionId);
    }

    @GetMapping("/user/{userId}")
    public Collection findCollectionByUserId(@PathVariable int userId) {
        return service.findCollectionByUserId(userId);
    }

    @GetMapping("/email/{userEmail}")
    public Collection findCollectionByUserEmail(@PathVariable String userEmail) {
        return service.findCollectionByUserEmail(userEmail);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Collection collection) {
        Result<Collection> result = service.add(collection);

        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{collectionId}")
    public ResponseEntity<Object> update(@PathVariable int collectionId, @RequestBody Collection collection) {
        if (collectionId != collection.getCollectionId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Collection> result = service.editCollection(collection);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }
}
