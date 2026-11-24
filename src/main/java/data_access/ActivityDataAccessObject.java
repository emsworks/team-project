package data_access;

import entity.Activity;
import use_case.check_time_limit.CheckTimeLimitDataAccessInterface;
import use_case.create_activity.CreateActivityDataAccessInterface;
import use_case.dao_interfaces.ActivityDataAccessInterface;
import use_case.delete_activity.DeleteActivityDataAccessInterface;
import use_case.delete_entry.DeleteEntryDataAccessInterface;
import use_case.edit_entry.EditEntryDataAccessInterface;
import use_case.set_time_limit.SetTimeLimitDataAccessInterface;
import use_case.start_activity.StartActivityDataAccessInterface;
import use_case.stop_activity.StopActivityDataAccessInterface;
import use_case.view_summary.ViewSummaryDataAccessInterface;

import java.util.List;
import java.util.Optional;

/*
 * ActivityDataAccessObject
 * ------------------------
 * One storage type that every activity use case can use. It combines the shared
 * ActivityDataAccessInterface with each use case's own small interface, so a single
 * object (in-memory or file) can be passed to every interactor in AppBuilder.
 *
 * The per-use-case interfaces call the same operations by different names
 * (retrieve vs getActivity, retrieveAll vs getActivities). The default methods
 * below map those names onto the shared ones, so implementations only write
 * the five shared methods.
 */
public interface ActivityDataAccessObject extends ActivityDataAccessInterface,
        CreateActivityDataAccessInterface, DeleteActivityDataAccessInterface,
        StartActivityDataAccessInterface, StopActivityDataAccessInterface,
        EditEntryDataAccessInterface, DeleteEntryDataAccessInterface,
        SetTimeLimitDataAccessInterface, CheckTimeLimitDataAccessInterface,
        ViewSummaryDataAccessInterface {

    /** Same as getActivity; the name several use cases expect. */
    @Override
    default Optional<Activity> retrieve(String username, String activityId) {
        return getActivity(username, activityId);
    }

    /** Same as getActivities; the name View Summary expects. */
    @Override
    default List<Activity> retrieveAll(String username) {
        return getActivities(username);
    }
}
