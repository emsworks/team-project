package use_case.delete_entry;

/*
 * DeleteEntryInputBoundary
 * -------------------------
 * Defines the contract that the Delete Entry interactor exposes to its caller
 * (the controller).  Implementations handle the business logic for deleting
 * a time entry from an activity.
 *
 * Main function:
 *  - execute(request) : delete a time entry
 */
public interface DeleteEntryInputBoundary {

    /**
     * Executes the delete entry use case with the provided request data.
     *
     * @param request the delete entry request
     */
    void execute(DeleteEntryRequestData request);
}
