package use_case.start_activity;

/*
 * StartActivityUserData
 * ----------------------
 * Immutable data returned by the Start Activity interactor after a successful
 * punch-in.  The presenter uses this to update the view (e.g. start the
 * live timer on the dashboard).
 */
public class StartActivityUserData {

    private final String activityName;

    /**
     * Creates output data for a successful punch-in.
     *
     * @param activityName the name of the activity that was started
     */
    public StartActivityUserData(String activityName) {
        this.activityName = activityName;
    }

    /** Returns the name of the started activity. */
    public String getActivityName() {
        return activityName;
    }
}
