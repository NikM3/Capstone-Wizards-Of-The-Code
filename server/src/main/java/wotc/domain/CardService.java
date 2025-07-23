package wotc.domain;

import org.springframework.stereotype.Service;
import wotc.data.CardJdbcTemplateRepository;
import wotc.data.CardRepository;
import wotc.models.Card;

import java.util.List;

@Service
public class CardService {

    CardJdbcTemplateRepository repository;

    public CardService (CardJdbcTemplateRepository repository) {
        this.repository = repository;
    }

    public List<Card> findAll() {
        return repository.findAll();
    }

    public Result<Card> add(Card card) {
        return new Result<>();
    }

    public boolean runScryfallUpdate() throws Exception {
        return repository.runScryfallUpdate();
    }
}
