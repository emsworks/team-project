package use_case.delete_entry;

/*
 * DeleteEntryUserData
 * --------------------
 * Immutable data returned by the Delete Entry interactor after a successful
 * deletion.  The presenter uses this to update the view (e.g. refresh the
 * entry list).
 */
public class DeleteEntryUserData {

    private final String entryId;

    /**
     * Creates output data for a successful deletion.
     *
     * @param entryId the id of the entry that was deleted
     */
    public DeleteEntryUserData(String entryId) {
        this.entryId = entryId;
    }

    /** Returns the id of the deleted entry. */
    public String getEntryId() {
        return entryId;
    }
}
