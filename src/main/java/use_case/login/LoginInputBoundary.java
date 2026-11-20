package use_case.login;

/*
 * LoginInputBoundary
 * --------------------
 * Defines the contract that the Log In interactor exposes to its caller
 * (the controller).  Implementations handle the business logic for
 * authenticating a user.
 *
 * Main function:
 *  - execute(request) : attempt to log in with the given credentials
 */
public interface LoginInputBoundary {

    /**
     * Executes the log-in use case with the provided request data.
     *
     * @param request the log-in request containing username and password
     */
    void execute(LoginRequestData request);
}
