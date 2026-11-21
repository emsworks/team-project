package use_case.start_activity;

/*
 * StartActivityOutputBoundary
 * ------------------------------
 * The output boundary that the Start Activity interactor calls to deliver results
 * to the presenter.  The presenter implements this interface and is
 * responsible for updating the view.
 *
 * Main functions:
 *  - success(userData)   : punch-in succeeded; start live timer
 *  - failure(message)    : punch-in failed; show error message
 */
public interface StartActivityOutputBoundary {

    /**
     * Called when the punch-in succeeds.
     *
     * @param userData the data about the started activity
     */
    void success(StartActivityUserData userData);

    /**
     * Called when punch-in fails (e.g. no activity selected, already running).
     *
     * @param message the error message to display
     */
    void failure(String message);
}
