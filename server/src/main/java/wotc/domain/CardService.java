package wotc.domain;

import org.springframework.stereotype.Service;
import wotc.data.CardJdbcTemplateRepository;
import wotc.data.CardSearchRepository;
import wotc.models.Card;
import wotc.models.CardSearch;

import java.util.List;

@Service
public class CardService {

    private final CardJdbcTemplateRepository repository;
    private final CardSearchRepository cardSearchRepository;

    public CardService (CardJdbcTemplateRepository repository, CardSearchRepository cardSearchRepository) {
        this.repository = repository;
        this.cardSearchRepository = cardSearchRepository;
    }

    public List<Card> findAll() {
        return repository.findAll();
    }

    public Card findById(String cardId) {
        return repository.findById(cardId);
    }

    public Result<Card> add(Card card) {
        Result<Card> result = validate(card);

        if (!result.isSuccess()) {
            return result;
        }

        try {
            // Save to MySQL
            boolean success = repository.insert(card);

            if (!success) {
                result.addMessage("Unable to insert card into database", ResultType.INVALID);
                return result;
            }

            // Save to Elasticsearch
            CardSearch cardSearch = new CardSearch(card);
            cardSearchRepository.save(cardSearch);

            result.setPayload(card);
        } catch (Exception e) {
            result.addMessage("Failed to save card: " + e.getMessage(), ResultType.INVALID);
        }

        return result;
    }

    public boolean runScryfallUpdate() throws Exception {
        boolean updated = repository.runScryfallUpdate();

        if (updated) {
            List<Card> cards = repository.findAll();
            List<CardSearch> searchCards = cards.stream()
                    .map(CardSearch::new)
                    .toList();

            cardSearchRepository.saveAll(searchCards);
            System.out.println("Cards auto-indexed to Elasticsearch.");
        }
        return updated;
    }

    private Result<Card> validate(Card card) {
        Result<Card> result = new Result<>();

        if (card == null) {
            result.addMessage("Card cannot be null.", ResultType.INVALID);
            return result;
        }

        if (card.getCardId() == null || card.getCardId().isBlank()) {
            result.addMessage("Card ID is required.", ResultType.INVALID);
        }

        if (card.getName() == null || card.getName().isBlank()) {
            result.addMessage("Card name is required.", ResultType.INVALID);
        }

        if (card.getCardType() == null) {
            result.addMessage("Card type is required.", ResultType.INVALID);
        }

        if (card.getCardRarity() == null) {
            result.addMessage("Card rarity is required.", ResultType.INVALID);
        }

        return result;
    }
}
