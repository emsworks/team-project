package use_case.logout;

/*
 * LogoutInteractor
 * -----------------
 * Contains the business logic for the Log Out use case.
 * It clears the current user session and notifies the presenter so the
 * view can switch back to the login screen.
 *
 * The interactor holds a reference to the current username.  The login
 * interactor (or a shared session manager) sets it, and this interactor
 * clears it.
 *
 * Main flow:
 *  1. Check that a user is currently logged in.
 *  2. Clear the current user.
 *
 * Alternative flows:
 *  - No user is logged in → failure message
 */
public class LogoutInteractor implements LogoutInputBoundary {

    private final LogoutOutputBoundary outputBoundary;

    // The username of the currently logged-in user, or null if nobody is logged in.
    private String currentUser;

    /**
     * Creates the interactor with its output boundary injected.
     *
     * @param outputBoundary the presenter that receives the result
     */
    public LogoutInteractor(LogoutOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    /**
     * Sets the current user (called by the login flow after a successful login).
     *
     * @param username the username of the logged-in user
     */
    public void setCurrentUser(String username) {
        this.currentUser = username;
    }

    /**
     * Returns the current username, or null if no user is logged in.
     */
    public String getCurrentUser() {
        return currentUser;
    }

    @Override
    public void execute() {
        // Check that a user is logged in.
        if (currentUser == null) {
            outputBoundary.failure("No user is logged in.");
            return;
        }

        // Remember the username for the output data, then clear the session.
        String username = currentUser;
        currentUser = null;

        // Success — notify presenter.
        outputBoundary.success(new LogoutUserData(username));
    }
}
