package use_case.check_time_limit;

import entity.Activity;
import java.util.Optional;

/*
 * CheckTimeLimitDataAccessInterface
 * -----------------------------------
 * The storage operations that the Check Time Limit use case needs.
 * The interactor depends only on this interface; the real implementation
 * (file or database) lives in the data access layer.
 *
 * Main function:
 *  - retrieve(username, activityId) : load an activity
 */
public interface CheckTimeLimitDataAccessInterface {

    /**
     * Returns the user's activity with this id, or empty if it doesn't exist.
     *
     * @param username    the owner of the activity
     * @param activityId  the id of the activity to load
     */
    Optional<Activity> retrieve(String username, String activityId);
}
