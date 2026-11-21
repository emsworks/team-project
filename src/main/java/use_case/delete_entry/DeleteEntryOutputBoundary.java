package use_case.delete_entry;

/*
 * DeleteEntryOutputBoundary
 * ---------------------------
 * The output boundary that the Delete Entry interactor calls to deliver results
 * to the presenter.  The presenter implements this interface and is
 * responsible for updating the view.
 *
 * Main functions:
 *  - success(userData)   : entry deleted; refresh entry list
 *  - failure(message)    : deletion failed; show error message
 */
public interface DeleteEntryOutputBoundary {

    /**
     * Called when the entry is deleted successfully.
     *
     * @param userData the data about the deleted entry
     */
    void success(DeleteEntryUserData userData);

    /**
     * Called when deletion fails.
     *
     * @param message the error message to display
     */
    void failure(String message);
}
