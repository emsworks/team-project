package use_case.delete_account;

/*
 * DeleteAccountRequestData
 * -------------------------
 * Immutable data transferred from the controller to the Delete Account interactor.
 * Holds the username of the account to delete.
 */
public class DeleteAccountRequestData {

    private final String username;

    /**
     * Creates a new delete-account request.
     *
     * @param username the username of the account to delete
     */
    public DeleteAccountRequestData(String username) {
        this.username = username;
    }

    /** Returns the username. */
    public String getUsername() {
        return username;
    }
}
