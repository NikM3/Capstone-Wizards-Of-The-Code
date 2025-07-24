package wotc.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "cardsearch")
public class CardSearch {

    @Id
    private String id;

    @Field(type = FieldType.Text, searchAnalyzer = "standard")
    private String name;

    @Field(type = FieldType.Text)
    private String type;

    public CardSearch() {}

    public CardSearch(Card card) {

        this.id = card.getCardId();
        this.name = card.getName();
        this.type = card.getCardType().name();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
