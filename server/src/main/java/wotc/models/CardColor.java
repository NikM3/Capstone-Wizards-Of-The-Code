package wotc.models;

public enum CardColor {
    WHITE(1, "White", "w"),
    BLUE(2, "Blue", "u"),
    BLACK(3, "Black", "b"),
    RED(4, "Red", "r"),
    GREEN(5, "Green", "g"),
    COLORLESS(6, "Colorless", "c");

    private final int id;
    private final String name;
    private final String abbreviation;

    CardColor(int id, String name, String abbreviation) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public static CardColor findById(int id) {
        for (CardColor color : CardColor.values()) {
            if (color.getId() == id) {
                return color;
            }
        }
        String message = String.format("No Card Color with id: %s", id);
        throw new RuntimeException(message);
    }

    public static CardColor findByName(String name) {
        for (CardColor color : CardColor.values()) {
            if (color.getName().equalsIgnoreCase(name)) {
                return color;
            }
        }
        String message = String.format("No Card Color with name: %s", name);
        throw new RuntimeException(message);
    }

    public static CardColor findByAbbreviation(String abbr) {
        for (CardColor color : CardColor.values()) {
            if (color.getAbbreviation().equalsIgnoreCase(abbr)) {
                return color;
            }
        }
        String message = String.format("No Card Color with abbreviation: %s", abbr);
        throw new RuntimeException(message);
    }
}
