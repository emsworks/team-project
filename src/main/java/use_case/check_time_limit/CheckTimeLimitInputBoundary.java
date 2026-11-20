package use_case.check_time_limit;

/*
 * CheckTimeLimitInputBoundary
 * ------------------------------
 * Defines the contract that the Check Time Limit interactor exposes to its caller
 * (the controller).  Implementations handle the business logic for checking
 * whether an activity has exceeded its time budget.
 *
 * Main function:
 *  - execute(request) : check if an activity is over its time limit
 */
public interface CheckTimeLimitInputBoundary {

    /**
     * Executes the check time limit use case with the provided request data.
     *
     * @param request the check time limit request
     */
    void execute(CheckTimeLimitRequestData request);
}
