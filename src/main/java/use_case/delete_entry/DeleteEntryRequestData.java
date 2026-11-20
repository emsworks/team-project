package use_case.delete_entry;

/*
 * DeleteEntryRequestData
 * -----------------------
 * Immutable data transferred from the controller to the Delete Entry interactor.
 * Holds the identifiers for the entry to delete.
 */
public class DeleteEntryRequestData {

    private final String username;
    private final String activityId;
    private final String entryId;

    /**
     * Creates a new delete-entry request.
     *
     * @param username   the owner of the activity
     * @param activityId the id of the activity containing the entry
     * @param entryId    the id of the entry to delete
     */
    public DeleteEntryRequestData(String username, String activityId, String entryId) {
        this.username = username;
        this.activityId = activityId;
        this.entryId = entryId;
    }

    public String getUsername() {
        return username;
    }

    public String getActivityId() {
        return activityId;
    }

    public String getEntryId() {
        return entryId;
    }
}
