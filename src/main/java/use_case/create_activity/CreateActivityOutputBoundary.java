package use_case.create_activity;

/*
 * CreateActivityOutputBoundary
 * ------------------------------
 * The output boundary that the Create Activity interactor calls to deliver results
 * to the presenter.  The presenter implements this interface and is
 * responsible for updating the view.
 *
 * Main functions:
 *  - success(userData)   : activity created; refresh activity list
 *  - failure(message)    : creation failed; show error message
 */
public interface CreateActivityOutputBoundary {

    /**
     * Called when the activity is created successfully.
     *
     * @param userData the data about the newly created activity
     */
    void success(CreateActivityUserData userData);

    /**
     * Called when creation fails (e.g. blank name, duplicate name).
     *
     * @param message the error message to display
     */
    void failure(String message);
}
