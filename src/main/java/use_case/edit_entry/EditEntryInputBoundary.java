package use_case.edit_entry;

/*
 * EditEntryInputBoundary
 * -----------------------
 * Defines the contract that the Edit Entry interactor exposes to its caller
 * (the controller).  Implementations handle the business logic for editing
 * a past time entry's start and end times.
 *
 * Main function:
 *  - execute(request) : edit a time entry
 */
public interface EditEntryInputBoundary {

    /**
     * Executes the edit entry use case with the provided request data.
     *
     * @param request the edit entry request
     */
    void execute(EditEntryRequestData request);
}
