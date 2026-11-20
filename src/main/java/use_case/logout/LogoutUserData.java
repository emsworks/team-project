package use_case.logout;

/*
 * LogoutUserData
 * ---------------
 * Immutable data returned by the Log Out interactor after a successful
 * logout.  The presenter uses this to update the view (e.g. pre-fill
 * the username on the login screen).
 */
public class LogoutUserData {

    private final String username;

    /**
     * Creates output data for a successful logout.
     *
     * @param username the username of the user who was logged out
     */
    public LogoutUserData(String username) {
        this.username = username;
    }

    /** Returns the username of the user who was logged out. */
    public String getUsername() {
        return username;
    }
}
