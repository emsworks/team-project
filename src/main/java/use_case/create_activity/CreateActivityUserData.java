package use_case.create_activity;

/*
 * CreateActivityUserData
 * -----------------------
 * Immutable data returned by the Create Activity interactor after a successful
 * creation.  The presenter uses this to update the view (e.g. refresh the
 * activity list on the dashboard).
 */
public class CreateActivityUserData {

    private final String activityName;

    /**
     * Creates output data for a successful activity creation.
     *
     * @param activityName the name of the activity that was created
     */
    public CreateActivityUserData(String activityName) {
        this.activityName = activityName;
    }

    /** Returns the name of the newly created activity. */
    public String getActivityName() {
        return activityName;
    }
}
