package use_case.login;

/*
 * LoginOutputBoundary
 * --------------------
 * The output boundary that the Log In interactor calls to deliver results
 * to the presenter.  The presenter implements this interface and is
 * responsible for updating the view.
 *
 * Main functions:
 *  - success(userData)   : login succeeded; switch to dashboard
 *  - failure(message)    : login failed; show error message
 */
public interface LoginOutputBoundary {

    /**
     * Called when the log-in succeeds.  The presenter should switch the
     * view to the dashboard.
     *
     * @param userData the data about the logged-in user
     */
    void success(LoginUserData userData);

    /**
     * Called when the log-in fails (e.g. empty fields, account not found,
     * wrong password).  The presenter should display the error message.
     *
     * @param message the error message to display
     */
    void failure(String message);
}
