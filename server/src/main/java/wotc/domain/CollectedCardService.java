package wotc.domain;

import org.springframework.stereotype.Service;
import wotc.data.CollectedCardRepository;
import wotc.models.CollectedCard;
import wotc.models.Collection;

import java.util.List;

@Service
public class CollectedCardService {
    private final CollectedCardRepository repository;

    public CollectedCardService(CollectedCardRepository repository) {
        this.repository = repository;
    }

    public List<CollectedCard> findCollectedCardsByCollection(int collectionId) {
        return repository.findCollectedCardsByCollection(collectionId);
    }

    public CollectedCard findByCardId(int collectedCardId) {
        return repository.findByCardId(collectedCardId);
    }

    public Result<CollectedCard> addCollectedCard(CollectedCard collectedCard) {
        Result<CollectedCard> result = validate(collectedCard);
        if (!result.isSuccess()) {
            return result;
        }

        if (collectedCard.getCollectedCardId() != 0) {
            result.addMessage("collectedCardId cannot be set for an `add` operation", ResultType.INVALID);
            return result;
        }

        collectedCard = repository.addCollectedCard(collectedCard);
        result.setPayload(collectedCard);
        return result;
    }

    public Result<CollectedCard> editCollectedCard(CollectedCard collectedCard) {
        Result<CollectedCard> result = validate(collectedCard);
        if (!result.isSuccess()) {
            return result;
        }

        if (collectedCard.getCollectedCardId() <= 0) {
            result.addMessage("collectedCardId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.editCollectedCard(collectedCard)) {
            String msg = String.format("collectedCardId: %s, not found", collectedCard.getCollectedCardId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }
        return result;
    }

    public Result<CollectedCard> deleteCollectedCard(int collectedCardId) {
        Result<CollectedCard> result = new Result<>();
        if (!repository.deleteCollectedCard(collectedCardId)) {
            String msg = String.format("collectionCardId: %s, not found", collectedCardId);
            result.addMessage(msg, ResultType.NOT_FOUND);
            return result;
        }
        return result;
    }

    // CollectedCard cannot be null
    // Card id cannot be null
    // Collection id cannot be null
    // quantity cannot be null
    private Result<CollectedCard> validate(CollectedCard collectedCard) {
        Result<CollectedCard> result = new Result<>();
        if (collectedCard == null) {
            result.addMessage("collected card cannot be null", ResultType.INVALID);
            return result;
        }

        if (collectedCard.getCardId() <= 0) {
            result.addMessage("card_id cannot be null", ResultType.INVALID);
        }

        if (collectedCard.getCollectionId() <= 0) {
            result.addMessage("collection_id cannot be null", ResultType.INVALID);
        }

        if(collectedCard.getQuantity() <= 0) {
            result.addMessage("Quantity cannot be less than or equal to 0", ResultType.INVALID);
        }

        List<CollectedCard> collectedCards = findCollectedCardsByCollection(collectedCard.getCollectionId());
        for (CollectedCard card : collectedCards) {
            if (card.getCardId() == collectedCard.getCardId()) {
                result.addMessage(String.format("A card with card_id %s is already in your collection", collectedCard.getCardId()), ResultType.INVALID);
            }
        }
        return result;

    }
}
