package wotc.data;

import wotc.models.Card;

import java.util.List;

public interface CardRepository {
    List<Card> findAll();

    boolean updateDatabase(List<Card> cards);

    boolean runScryfallUpdate() throws Exception;
}
