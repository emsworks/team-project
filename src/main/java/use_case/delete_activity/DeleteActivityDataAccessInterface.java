package use_case.delete_activity;

import entity.Activity;
import java.util.Optional;

/*
 * DeleteActivityDataAccessInterface
 * ----------------------------------
 * The storage operations that the Delete Activity use case needs.
 * The interactor depends only on this interface; the real implementation
 * (file or database) lives in the data access layer.
 *
 * Main function:
 *  - delete(username, activityId) : remove an activity and all its time entries
 */
public interface DeleteActivityDataAccessInterface {

    /**
     * Permanently removes the activity and all of its time entries.
     *
     * @param username    the owner of the activity
     * @param activityId  the id of the activity to delete
     */
    void delete(String username, String activityId);
}
