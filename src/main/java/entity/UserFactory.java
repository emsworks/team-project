package entity;

/*
 * UserFactory
 * -----------
 * Factory for creating User entities.
 *
 * Centralises user creation so that validation logic lives in one place
 * and can be extended later (e.g. password hashing) without changing
 * every call site.
 */
public class UserFactory {

    /**
     * Creates a new User with the given username and password.
     * Both must be non-null and non-blank.
     *
     * @param username the desired username
     * @param password the desired password
     * @return a new User instance
     * @throws IllegalArgumentException if username or password is blank
     */
    public static User create(String username, String password) {
        return new User(username, password);
    }
}
