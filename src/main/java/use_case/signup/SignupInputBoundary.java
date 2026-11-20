package use_case.signup;

/*
 * SignupInputBoundary
 * -------------------
 * The input boundary (use case interface) for the Sign Up use case.
 *
 * Controllers call this interface to trigger the sign-up use case.
 * The interactor implements this interface, so the controller depends
 * only on an abstraction, not a concrete class.
 */
public interface SignupInputBoundary {

    /**
     * Executes the sign-up use case with the provided request data.
     *
     * @param request the sign-up request containing username and password
     */
    void execute(SignupRequestData request);
}
