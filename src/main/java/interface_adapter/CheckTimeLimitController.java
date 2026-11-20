package interface_adapter;

import use_case.check_time_limit.CheckTimeLimitInputBoundary;
import use_case.check_time_limit.CheckTimeLimitRequestData;

/** Called by the view to check whether an activity has gone over its time limit. */
public class CheckTimeLimitController {
    private final CheckTimeLimitInputBoundary interactor;

    public CheckTimeLimitController(CheckTimeLimitInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String username, String activityId) {
        interactor.execute(new CheckTimeLimitRequestData(username, activityId));
    }
}
