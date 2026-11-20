package use_case.edit_entry;

/*
 * EditEntryOutputBoundary
 * -------------------------
 * The output boundary that the Edit Entry interactor calls to deliver results
 * to the presenter.  The presenter implements this interface and is
 * responsible for updating the view.
 *
 * Main functions:
 *  - success(userData)   : entry edited; refresh entry list
 *  - failure(message)    : edit failed; show error message
 */
public interface EditEntryOutputBoundary {

    /**
     * Called when the entry is edited successfully.
     *
     * @param userData the data about the edited entry
     */
    void success(EditEntryUserData userData);

    /**
     * Called when edit fails (e.g. entry not found, invalid times).
     *
     * @param message the error message to display
     */
    void failure(String message);
}
