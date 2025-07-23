package wotc.data;

import wotc.models.Card;

import java.util.List;

public interface CardRepository {
    List<Card> findAll();

    Card findById(String cardId);

    boolean updateDatabase(List<Card> cards);

    boolean insert(Card card);

    boolean runScryfallUpdate() throws Exception;
}
