package use_case.logout;

/*
 * LogoutOutputBoundary
 * ----------------------
 * The output boundary that the Log Out interactor calls to deliver results
 * to the presenter.  The presenter implements this interface and is
 * responsible for updating the view.
 *
 * Main functions:
 *  - success(userData)   : logout succeeded; switch to login screen
 *  - failure(message)    : logout failed (e.g. no user logged in)
 */
public interface LogoutOutputBoundary {

    /**
     * Called when the log-out succeeds.  The presenter should switch the
     * view to the login screen, optionally pre-filling the username.
     *
     * @param userData the data about the user who was logged out
     */
    void success(LogoutUserData userData);

    /**
     * Called when the log-out fails (e.g. no user is currently logged in).
     * The presenter should display the error message.
     *
     * @param message the error message to display
     */
    void failure(String message);
}
