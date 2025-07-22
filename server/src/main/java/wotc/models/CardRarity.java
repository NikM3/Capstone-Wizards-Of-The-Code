package wotc.models;

public enum CardRarity {
    COMMON(1, "Common"),
    UNCOMMON(2, "Uncommon"),
    RARE(3, "Rare"),
    MYTHIC(4, "Mythic");

    private final int id;
    private final String name;


    CardRarity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static CardRarity findById(int id) {
        for (CardRarity rarity : CardRarity.values()) {
            if (rarity.getId() == id) {
                return rarity;
            }
        }
        String message = String.format("No Card Rarity with id: %s", id);
        throw new RuntimeException(message);
    }

    public static CardRarity findByName(String name) {
        for (CardRarity rarity : CardRarity.values()) {
            if (rarity.getName().equalsIgnoreCase(name)) {
                return rarity;
            }
        }
        String message = String.format("No Card Rarity with name: %s", name);
        throw new RuntimeException(message);
    }
}
