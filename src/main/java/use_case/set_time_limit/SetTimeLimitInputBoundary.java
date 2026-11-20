package use_case.set_time_limit;

/*
 * SetTimeLimitInputBoundary
 * ---------------------------
 * Defines the contract that the Set Time Limit interactor exposes to its caller
 * (the controller).  Implementations handle the business logic for setting
 * a time budget on an activity.
 *
 * Main function:
 *  - execute(request) : set a time limit on an activity
 */
public interface SetTimeLimitInputBoundary {

    /**
     * Executes the set time limit use case with the provided request data.
     *
     * @param request the set time limit request
     */
    void execute(SetTimeLimitRequestData request);
}
