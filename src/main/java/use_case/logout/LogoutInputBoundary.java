package use_case.logout;

/*
 * LogoutInputBoundary
 * --------------------
 * Defines the contract that the Log Out interactor exposes to its caller
 * (the controller).  Implementations handle the business logic for
 * ending the current user session.
 *
 * Main function:
 *  - execute() : log out the current user
 */
public interface LogoutInputBoundary {

    /**
     * Executes the log-out use case, clearing the current user session.
     */
    void execute();
}
