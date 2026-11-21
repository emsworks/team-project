package use_case.delete_account;

/*
 * DeleteAccountInputBoundary
 * ----------------------------
 * Defines the contract that the Delete Account interactor exposes to its caller
 * (the controller).  Implementations handle the business logic for deleting
 * a user account and all of their data.
 *
 * Main function:
 *  - execute(request) : delete a user account and all associated data
 */
public interface DeleteAccountInputBoundary {

    /**
     * Executes the delete account use case with the provided request data.
     *
     * @param request the delete account request containing the username
     */
    void execute(DeleteAccountRequestData request);
}
