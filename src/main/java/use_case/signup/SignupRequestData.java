package use_case.signup;

/*
 * SignupRequestData
 * ------------------
 * Immutable data transferred from the controller to the Sign Up interactor.
 * Holds the username and password (entered twice for confirmation) that the
 * user provided on the sign-up screen.
 */
public class SignupRequestData {

    private final String username;
    private final String password;
    private final String passwordConfirmation;

    /**
     * Creates a new sign-up request.
     *
     * @param username             the desired username
     * @param password             the chosen password
     * @param passwordConfirmation the password repeated for confirmation
     */
    public SignupRequestData(String username, String password, String passwordConfirmation) {
        this.username = username;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }

    /** Returns the desired username. */
    public String getUsername() {
        return username;
    }

    /** Returns the chosen password. */
    public String getPassword() {
        return password;
    }

    /** Returns the password confirmation value. */
    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }
}
