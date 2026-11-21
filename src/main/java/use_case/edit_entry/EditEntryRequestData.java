package use_case.edit_entry;

import java.time.LocalDateTime;

/*
 * EditEntryRequestData
 * ---------------------
 * Immutable data transferred from the controller to the Edit Entry interactor.
 * Holds the identifiers and new times for editing a time entry.
 */
public class EditEntryRequestData {

    private final String username;
    private final String activityId;
    private final String entryId;
    private final LocalDateTime newStart;
    private final LocalDateTime newEnd;

    /**
     * Creates a new edit-entry request.
     *
     * @param username   the owner of the activity
     * @param activityId the id of the activity containing the entry
     * @param entryId    the id of the entry to edit
     * @param newStart   the new start time
     * @param newEnd     the new end time
     */
    public EditEntryRequestData(String username, String activityId,
                                String entryId, LocalDateTime newStart, LocalDateTime newEnd) {
        this.username = username;
        this.activityId = activityId;
        this.entryId = entryId;
        this.newStart = newStart;
        this.newEnd = newEnd;
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

    public LocalDateTime getNewStart() {
        return newStart;
    }

    public LocalDateTime getNewEnd() {
        return newEnd;
    }
}
