package wotc.models;

import java.util.List;

public class User {
    private int userId;
    private String username;
    private String email;
    private String password;
    private boolean restricted;
    private List<String> roles;

    // Default constructor
    public User() {

    }

    // Constructor for testing
    public User(int userId, String username, String email, String password, boolean restricted, List<String> roles) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.restricted = restricted;
        this.roles = roles;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRestricted() {
        return restricted;
    }

    public void setRestricted(boolean restricted) {
        this.restricted = restricted;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
