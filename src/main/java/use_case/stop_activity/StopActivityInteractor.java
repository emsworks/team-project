package use_case.stop_activity;

import entity.Activity;
import java.time.LocalDateTime;

/*
 * StopActivityInteractor
 * -----------------------
 * Contains the business logic for the Stop Activity (Punch Out) use case.
 * It loads the activity, finds the running time entry, stops it at the
 * current time, and persists the activity.
 *
 * On success it notifies the presenter via the output boundary.
 * On failure it returns a failure message so the presenter can display it.
 *
 * Main flow:
 *  1. Validate that an activity id is provided.
 *  2. Load the activity.
 *  3. Find the running entry and stop it.
 *  4. Save the activity.
 *
 * Alternative flows:
 *  - No activity selected → failure message
 *  - Activity not found → failure message
 *  - No running entry → failure message
 */
public class StopActivityInteractor implements StopActivityInputBoundary {

    private final StopActivityDataAccessInterface dataAccessInterface;
    private final StopActivityOutputBoundary outputBoundary;

    /**
     * Creates the interactor with its dependencies injected.
     *
     * @param dataAccessInterface the storage interface for activities
     * @param outputBoundary      the presenter that receives the result
     */
    public StopActivityInteractor(
            StopActivityDataAccessInterface dataAccessInterface,
            StopActivityOutputBoundary outputBoundary) {
        this.dataAccessInterface = dataAccessInterface;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(StopActivityRequestData request) {
        // Validate activity id.
        if (request.getActivityId() == null || request.getActivityId().isBlank()) {
            outputBoundary.failure("No activity is selected.");
            return;
        }

        // Load the activity.
        Activity activity = dataAccessInterface.retrieve(request.getUsername(), request.getActivityId())
                .orElse(null);
        if (activity == null) {
            outputBoundary.failure("Activity not found.");
            return;
        }

        // Find and stop the running entry.
        if (!activity.getRunningEntry().isPresent()) {
            outputBoundary.failure("No running entry to stop.");
            return;
        }
        activity.getRunningEntry().get().stop(LocalDateTime.now());

        // Save the updated activity.
        dataAccessInterface.save(request.getUsername(), activity);

        // Success — notify presenter.
        outputBoundary.success(new StopActivityUserData(activity.getName()));
    }
}
