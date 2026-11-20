package use_case.login;

/*
 * LoginUserData
 * --------------
 * Immutable data returned by the Log In interactor after a successful
 * login.  The presenter uses this to update the view (e.g. display the
 * user's name on the dashboard).
 */
public class LoginUserData {

    private final String username;

    /**
     * Creates output data for a successful login.
     *
     * @param username the username of the logged-in user
     */
    public LoginUserData(String username) {
        this.username = username;
    }

    /** Returns the username of the logged-in user. */
    public String getUsername() {
        return username;
    }
}
