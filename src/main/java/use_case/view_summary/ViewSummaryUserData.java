package use_case.view_summary;

import java.time.Duration;
import java.util.List;
import java.util.Map;

/*
 * ViewSummaryUserData
 * --------------------
 * Immutable data returned by the View Summary interactor.  Contains the
 * time totals per activity and per category for the requested period.
 */
public class ViewSummaryUserData {

    /** Time spent on each activity during the period. */
    private final Map<String, Duration> timePerActivity;

    /** Time spent in each category during the period. */
    private final Map<String, Duration> timePerCategory;

    /** The period that was queried. */
    private final SummaryPeriod period;

    /**
     * Creates output data for a summary query.
     *
     * @param timePerActivity  map of activity name → total duration
     * @param timePerCategory  map of category name → total duration
     * @param period           the period that was queried
     */
    public ViewSummaryUserData(Map<String, Duration> timePerActivity,
                               Map<String, Duration> timePerCategory,
                               SummaryPeriod period) {
        this.timePerActivity = timePerActivity;
        this.timePerCategory = timePerCategory;
        this.period = period;
    }

    /** Returns the time spent on each activity. */
    public Map<String, Duration> getTimePerActivity() {
        return timePerActivity;
    }

    /** Returns the time spent in each category. */
    public Map<String, Duration> getTimePerCategory() {
        return timePerCategory;
    }

    /** Returns the queried period. */
    public SummaryPeriod getPeriod() {
        return period;
    }
}
