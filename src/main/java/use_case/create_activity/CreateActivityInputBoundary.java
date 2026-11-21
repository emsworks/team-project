package use_case.create_activity;

/*
 * CreateActivityInputBoundary
 * ----------------------------
 * Defines the contract that the Create Activity interactor exposes to its caller
 * (the controller).  Implementations handle the business logic for creating
 * a new activity.
 *
 * Main function:
 *  - execute(request) : create a new activity for the user
 */
public interface CreateActivityInputBoundary {

    /**
     * Executes the create activity use case with the provided request data.
     *
     * @param request the create activity request containing username and activity name
     */
    void execute(CreateActivityRequestData request);
}
