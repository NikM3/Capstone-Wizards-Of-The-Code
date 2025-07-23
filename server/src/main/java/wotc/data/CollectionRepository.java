package wotc.data;

import wotc.models.CollectedCard;
import wotc.models.Collection;

import java.util.List;

public interface CollectionRepository {
    Collection findCollectionByUserId(int userId);

    Collection findCollectionByUserEmail(String email);

    Collection add(Collection collection);

    boolean editCollection(Collection collection);

    boolean deleteById(int collectionId);
}
