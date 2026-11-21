package use_case.stop_activity;

/*
 * StopActivityOutputBoundary
 * -----------------------------
 * The output boundary that the Stop Activity interactor calls to deliver results
 * to the presenter.  The presenter implements this interface and is
 * responsible for updating the view.
 *
 * Main functions:
 *  - success(userData)   : punch-out succeeded; stop live timer
 *  - failure(message)    : punch-out failed; show error message
 */
public interface StopActivityOutputBoundary {

    /**
     * Called when the punch-out succeeds.
     *
     * @param userData the data about the stopped activity
     */
    void success(StopActivityUserData userData);

    /**
     * Called when punch-out fails (e.g. no activity selected, no running entry).
     *
     * @param message the error message to display
     */
    void failure(String message);
}
