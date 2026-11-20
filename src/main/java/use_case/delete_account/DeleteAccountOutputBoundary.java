package use_case.delete_account;

/*
 * DeleteAccountOutputBoundary
 * ------------------------------
 * The output boundary that the Delete Account interactor calls to deliver results
 * to the presenter.  The presenter implements this interface and is
 * responsible for updating the view.
 *
 * Main functions:
 *  - success(userData)   : account deleted; switch to login screen
 *  - failure(message)    : deletion failed; show error message
 */
public interface DeleteAccountOutputBoundary {

    /**
     * Called when the account is deleted successfully.
     *
     * @param userData the data about the deleted account
     */
    void success(DeleteAccountUserData userData);

    /**
     * Called when deletion fails.
     *
     * @param message the error message to display
     */
    void failure(String message);
}
