package use_case.start_activity;

/*
 * StartActivityRequestData
 * -------------------------
 * Immutable data transferred from the controller to the Start Activity interactor.
 * Holds the username and activity id for the punch-in action.
 */
public class StartActivityRequestData {

    private final String username;
    private final String activityId;

    /**
     * Creates a new start-activity request.
     *
     * @param username   the owner of the activity
     * @param activityId the id of the activity to start
     */
    public StartActivityRequestData(String username, String activityId) {
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
