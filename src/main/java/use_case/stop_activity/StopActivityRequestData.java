package use_case.stop_activity;

/*
 * StopActivityRequestData
 * ------------------------
 * Immutable data transferred from the controller to the Stop Activity interactor.
 * Holds the username and activity id for the punch-out action.
 */
public class StopActivityRequestData {

    private final String username;
    private final String activityId;

    /**
     * Creates a new stop-activity request.
     *
     * @param username   the owner of the activity
     * @param activityId the id of the activity to stop
     */
    public StopActivityRequestData(String username, String activityId) {
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
