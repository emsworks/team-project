package use_case.edit_entry;

import entity.Activity;
import entity.TimeEntry;

/*
 * EditEntryInteractor
 * --------------------
 * Contains the business logic for the Edit Entry use case.
 * It loads the activity, finds the time entry, updates its start and end
 * times, and persists the activity.
 *
 * On success it notifies the presenter via the output boundary.
 * On failure it returns a failure message so the presenter can display it.
 *
 * Main flow:
 *  1. Validate that the entry id is provided.
 *  2. Load the activity.
 *  3. Find the time entry.
 *  4. Update the entry's times.
 *  5. Save the activity.
 *
 * Alternative flows:
 *  - Missing entry id → failure message
 *  - Activity not found → failure message
 *  - Entry not found → failure message
 *  - End before start → failure message
 */
public class EditEntryInteractor implements EditEntryInputBoundary {

    private final EditEntryDataAccessInterface dataAccessInterface;
    private final EditEntryOutputBoundary outputBoundary;

    /**
     * Creates the interactor with its dependencies injected.
     *
     * @param dataAccessInterface the storage interface for activities
     * @param outputBoundary      the presenter that receives the result
     */
    public EditEntryInteractor(
            EditEntryDataAccessInterface dataAccessInterface,
            EditEntryOutputBoundary outputBoundary) {
        this.dataAccessInterface = dataAccessInterface;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(EditEntryRequestData request) {
        // Validate entry id.
        if (request.getEntryId() == null || request.getEntryId().isBlank()) {
            outputBoundary.failure("Time entry id cannot be blank.");
            return;
        }

        // Load the activity.
        Activity activity = dataAccessInterface.retrieve(request.getUsername(), request.getActivityId())
                .orElse(null);
        if (activity == null) {
            outputBoundary.failure("Activity not found.");
            return;
        }

        // Find the entry.
        TimeEntry entry = activity.getTimeEntry(request.getEntryId()).orElse(null);
        if (entry == null) {
            outputBoundary.failure("Time entry not found.");
            return;
        }

        // Validate start time.
        if (request.getNewStart() == null) {
            outputBoundary.failure("Start time cannot be null.");
            return;
        }

        // Update the entry's times.
        try {
            entry.setTimes(request.getNewStart(), request.getNewEnd());
        } catch (IllegalArgumentException e) {
            outputBoundary.failure(e.getMessage());
            return;
        }

        // Save the updated activity.
        dataAccessInterface.save(request.getUsername(), activity);

        // Success — notify presenter.
        outputBoundary.success(new EditEntryUserData(request.getEntryId()));
    }
}
