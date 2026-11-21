package use_case.view_summary;

import entity.Activity;
import java.util.List;

/*
 * ViewSummaryDataAccessInterface
 * --------------------------------
 * The storage operations that the View Summary use case needs.
 * The interactor depends only on this interface; the real implementation
 * (file or database) lives in the data access layer.
 *
 * Main function:
 *  - retrieveAll(username) : load all of a user's activities with their entries
 */
public interface ViewSummaryDataAccessInterface {

    /**
     * Returns all of the user's activities (empty list if none).
     *
     * @param username the owner of the activities
     */
    List<Activity> retrieveAll(String username);
}
