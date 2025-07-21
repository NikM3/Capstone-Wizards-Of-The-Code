package wotc.data;

import wotc.models.CollectedCard;
import wotc.models.Collection;

import java.util.List;

public interface CollectionRepository {
    Collection findCollectionByUser(int userId);

    Collection add(Collection collection);

    boolean update(Collection collection);

    boolean deleteById(int collectionId);
}
