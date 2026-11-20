package use_case.login;

/*
 * LoginRequestData
 * -----------------
 * Immutable data transferred from the controller to the Log In interactor.
 * Holds the username and password that the user entered on the login screen.
 */
public class LoginRequestData {

    private final String username;
    private final String password;

    /**
     * Creates a new log-in request.
     *
     * @param username the username to log in with
     * @param password the password to verify
     */
    public LoginRequestData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /** Returns the username. */
    public String getUsername() {
        return username;
    }

    /** Returns the password. */
    public String getPassword() {
        return password;
    }
}
