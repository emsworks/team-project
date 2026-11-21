package use_case.delete_entry;

import entity.Activity;
import entity.TimeEntry;

/*
 * DeleteEntryInteractor
 * ----------------------
 * Contains the business logic for the Delete Entry use case.
 * It loads the activity, finds the time entry, removes it, and persists
 * the activity.
 *
 * On success it notifies the presenter via the output boundary.
 * On failure it returns a failure message so the presenter can display it.
 *
 * Main flow:
 *  1. Validate that the entry id is provided.
 *  2. Load the activity.
 *  3. Find and remove the time entry.
 *  4. Save the activity.
 *
 * Alternative flows:
 *  - Missing entry id → failure message
 *  - Activity not found → failure message
 *  - Entry not found → failure message
 */
public class DeleteEntryInteractor implements DeleteEntryInputBoundary {

    private final DeleteEntryDataAccessInterface dataAccessInterface;
    private final DeleteEntryOutputBoundary outputBoundary;

    /**
     * Creates the interactor with its dependencies injected.
     *
     * @param dataAccessInterface the storage interface for activities
     * @param outputBoundary      the presenter that receives the result
     */
    public DeleteEntryInteractor(
            DeleteEntryDataAccessInterface dataAccessInterface,
            DeleteEntryOutputBoundary outputBoundary) {
        this.dataAccessInterface = dataAccessInterface;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(DeleteEntryRequestData request) {
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

        // Remove the entry.
        activity.removeTimeEntry(request.getEntryId());

        // Save the updated activity.
        dataAccessInterface.save(request.getUsername(), activity);

        // Success — notify presenter.
        outputBoundary.success(new DeleteEntryUserData(request.getEntryId()));
    }
}
