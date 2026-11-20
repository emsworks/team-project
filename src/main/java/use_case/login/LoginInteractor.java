package use_case.login;

import entity.User;

/*
 * LoginInteractor
 * ----------------
 * Contains the business logic for the Log In use case.
 * It validates the user's input, retrieves the stored user, checks the
 * password, and notifies the presenter of the result.
 *
 * On success it notifies the presenter via the output boundary.
 * On failure it returns a failure message so the presenter can display it.
 *
 * Main flow:
 *  1. Validate that username and password are non-empty.
 *  2. Retrieve the user from storage.
 *  3. Check that the password matches.
 *
 * Alternative flows:
 *  - Empty fields → failure message
 *  - Account doesn't exist → failure message
 *  - Wrong password → failure message
 */
public class LoginInteractor implements LoginInputBoundary {

    private final LoginUserDataAccessInterface userDataAccessInterface;
    private final LoginOutputBoundary outputBoundary;

    /**
     * Creates the interactor with its dependencies injected.
     *
     * @param userDataAccessInterface the storage interface for users
     * @param outputBoundary          the presenter that receives the result
     */
    public LoginInteractor(
            LoginUserDataAccessInterface userDataAccessInterface,
            LoginOutputBoundary outputBoundary) {
        this.userDataAccessInterface = userDataAccessInterface;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(LoginRequestData request) {
        // Validate non-empty fields.
        if (request.getUsername() == null || request.getUsername().isBlank()
                || request.getPassword() == null || request.getPassword().isBlank()) {
            outputBoundary.failure("Username and password can't be empty.");
            return;
        }

        // Retrieve the user from storage.
        User user = userDataAccessInterface.retrieve(request.getUsername())
                .orElse(null);

        if (user == null) {
            outputBoundary.failure(request.getUsername() + ": Account does not exist.");
            return;
        }

        // Check the password.
        if (!user.getPassword().equals(request.getPassword())) {
            outputBoundary.failure("Incorrect password.");
            return;
        }

        // Success — notify presenter.
        outputBoundary.success(new LoginUserData(user.getUsername()));
    }
}
