package use_case.delete_account;

/*
 * DeleteAccountUserData
 * ----------------------
 * Immutable data returned by the Delete Account interactor after a successful
 * deletion.  The presenter uses this to update the view (e.g. switch to the
 * login screen).
 */
public class DeleteAccountUserData {

    private final String username;

    /**
     * Creates output data for a successful account deletion.
     *
     * @param username the username that was deleted
     */
    public DeleteAccountUserData(String username) {
        this.username = username;
    }

    /** Returns the username that was deleted. */
    public String getUsername() {
        return username;
    }
}
