package entity;

import java.util.Objects;

/*
 * User
 * -----
 * Represents a registered user account.
 *
 * A user has a unique username and a password.  The username is used as the
 * primary identifier throughout the application; other entities (activities,
 * time entries) refer to their owner by username.
 *
 * Main functions:
 *  - User(username, password) : create a new user
 *  - getUsername() / getPassword() : read-only access to the fields
 *
 * This class does not know about the GUI, storage, or use cases.
 */
public class User {

    private final String username;
    private final String password;

    /**
     * Creates a new user with the given credentials.
     *
     * @param username the unique username
     * @param password the password (stored as-is; hashing is a planned improvement)
     */
    public User(String username, String password) {
        this.username = Objects.requireNonNull(username, "username cannot be null");
        this.password = Objects.requireNonNull(password, "password cannot be null");
    }

    /** Returns the user's unique username. */
    public String getUsername() {
        return username;
    }

    /** Returns the user's password. */
    public String getPassword() {
        return password;
    }

    /**
     * Two users are equal if they share a username.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User other = (User) o;
        return username.equals(other.username);
    }

    /** Hash code based on the username, consistent with equals. */
    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
