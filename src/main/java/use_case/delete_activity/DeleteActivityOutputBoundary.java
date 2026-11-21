package use_case.delete_activity;

/*
 * DeleteActivityOutputBoundary
 * ------------------------------
 * The output boundary that the Delete Activity interactor calls to deliver results
 * to the presenter.  The presenter implements this interface and is
 * responsible for updating the view.
 *
 * Main functions:
 *  - success(userData)   : activity deleted; refresh activity list
 *  - failure(message)    : deletion failed; show error message
 */
public interface DeleteActivityOutputBoundary {

    /**
     * Called when the activity is deleted successfully.
     *
     * @param userData the data about the deleted activity
     */
    void success(DeleteActivityUserData userData);

    /**
     * Called when deletion fails.
     *
     * @param message the error message to display
     */
    void failure(String message);
}
