package use_case.delete_activity;

/*
 * DeleteActivityInteractor
 * -------------------------
 * Contains the business logic for the Delete Activity use case.
 * It validates the activity id and deletes the activity and all of its
 * time entries through the data access interface.
 *
 * On success it notifies the presenter via the output boundary.
 * On failure it returns a failure message so the presenter can display it.
 *
 * Main flow:
 *  1. Validate that the activity id is provided.
 *  2. Delete the activity and all its time entries.
 *
 * Alternative flows:
 *  - Missing activity id → failure message
 */
public class DeleteActivityInteractor implements DeleteActivityInputBoundary {

    private final DeleteActivityDataAccessInterface dataAccessInterface;
    private final DeleteActivityOutputBoundary outputBoundary;

    /**
     * Creates the interactor with its dependencies injected.
     *
     * @param dataAccessInterface the storage interface for activities
     * @param outputBoundary      the presenter that receives the result
     */
    public DeleteActivityInteractor(
            DeleteActivityDataAccessInterface dataAccessInterface,
            DeleteActivityOutputBoundary outputBoundary) {
        this.dataAccessInterface = dataAccessInterface;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(DeleteActivityRequestData request) {
        // Validate activity id.
        if (request.getActivityId() == null || request.getActivityId().isBlank()) {
            outputBoundary.failure("Activity id cannot be blank.");
            return;
        }

        // Delete the activity.
        dataAccessInterface.delete(request.getUsername(), request.getActivityId());

        // Success — notify presenter.
        outputBoundary.success(new DeleteActivityUserData(request.getActivityId()));
    }
}
