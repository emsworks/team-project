package use_case.check_time_limit;

/*
 * CheckTimeLimitOutputBoundary
 * -------------------------------
 * The output boundary that the Check Time Limit interactor calls to deliver results
 * to the presenter.  The presenter implements this interface and is
 * responsible for updating the view (e.g. showing a notification).
 *
 * Main functions:
 *  - success(userData)   : check complete; show notification if over budget
 *  - failure(message)    : check failed; show error message
 */
public interface CheckTimeLimitOutputBoundary {

    /**
     * Called when the check completes successfully.
     *
     * @param userData the result of the time-limit check
     */
    void success(CheckTimeLimitUserData userData);

    /**
     * Called when the check fails.
     *
     * @param message the error message to display
     */
    void failure(String message);
}
