package use_case.set_time_limit;

/*
 * SetTimeLimitOutputBoundary
 * -----------------------------
 * The output boundary that the Set Time Limit interactor calls to deliver results
 * to the presenter.  The presenter implements this interface and is
 * responsible for updating the view.
 *
 * Main functions:
 *  - success(userData)   : limit set; update activity card
 *  - failure(message)    : failed; show error message
 */
public interface SetTimeLimitOutputBoundary {

    /**
     * Called when the time limit is set successfully.
     *
     * @param userData the data about the updated activity
     */
    void success(SetTimeLimitUserData userData);

    /**
     * Called when setting the limit fails (e.g. invalid limit).
     *
     * @param message the error message to display
     */
    void failure(String message);
}
