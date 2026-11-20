package use_case.signup;

import entity.User;
import java.util.Optional;

/*
 * SignupUserDataAccessInterface
 * ------------------------------
 * The storage operations that the Sign Up use case needs for users.
 * The interactor depends only on this interface; the real implementation
 * (file or database) lives in the data access layer.
 *
 * Main functions:
 *  - exists(username) : check whether a username is already taken
 *  - save(user)       : persist a new user
 */
public interface SignupUserDataAccessInterface {

    /**
     * Returns true if a user with this username already exists.
     */
    boolean exists(String username);

    /**
     * Saves a new user to storage.
     */
    void save(User user);
}
