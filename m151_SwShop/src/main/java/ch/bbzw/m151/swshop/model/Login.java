package ch.bbzw.m151.swshop.model;

public class Login {
    private final String username;
    private final String password;

    public Login(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public static Login fromString(final String string) {
        final String[] split = string.split(":");
        return new Login(split[0], split[1]);
    }

    public String toString() {
        return String.format("%s:%s", username, password);
    }
}
