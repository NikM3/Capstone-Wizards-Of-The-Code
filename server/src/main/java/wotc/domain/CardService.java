package wotc.domain;

import org.springframework.stereotype.Service;
import wotc.models.Card;

import java.util.List;

@Service
public class CardService {

    public List<Card> findAll() {
        return List.of();
    }

    public Result<Card> add(Card card) {
        return new Result<>();
    }
}
