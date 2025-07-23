package wotc.models;

public enum CardType {
    ARTIFACT(1, "Artifact"),
    CREATURE(2, "Creature"),
    ENCHANTMENT(3, "Enchantment"),
    LAND(4, "Land"),
    INSTANT(5, "Instant"),
    PLANESWALKER(6, "Planeswalker"),
    SORCERY(7, "Sorcery"),
    BATTLE(8, "Battle"),
    UNKNOWN(9, "Unknown");

    private final int id;
    private final String name;


    CardType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static CardType findById(int id) {
        for (CardType type : CardType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        String message = String.format("No Card Type with id: %s", id);
        throw new RuntimeException(message);
    }

    public static CardType findByName(String name) {
        for (CardType type : CardType.values()) {
            if (name.toLowerCase().contains(type.getName().toLowerCase())) {
                return type;
            }
        }
        return CardType.UNKNOWN;
    }
}
