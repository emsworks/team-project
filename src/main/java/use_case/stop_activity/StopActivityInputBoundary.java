package use_case.stop_activity;

/*
 * StopActivityInputBoundary
 * --------------------------
 * Defines the contract that the Stop Activity (Punch Out) interactor exposes
 * to its caller (the controller).  Implementations handle the business logic
 * for stopping the currently running time entry on an activity.
 *
 * Main function:
 *  - execute(request) : punch out from an activity
 */
public interface StopActivityInputBoundary {

    /**
     * Executes the stop activity use case with the provided request data.
     *
     * @param request the stop activity request containing username and activity id
     */
    void execute(StopActivityRequestData request);
}
