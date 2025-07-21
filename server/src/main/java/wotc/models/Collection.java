package wotc.models;

public class Collection {
    private int collectionId;
    private String name;

    // Default constructor
    public Collection() {

    }

    // Constructor for testing
    public Collection(int collectionId, String name) {
        this.collectionId = collectionId;
        this.name = name;
    }

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
