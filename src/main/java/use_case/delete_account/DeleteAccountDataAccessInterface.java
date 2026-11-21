package use_case.delete_account;

import entity.User;
import java.util.Optional;

/*
 * DeleteAccountDataAccessInterface
 * ----------------------------------
 * The storage operations that the Delete Account use case needs.
 * The interactor depends only on this interface; the real implementation
 * (file or database) lives in the data access layer.
 *
 * Main functions:
 *  - retrieve(username) : load a user
 *  - deleteUser(username) : remove the user and all associated data
 */
public interface DeleteAccountDataAccessInterface {

    /**
     * Returns the user with the given username, or empty if no such user exists.
     *
     * @param username the username to look up
     */
    Optional<User> retrieve(String username);

    /**
     * Permanently removes the user and all of their activities and time entries.
     *
     * @param username the username to delete
     */
    void deleteUser(String username);
}
