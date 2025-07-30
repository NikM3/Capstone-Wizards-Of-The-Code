package wotc.models;

import java.util.List;

public class Card {
    private String cardId;
    private String name;
    private String manaCost;
    private CardType cardType;
    private List<CardColor> cardColors;
    private CardRarity cardRarity;
    private String cardText;
    private String cardSet;
    private String imageUri;

    // Default constructor
    public Card() {

    }

    // Constructor for testing
    public Card(String cardId, String name, String manaCost, CardType cardType, List<CardColor> cardColors, CardRarity cardRarity, String cardText, String cardSet, String imageUri) {
        this.cardId = cardId;
        this.name = name;
        this.manaCost = manaCost;
        this.cardType = cardType;
        this.cardColors = cardColors;
        this.cardRarity = cardRarity;
        this.cardText = cardText;
        this.cardSet = cardSet;
        this.imageUri = imageUri;
    }

   // Getters and setters

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManaCost() {
        return manaCost;
    }

    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public List<CardColor> getCardColors() {
        return cardColors;
    }

    public void setCardColors(List<CardColor> cardColors) {
        this.cardColors = cardColors;
    }

    public CardRarity getCardRarity() {
        return cardRarity;
    }

    public void setCardRarity(CardRarity cardRarity) {
        this.cardRarity = cardRarity;
    }

    public String getCardText() {
        return cardText;
    }

    public void setCardText(String cardText) {
        this.cardText = cardText;
    }

    public String getCardSet() {
        return cardSet;
    }

    public void setCardSet(String cardSet) {
        this.cardSet = cardSet;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
