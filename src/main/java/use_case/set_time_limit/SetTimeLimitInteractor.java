package use_case.set_time_limit;

import entity.Activity;
import java.time.Duration;

/*
 * SetTimeLimitInteractor
 * -----------------------
 * Contains the business logic for the Set Time Limit use case.
 * It loads the activity, sets the time budget, and persists the activity.
 *
 * On success it notifies the presenter via the output boundary.
 * On failure it returns a failure message so the presenter can display it.
 *
 * Main flow:
 *  1. Validate that the time limit is positive (or null to remove).
 *  2. Load the activity.
 *  3. Set the time budget.
 *  4. Save the activity.
 *
 * Alternative flows:
 *  - Activity not found → failure message
 *  - Invalid limit → failure message
 */
public class SetTimeLimitInteractor implements SetTimeLimitInputBoundary {

    private final SetTimeLimitDataAccessInterface dataAccessInterface;
    private final SetTimeLimitOutputBoundary outputBoundary;

    /**
     * Creates the interactor with its dependencies injected.
     *
     * @param dataAccessInterface the storage interface for activities
     * @param outputBoundary      the presenter that receives the result
     */
    public SetTimeLimitInteractor(
            SetTimeLimitDataAccessInterface dataAccessInterface,
            SetTimeLimitOutputBoundary outputBoundary) {
        this.dataAccessInterface = dataAccessInterface;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(SetTimeLimitRequestData request) {
        // Validate time limit.
        Duration timeLimit = request.getTimeLimit();
        if (timeLimit != null && (timeLimit.isNegative() || timeLimit.isZero())) {
            outputBoundary.failure("Time limit must be positive.");
            return;
        }

        // Load the activity.
        Activity activity = dataAccessInterface.retrieve(request.getUsername(), request.getActivityId())
                .orElse(null);
        if (activity == null) {
            outputBoundary.failure("Activity not found.");
            return;
        }

        // Set the time budget.
        activity.setTimeBudget(timeLimit);

        // Save the updated activity.
        dataAccessInterface.save(request.getUsername(), activity);

        // Success — notify presenter.
        outputBoundary.success(new SetTimeLimitUserData(activity.getName(), timeLimit));
    }
}
