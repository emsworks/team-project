package use_case.check_time_limit;

/*
 * CheckTimeLimitRequestData
 * --------------------------
 * Immutable data transferred from the controller to the Check Time Limit interactor.
 * Holds the activity id to check.
 */
public class CheckTimeLimitRequestData {

    private final String username;
    private final String activityId;

    /**
     * Creates a new check-time-limit request.
     *
     * @param username   the owner of the activity
     * @param activityId the id of the activity to check
     */
    public CheckTimeLimitRequestData(String username, String activityId) {
        this.username = username;
        this.activityId = activityId;
    }

    public String getUsername() {
        return username;
    }

    public String getActivityId() {
        return activityId;
    }
}
