package use_case.create_activity;

import entity.Activity;

/*
 * CreateActivityInteractor
 * -------------------------
 * Contains the business logic for the Create Activity use case.
 * It validates the activity name, checks for duplicates, creates the
 * activity entity, and persists it through the data access interface.
 *
 * On success it notifies the presenter via the output boundary.
 * On failure it returns a failure message so the presenter can display it.
 *
 * Main flow:
 *  1. Validate that the activity name is non-empty.
 *  2. Check that the name is not a duplicate for this user.
 *  3. Create and save the activity.
 *
 * Alternative flows:
 *  - Blank name → failure message
 *  - Duplicate name → failure message
 */
public class CreateActivityInteractor implements CreateActivityInputBoundary {

    private final CreateActivityDataAccessInterface dataAccessInterface;
    private final CreateActivityOutputBoundary outputBoundary;

    /**
     * Creates the interactor with its dependencies injected.
     *
     * @param dataAccessInterface the storage interface for activities
     * @param outputBoundary      the presenter that receives the result
     */
    public CreateActivityInteractor(
            CreateActivityDataAccessInterface dataAccessInterface,
            CreateActivityOutputBoundary outputBoundary) {
        this.dataAccessInterface = dataAccessInterface;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(CreateActivityRequestData request) {
        // Validate non-empty name.
        if (request.getActivityName() == null || request.getActivityName().isBlank()) {
            outputBoundary.failure("Activity name cannot be blank.");
            return;
        }

        // Check for duplicate name.
        if (dataAccessInterface.existsByName(request.getUsername(), request.getActivityName())) {
            outputBoundary.failure("An activity with this name already exists.");
            return;
        }

        // Create and save the activity.
        Activity activity = new Activity(request.getActivityName());
        dataAccessInterface.save(request.getUsername(), activity);

        // Success — notify presenter.
        outputBoundary.success(new CreateActivityUserData(activity.getName()));
    }
}
