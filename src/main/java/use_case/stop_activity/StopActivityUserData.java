package use_case.stop_activity;

/*
 * StopActivityUserData
 * ---------------------
 * Immutable data returned by the Stop Activity interactor after a successful
 * punch-out.  The presenter uses this to update the view (e.g. stop the
 * live timer and update the dashboard totals).
 */
public class StopActivityUserData {

    private final String activityName;

    /**
     * Creates output data for a successful punch-out.
     *
     * @param activityName the name of the activity that was stopped
     */
    public StopActivityUserData(String activityName) {
        this.activityName = activityName;
    }

    /** Returns the name of the stopped activity. */
    public String getActivityName() {
        return activityName;
    }
}
