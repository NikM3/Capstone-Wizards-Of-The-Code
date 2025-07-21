package wotc.models;

public enum CardRarity {
    COMMON("Common"),
    UNCOMMON("Uncommon"),
    RARE("Rare"),
    MYTHIC("Mythic");

    private final String name;


    CardRarity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
