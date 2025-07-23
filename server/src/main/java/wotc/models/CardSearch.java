package wotc.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "cardsearch")
public class CardSearch {

    @Id
    private int id;
    private String name;
    private String type;

    public CardSearch() {}

    public CardSearch(Card card) {
        this.id = Integer.parseInt(card.getCardId());
        this.name = card.getName();
        this.type = card.getCardType().name();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
