package wotc.models;

public enum UserRole {
    ADMIN("Admin"),
    GUEST("Guest");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static UserRole findByName(String name) {
        for (UserRole role : UserRole.values()) {
            if (role.getName().equalsIgnoreCase(name)) {
                return role;
            }
        }
        String message = String.format("No Role with name: %s", name);
        throw new RuntimeException(message);
    }
}
