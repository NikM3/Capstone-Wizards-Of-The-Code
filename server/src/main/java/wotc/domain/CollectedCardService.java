package wotc.domain;

import org.springframework.stereotype.Service;
import wotc.data.CollectedCardRepository;
import wotc.models.CollectedCard;

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
}
