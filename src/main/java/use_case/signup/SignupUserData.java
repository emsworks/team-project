package use_case.signup;

/*
 * SignupUserData
 * ---------------
 * Immutable data returned by the Sign Up interactor after a successful
 * sign-up.  The presenter uses this to update the view (e.g. pre-fill
 * the username on the login screen).
 */
public class SignupUserData {

    private final String username;

    /**
     * Creates output data for a successful sign-up.
     *
     * @param username the username that was just created
     */
    public SignupUserData(String username) {
        this.username = username;
    }

    /** Returns the username of the newly created account. */
    public String getUsername() {
        return username;
    }
}
