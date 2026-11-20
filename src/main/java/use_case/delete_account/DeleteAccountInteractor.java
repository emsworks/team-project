package use_case.delete_account;

import entity.User;

/*
 * DeleteAccountInteractor
 * ------------------------
 * Contains the business logic for the Delete Account use case.
 * It verifies the user exists and deletes the account and all associated
 * data (activities and time entries).
 *
 * On success it notifies the presenter via the output boundary.
 * On failure it returns a failure message so the presenter can display it.
 *
 * Main flow:
 *  1. Validate that the username is provided.
 *  2. Verify the account exists.
 *  3. Delete the user and all associated data.
 *  4. Notify the presenter.
 *
 * Alternative flows:
 *  - Missing username → failure message
 *  - Account not found → failure message
 */
public class DeleteAccountInteractor implements DeleteAccountInputBoundary {

    private final DeleteAccountDataAccessInterface dataAccessInterface;
    private final DeleteAccountOutputBoundary outputBoundary;

    /**
     * Creates the interactor with its dependencies injected.
     *
     * @param dataAccessInterface the storage interface for users
     * @param outputBoundary      the presenter that receives the result
     */
    public DeleteAccountInteractor(
            DeleteAccountDataAccessInterface dataAccessInterface,
            DeleteAccountOutputBoundary outputBoundary) {
        this.dataAccessInterface = dataAccessInterface;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(DeleteAccountRequestData request) {
        String username = request.getUsername();

        // Validate username.
        if (username == null || username.isBlank()) {
            outputBoundary.failure("Username cannot be blank.");
            return;
        }

        // Verify the account exists.
        User user = dataAccessInterface.retrieve(username).orElse(null);
        if (user == null) {
            outputBoundary.failure("Account does not exist.");
            return;
        }

        // Delete the user and all associated data.
        dataAccessInterface.deleteUser(username);

        // Success — notify presenter.
        outputBoundary.success(new DeleteAccountUserData(username));
    }
}
