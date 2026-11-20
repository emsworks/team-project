package use_case.login;

import entity.User;
import java.util.Optional;

/*
 * LoginUserDataAccessInterface
 * ------------------------------
 * The storage operations that the Log In use case needs for users.
 * The interactor depends only on this interface; the real implementation
 * (file or database) lives in the data access layer.
 *
 * Main function:
 *  - retrieve(username) : load a user by username so credentials can be checked
 */
public interface LoginUserDataAccessInterface {

    /**
     * Returns the user with the given username, or empty if no such user exists.
     *
     * @param username the username to look up
     * @return the user entity if found, otherwise empty
     */
    Optional<User> retrieve(String username);
}
