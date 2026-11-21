package use_case.stop_activity;

import entity.Activity;
import java.util.Optional;

/*
 * StopActivityDataAccessInterface
 * --------------------------------
 * The storage operations that the Stop Activity (Punch Out) use case needs.
 * The interactor depends only on this interface; the real implementation
 * (file or database) lives in the data access layer.
 *
 * Main functions:
 *  - retrieve(username, activityId) : load an activity
 *  - save(username, activity)       : persist the updated activity
 */
public interface StopActivityDataAccessInterface {

    /**
     * Returns the user's activity with this id, or empty if it doesn't exist.
     *
     * @param username    the owner of the activity
     * @param activityId  the id of the activity to load
     */
    Optional<Activity> retrieve(String username, String activityId);

    /**
     * Saves the updated activity back to storage.
     *
     * @param username  the owner of the activity
     * @param activity  the activity to save
     */
    void save(String username, Activity activity);
}
