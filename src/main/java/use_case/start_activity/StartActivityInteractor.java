package use_case.start_activity;

import entity.Activity;
import entity.TimeEntry;

/*
 * StartActivityInteractor
 * ------------------------
 * Contains the business logic for the Start Activity (Punch In) use case.
 * It loads the activity, checks that no entry is already running, creates
 * a new running time entry, and persists the activity.
 *
 * On success it notifies the presenter via the output boundary.
 * On failure it returns a failure message so the presenter can display it.
 *
 * Main flow:
 *  1. Validate that an activity id is provided.
 *  2. Load the activity.
 *  3. Check that no entry is already running.
 *  4. Create a new running TimeEntry and add it to the activity.
 *  5. Save the activity.
 *
 * Alternative flows:
 *  - No activity selected → failure message
 *  - Activity not found → failure message
 *  - Already running → failure message
 */
public class StartActivityInteractor implements StartActivityInputBoundary {

    private final StartActivityDataAccessInterface dataAccessInterface;
    private final StartActivityOutputBoundary outputBoundary;

    /**
     * Creates the interactor with its dependencies injected.
     *
     * @param dataAccessInterface the storage interface for activities
     * @param outputBoundary      the presenter that receives the result
     */
    public StartActivityInteractor(
            StartActivityDataAccessInterface dataAccessInterface,
            StartActivityOutputBoundary outputBoundary) {
        this.dataAccessInterface = dataAccessInterface;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(StartActivityRequestData request) {
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

        // Check that no entry is already running.
        if (activity.getRunningEntry().isPresent()) {
            outputBoundary.failure("An activity is already running. Stop it first.");
            return;
        }

        // Create a new running time entry.
        TimeEntry entry = new TimeEntry(request.getCurrentTime());
        activity.addTimeEntry(entry);

        // Save the updated activity.
        dataAccessInterface.save(request.getUsername(), activity);

        // Success — notify presenter.
        outputBoundary.success(new StartActivityUserData(activity.getName()));
    }
}
