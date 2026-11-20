package use_case.delete_activity;

/*
 * DeleteActivityUserData
 * -----------------------
 * Immutable data returned by the Delete Activity interactor after a successful
 * deletion.  The presenter uses this to update the view (e.g. remove the
 * activity from the list).
 */
public class DeleteActivityUserData {

    private final String activityId;

    /**
     * Creates output data for a successful activity deletion.
     *
     * @param activityId the id of the activity that was deleted
     */
    public DeleteActivityUserData(String activityId) {
        this.activityId = activityId;
    }

    /** Returns the id of the deleted activity. */
    public String getActivityId() {
        return activityId;
    }
}
