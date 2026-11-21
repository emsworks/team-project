package use_case.view_summary;

/*
 * ViewSummaryOutputBoundary
 * ---------------------------
 * The output boundary that the View Summary interactor calls to deliver results
 * to the presenter.  The presenter implements this interface and is
 * responsible for updating the view (e.g. rendering the chart).
 *
 * Main functions:
 *  - success(userData)   : summary computed; display chart
 *  - failure(message)    : summary failed; show error message
 */
public interface ViewSummaryOutputBoundary {

    /**
     * Called when the summary is computed successfully.
     *
     * @param userData the summary data with time totals
     */
    void success(ViewSummaryUserData userData);

    /**
     * Called when the summary query fails.
     *
     * @param message the error message to display
     */
    void failure(String message);
}
