package use_case.signup;

import entity.User;

/*
 * SignupInteractor
 * -----------------
 * Contains the business logic for the Sign Up use case.
 * It validates the user's input, checks that the username is available,
 * creates the user entity, and persists it through the data access interface.
 *
 * On success it notifies the presenter via the output boundary.
 * On failure it returns a failure message so the presenter can display it.
 *
 * Main flow:
 *  1. Validate that username and password are non-empty.
 *  2. Validate that the two password fields match.
 *  3. Check that the username is not already taken.
 *  4. Create and save the user.
 *
 * Alternative flows:
 *  - Empty fields → failure message
 *  - Passwords don't match → failure message
 *  - Username already taken → failure message
 */
public class SignupInteractor implements SignupInputBoundary {

    private final SignupUserDataAccessInterface userDataAccessInterface;
    private final SignupOutputBoundary outputBoundary;

    /**
     * Creates the interactor with its dependencies injected.
     *
     * @param userDataAccessInterface the storage interface for users
     * @param outputBoundary          the presenter that receives the result
     */
    public SignupInteractor(
            SignupUserDataAccessInterface userDataAccessInterface,
            SignupOutputBoundary outputBoundary) {
        this.userDataAccessInterface = userDataAccessInterface;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(SignupRequestData request) {
        // Validate non-empty fields.
        if (request.getUsername() == null || request.getUsername().isBlank()
                || request.getPassword() == null || request.getPassword().isBlank()) {
            outputBoundary.preparationFailure("Username and password can't be empty.");
            return;
        }

        // Validate password match.
        if (!request.getPassword().equals(request.getPasswordConfirmation())) {
            outputBoundary.preparationFailure("Passwords don't match.");
            return;
        }

        // Check for duplicate username.
        if (userDataAccessInterface.exists(request.getUsername())) {
            outputBoundary.preparationFailure("User already exists.");
            return;
        }

        // Create and save the user.
        User user = new User(request.getUsername(), request.getPassword());
        userDataAccessInterface.save(user);

        // Success — notify presenter.
        outputBoundary.preparationSuccess(new SignupUserData(request.getUsername()));
    }
}
