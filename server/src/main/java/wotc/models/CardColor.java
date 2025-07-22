package wotc.models;

public enum CardColor {
    WHITE("White", "w"),
    BLUE("Blue", "u"),
    BLACK("Black", "b"),
    RED("Red", "r"),
    GREEN("Green", "g"),
    COLORLESS("Colorless", "c");

    private final String name;
    private final String abbreviation;

    CardColor(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
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
