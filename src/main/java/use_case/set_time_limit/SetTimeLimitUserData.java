package use_case.set_time_limit;

import java.time.Duration;

/*
 * SetTimeLimitUserData
 * ---------------------
 * Immutable data returned by the Set Time Limit interactor after a successful
 * update.  The presenter uses this to update the view (e.g. display the
 * new limit on the activity card).
 */
public class SetTimeLimitUserData {

    private final String activityName;
    private final Duration timeLimit;

    /**
     * Creates output data for a successful time-limit update.
     *
     * @param activityName the name of the activity
     * @param timeLimit    the new time budget (null if removed)
     */
    public SetTimeLimitUserData(String activityName, Duration timeLimit) {
        this.activityName = activityName;
        this.timeLimit = timeLimit;
    }

    public String getActivityName() {
        return activityName;
    }

    public Duration getTimeLimit() {
        return timeLimit;
    }
}
