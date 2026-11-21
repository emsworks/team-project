package use_case.start_activity;

/*
 * StartActivityInputBoundary
 * ---------------------------
 * Defines the contract that the Start Activity (Punch In) interactor exposes
 * to its caller (the controller).  Implementations handle the business logic
 * for starting a new time entry on an activity.
 *
 * Main function:
 *  - execute(request) : punch in to an activity
 */
public interface StartActivityInputBoundary {

    /**
     * Executes the start activity use case with the provided request data.
     *
     * @param request the start activity request containing username and activity id
     */
    void execute(StartActivityRequestData request);
}
