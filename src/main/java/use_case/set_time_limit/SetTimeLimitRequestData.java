package use_case.set_time_limit;

import java.time.Duration;

/*
 * SetTimeLimitRequestData
 * ------------------------
 * Immutable data transferred from the controller to the Set Time Limit interactor.
 * Holds the activity id and the desired time budget.
 */
public class SetTimeLimitRequestData {

    private final String username;
    private final String activityId;
    private final Duration timeLimit;

    /**
     * Creates a new set-time-limit request.
     *
     * @param username   the owner of the activity
     * @param activityId the id of the activity
     * @param timeLimit  the desired time budget (null to remove the limit)
     */
    public SetTimeLimitRequestData(String username, String activityId, Duration timeLimit) {
        this.username = username;
        this.activityId = activityId;
        this.timeLimit = timeLimit;
    }

    public String getUsername() {
        return username;
    }

    public String getActivityId() {
        return activityId;
    }

    public Duration getTimeLimit() {
        return timeLimit;
    }
}
