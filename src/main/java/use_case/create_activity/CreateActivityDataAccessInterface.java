package use_case.create_activity;

import entity.Activity;
import java.util.Optional;

/*
 * CreateActivityDataAccessInterface
 * ----------------------------------
 * The storage operations that the Create Activity use case needs.
 * The interactor depends only on this interface; the real implementation
 * (file or database) lives in the data access layer.
 *
 * Main functions:
 *  - existsByName(username, name) : check for a duplicate activity name
 *  - save(username, activity)     : persist a new activity
 */
public interface CreateActivityDataAccessInterface {

    /**
     * Returns true if the user already has an activity with this name, ignoring case.
     *
     * @param username      the owner of the activity
     * @param activityName  the name to check
     */
    boolean existsByName(String username, String activityName);

    /**
     * Saves a new activity for the user.
     *
     * @param username  the owner of the activity
     * @param activity  the activity to save
     */
    void save(String username, Activity activity);
}
