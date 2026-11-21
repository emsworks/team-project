package use_case.view_summary;

/*
 * ViewSummaryRequestData
 * -----------------------
 * Immutable data transferred from the controller to the View Summary interactor.
 * Holds the username and the period for which the summary is requested.
 */
public class ViewSummaryRequestData {

    private final String username;
    private final SummaryPeriod period;

    /**
     * Creates a new view-summary request.
     *
     * @param username the owner of the activities
     * @param period   the time period (daily, weekly, monthly)
     */
    public ViewSummaryRequestData(String username, SummaryPeriod period) {
        this.username = username;
        this.period = period;
    }

    /** Returns the username. */
    public String getUsername() {
        return username;
    }

    /** Returns the summary period. */
    public SummaryPeriod getPeriod() {
        return period;
    }
}
