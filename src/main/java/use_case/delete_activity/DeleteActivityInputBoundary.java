package use_case.delete_activity;

/*
 * DeleteActivityInputBoundary
 * ----------------------------
 * Defines the contract that the Delete Activity interactor exposes to its caller
 * (the controller).  Implementations handle the business logic for deleting
 * an activity and all of its time entries.
 *
 * Main function:
 *  - execute(request) : delete an activity for the user
 */
public interface DeleteActivityInputBoundary {

    /**
     * Executes the delete activity use case with the provided request data.
     *
     * @param request the delete activity request containing username and activity id
     */
    void execute(DeleteActivityRequestData request);
}
