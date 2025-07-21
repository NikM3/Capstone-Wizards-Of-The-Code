package wotc.models;

public class Collection {
    private int collectionId;
    private int userId;
    private String name;

    // Default constructor
    public Collection() {

    }

    // Constructor for testing
    public Collection(int collectionId, int userId, String name) {
        this.collectionId = collectionId;
        this.userId = userId;
        this.name = name;
    }

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
