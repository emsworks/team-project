package use_case.check_time_limit;

import entity.Activity;
import java.time.Duration;

/*
 * CheckTimeLimitInteractor
 * -------------------------
 * Contains the business logic for the Check Time Limit use case.
 * It loads the activity and checks whether the total tracked time has
 * exceeded the activity's time budget.
 *
 * On success it notifies the presenter via the output boundary with the
 * over-budget status and remaining time.
 * On failure it returns a failure message so the presenter can display it.
 *
 * Main flow:
 *  1. Load the activity.
 *  2. Check if the activity is over budget.
 *  3. Compute remaining time.
 *  4. Return the result.
 *
 * Alternative flows:
 *  - Activity not found → failure message
 *  - No budget set → returns not over budget with null remaining
 */
public class CheckTimeLimitInteractor implements CheckTimeLimitInputBoundary {

    private final CheckTimeLimitDataAccessInterface dataAccessInterface;
    private final CheckTimeLimitOutputBoundary outputBoundary;

    /**
     * Creates the interactor with its dependencies injected.
     *
     * @param dataAccessInterface the storage interface for activities
     * @param outputBoundary      the presenter that receives the result
     */
    public CheckTimeLimitInteractor(
            CheckTimeLimitDataAccessInterface dataAccessInterface,
            CheckTimeLimitOutputBoundary outputBoundary) {
        this.dataAccessInterface = dataAccessInterface;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(CheckTimeLimitRequestData request) {
        // Load the activity.
        Activity activity = dataAccessInterface.retrieve(request.getUsername(), request.getActivityId())
                .orElse(null);
        if (activity == null) {
            outputBoundary.failure("Activity not found.");
            return;
        }

        // Check if a budget is set.
        Duration budget = activity.getTimeBudget().orElse(null);
        if (budget == null) {
            // No budget set — not over budget
            outputBoundary.success(new CheckTimeLimitUserData(activity.getName(), false, null));
            return;
        }

        // Compute remaining time.
        Duration totalTime = activity.getTotalTime();
        Duration remaining = budget.minus(totalTime);
        boolean isOver = remaining.isNegative() || remaining.isZero();

        // Success — notify presenter.
        outputBoundary.success(new CheckTimeLimitUserData(activity.getName(), isOver, remaining));
    }
}
