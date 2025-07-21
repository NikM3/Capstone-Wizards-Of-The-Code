package wotc.models;

public class CollectedCard {
    private int collectedCardId;
    private int cardId;
    private int collectionId;
    private int quantity;
    private String condition;
    private boolean inUse;

    // Default constructor
    public CollectedCard() {

    }

    // Constructor for testing
    public CollectedCard(int collectedCardId, int cardId, int collectionId, int quantity, String condition, boolean inUse) {
        this.collectedCardId = collectedCardId;
        this.cardId = cardId;
        this.collectionId = collectionId;
        this.quantity = quantity;
        this.condition = condition;
        this.inUse = inUse;
    }

    public int getCollectedCardId() {
        return collectedCardId;
    }

    public void setCollectedCardId(int collectedCardId) {
        this.collectedCardId = collectedCardId;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }
}
