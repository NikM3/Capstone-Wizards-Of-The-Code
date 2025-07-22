package wotc.models;

public enum CardType {
    ARTIFACT("Artifact"),
    CREATURE("Creature"),
    ENCHANTMENT("Enchantment"),
    LAND("Land"),
    INSTANT("Instant"),
    PLANESWALKER("Planeswalker"),
    SORCERY("Sorcery"),
    BATTLE("Battle");

    private final String name;


    CardType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static CardType findByName(String name) {
        for (CardType type : CardType.values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        String message = String.format("No Card Type with name: %s", name);
        throw new RuntimeException(message);
    }
}
