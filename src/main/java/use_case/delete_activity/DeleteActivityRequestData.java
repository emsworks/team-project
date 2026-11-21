package use_case.delete_activity;

/*
 * DeleteActivityRequestData
 * --------------------------
 * Immutable data transferred from the controller to the Delete Activity interactor.
 * Holds the username and activity id that identify the activity to delete.
 */
public class DeleteActivityRequestData {

    private final String username;
    private final String activityId;

    /**
     * Creates a new delete-activity request.
     *
     * @param username   the owner of the activity
     * @param activityId the id of the activity to delete
     */
    public DeleteActivityRequestData(String username, String activityId) {
        this.username = username;
        this.activityId = activityId;
    }

    /** Returns the username. */
    public String getUsername() {
        return username;
    }

    /** Returns the activity id. */
    public String getActivityId() {
        return activityId;
    }
}
