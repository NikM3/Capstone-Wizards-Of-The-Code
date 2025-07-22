package wotc.domain;

import org.springframework.stereotype.Service;
import wotc.data.CollectionRepository;
import wotc.models.Collection;

@Service
public class CollectionService {
    private final CollectionRepository repository;

    public CollectionService(CollectionRepository repository) {
        this.repository = repository;
    }

    public Collection findCollectionByUser(int userId) {
        return repository.findCollectionByUser(userId);
    }

    public Result<Collection> add(Collection collection) {
        Result<Collection> result = validate(collection);
        if (!result.isSuccess()) {
            return result;
        }

        if (collection.getCollectionId() != 0) {
            result.addMessage("collectionId cannot be set for an `add` operation", ResultType.INVALID);
            return result;
        }

        collection = repository.add(collection);
        result.setPayload(collection);
        return result;
    }

    public Result<Collection> editCollection(Collection collection) {
        Result<Collection> result = validate(collection);
        if (!result.isSuccess()) {
            return result;
        }

        if (collection.getCollectionId() <= 0) {
            result.addMessage("collectionId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.editCollection(collection)) {
            String msg = String.format("collectionId: %s, not found", collection.getCollectionId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }
        return result;
    }

    public Result<Collection> deleteById(int collectionId) {
        Result<Collection> result = new Result<>();
        if (!repository.deleteById(collectionId)) {
            String msg = String.format("collectionId: %s, not found", collectionId);
            result.addMessage(msg, ResultType.NOT_FOUND);
            return result;
        }
        return result;
    }


    // Collection cannot be null
    // User_id cannot be null
    private Result<Collection> validate(Collection collection) {
        Result<Collection> result = new Result<>();
        if (collection == null) {
            result.addMessage("collection cannot be null", ResultType.INVALID);
            return result;
        }

        if (collection.getUserId() <= 0) {
            result.addMessage("user_id cannot be null", ResultType.INVALID);
        }
        return result;
    }
}
