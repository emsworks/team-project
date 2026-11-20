package use_case.signup;

/*
 * SignupOutputBoundary
 * ----------------------
 * The output boundary that the Sign Up interactor calls to deliver results
 * to the presenter.  The presenter implements this interface and is
 * responsible for updating the view.
 *
 * Main functions:
 *  - preparationSuccess(userData) : sign up succeeded; switch to login
 *  - preparationFailure(message)  : sign up failed; show error message
 */
public interface SignupOutputBoundary {

    /**
     * Called when the sign-up succeeds.  The presenter should switch the
     * view to the login screen, optionally pre-filling the username.
     *
     * @param userData the data about the newly created user
     */
    void preparationSuccess(SignupUserData userData);

    /**
     * Called when the sign-up fails (e.g. empty fields, passwords don't
     * match, username already taken).  The presenter should display the
     * error message to the user.
     *
     * @param message the error message to display
     */
    void preparationFailure(String message);
}
