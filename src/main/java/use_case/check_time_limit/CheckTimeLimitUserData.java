package use_case.check_time_limit;

import java.time.Duration;

/*
 * CheckTimeLimitUserData
 * -----------------------
 * Immutable data returned by the Check Time Limit interactor.  Indicates
 * whether the activity is over its time budget and by how much.
 */
public class CheckTimeLimitUserData {

    private final String activityName;
    private final boolean isOverBudget;
    private final Duration timeRemaining;

    /**
     * Creates output data for a time-limit check.
     *
     * @param activityName   the name of the activity
     * @param isOverBudget   true if the activity has exceeded its budget
     * @param timeRemaining  time left (negative if over budget)
     */
    public CheckTimeLimitUserData(String activityName, boolean isOverBudget, Duration timeRemaining) {
        this.activityName = activityName;
        this.isOverBudget = isOverBudget;
        this.timeRemaining = timeRemaining;
    }

    public String getActivityName() {
        return activityName;
    }

    public boolean isOverBudget() {
        return isOverBudget;
    }

    public Duration getTimeRemaining() {
        return timeRemaining;
    }
}
