package use_case.categorize_activity;

/*
 * ActivityCategorizer
 * --------------------
 * Interface for categorizing an activity name into a high-level category
 * (e.g. "note-taking" → Study, "Fortnite" → Gaming).
 *
 * The use case layer depends only on this interface.  The data access layer
 * provides the concrete implementations:
 *  - ClaudeActivityCategorizer  : calls an external AI API
 *  - KeywordActivityCategorizer : offline keyword-based fallback
 *
 * Main function:
 *  - categorize(activityName) : return the best-matching category string
 */
public interface ActivityCategorizer {

    /**
     * Returns the category that best matches the given activity name.
     *
     * @param activityName the name of the activity to categorize
     * @return a category label (e.g. "Study", "Gaming", "Exercise")
     */
    String categorize(String activityName);
}
