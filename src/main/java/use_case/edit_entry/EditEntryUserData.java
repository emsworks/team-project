package use_case.edit_entry;

/*
 * EditEntryUserData
 * ------------------
 * Immutable data returned by the Edit Entry interactor after a successful
 * edit.  The presenter uses this to update the view (e.g. refresh the
 * entry list).
 */
public class EditEntryUserData {

    private final String entryId;

    /**
     * Creates output data for a successful edit.
     *
     * @param entryId the id of the entry that was edited
     */
    public EditEntryUserData(String entryId) {
        this.entryId = entryId;
    }

    /** Returns the id of the edited entry. */
    public String getEntryId() {
        return entryId;
    }
}
