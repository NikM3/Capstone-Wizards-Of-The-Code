package wotc.data;

import wotc.models.CollectedCard;

import java.util.List;

public interface CollectedCardRepository {
    List<CollectedCard> findCollectedCardsByCollection(int collectionId);

    CollectedCard findByCardId(int collectedCardId);

    CollectedCard addCollectedCard(CollectedCard collectedCard);

    boolean editCollectedCard(CollectedCard collectedCard);

    boolean deleteCollectedCard(int collectedCardId);

    CollectedCard findCollectedCardByCardId(int collectionId, int cardId);
}
