package use_case.view_summary;

import entity.Activity;
import entity.TimeEntry;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
 * ViewSummaryInteractor
 * ----------------------
 * Contains the business logic for the View Summary use case.
 * It loads all of the user's activities, filters entries by the requested
 * period, and computes time totals per activity and per category.
 *
 * On success it notifies the presenter via the output boundary.
 * On failure it returns a failure message so the presenter can display it.
 *
 * Main flow:
 *  1. Load all activities for the user.
 *  2. Aggregate time per activity and category for the period.
 *  3. Return the summary data.
 *
 * Alternative flows:
 *  - No entries in the period → empty summary (not an error)
 */
public class ViewSummaryInteractor implements ViewSummaryInputBoundary {

    private final ViewSummaryDataAccessInterface dataAccessInterface;
    private final ViewSummaryOutputBoundary outputBoundary;

    /**
     * Creates the interactor with its dependencies injected.
     *
     * @param dataAccessInterface the storage interface for activities
     * @param outputBoundary      the presenter that receives the result
     */
    public ViewSummaryInteractor(
            ViewSummaryDataAccessInterface dataAccessInterface,
            ViewSummaryOutputBoundary outputBoundary) {
        this.dataAccessInterface = dataAccessInterface;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(ViewSummaryRequestData request) {
        // Load all activities.
        List<Activity> activities = dataAccessInterface.retrieveAll(request.getUsername());

        // Aggregate time per activity.
        Map<String, Duration> timePerActivity = new LinkedHashMap<>();
        Map<String, Duration> timePerCategory = new LinkedHashMap<>();

        for (Activity activity : activities) {
            Duration total = Duration.ZERO;
            for (TimeEntry entry : activity.getTimeEntries()) {
                // TODO: filter entries by the requested period (daily/weekly/monthly)
                // For now, include all entries
                total = total.plus(entry.getDuration());
            }
            if (!total.isZero()) {
                timePerActivity.put(activity.getName(), total);
                // TODO: use activity category once ActivityCategory is implemented
            }
        }

        // Success — notify presenter.
        outputBoundary.success(new ViewSummaryUserData(
                timePerActivity, timePerCategory, request.getPeriod()));
    }
}
