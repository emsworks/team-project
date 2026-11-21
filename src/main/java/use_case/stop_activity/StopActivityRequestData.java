package use_case.stop_activity;

import java.time.LocalDateTime;

/*
 * StopActivityRequestData
 * ------------------------
 * Immutable data transferred from the controller to the Stop Activity interactor.
 * Holds the username and activity id for the punch-out action.
 */
public class StopActivityRequestData {

    private final String username;
    private final String activityId;
    private final LocalDateTime currentTime;

    /**
     * Creates a new stop-activity request using the current system time.
     *
     * @param username   the owner of the activity
     * @param activityId the id of the activity to stop
     */
    public StopActivityRequestData(String username, String activityId) {
        this(username, activityId, LocalDateTime.now());
    }

    /**
     * Creates a new stop-activity request with an explicit time.
     * Useful for testing with deterministic time values.
     *
     * @param username   the owner of the activity
     * @param activityId the id of the activity to stop
     * @param currentTime the time to record as the stop time
     */
    public StopActivityRequestData(String username, String activityId, LocalDateTime currentTime) {
        this.username = username;
        this.activityId = activityId;
        this.currentTime = currentTime;
    }

    /** Returns the username. */
    public String getUsername() {
        return username;
    }

    /** Returns the activity id. */
    public String getActivityId() {
        return activityId;
    }

    /** Returns the current time to use for the time entry. */
    public LocalDateTime getCurrentTime() {
        return currentTime;
    }
}
