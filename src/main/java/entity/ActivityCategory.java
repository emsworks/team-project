package entity;

import java.util.List;
import java.util.Locale;

/*
 * ActivityCategory
 * ----------------
 * A category an activity belongs to (e.g. "Studying", "Entertainment").
 *
 * A fixed set of predefined categories is provided as constants. That list is
 * also what gets sent to the external categorizer, so its answer can be
 * checked against known values. Custom categories can still be created with
 * the constructor.
 *
 * Category names are compared case-insensitively, so "study" and "Study"
 * are the same category.
 *
 * Main functions:
 *  - ActivityCategory(name)  : create a category (name cannot be blank)
 *  - getName()               : read the category's name
 *  - defaults()              : the predefined categories to offer the categorizer
 *  - fromLabel(label)        : turn a text label (e.g. from an API reply) into a category
 *  - UNCATEGORIZED           : "not categorized yet" (not offered to the categorizer)
 *  - OTHER                   : "none of the categories fit"
 *  - equals() / hashCode()   : compare categories by name, ignoring case
 *
 * This class only depends on the standard library, so it has no knowledge
 * of the GUI, database, API, or any outer layer.
 */
public class ActivityCategory {
    // Predefined categories.
    public static final ActivityCategory STUDYING = new ActivityCategory("Studying");
    public static final ActivityCategory ENTERTAINMENT = new ActivityCategory("Entertainment");
    public static final ActivityCategory EATING = new ActivityCategory("Eating");
    public static final ActivityCategory WORK = new ActivityCategory("Work");
    public static final ActivityCategory EXERCISE = new ActivityCategory("Exercise");
    public static final ActivityCategory SLEEP = new ActivityCategory("Sleep");
    public static final ActivityCategory SOCIAL = new ActivityCategory("Social");
    public static final ActivityCategory CHORES = new ActivityCategory("Chores");
    public static final ActivityCategory TRAVEL = new ActivityCategory("Travel");
    public static final ActivityCategory PERSONAL_CARE = new ActivityCategory("Personal care");

    // Catch-all when the categorizer works but nothing fits.
    public static final ActivityCategory OTHER = new ActivityCategory("Other");

    // Default for activities that haven't been categorized yet (e.g. the API failed).
    public static final ActivityCategory UNCATEGORIZED = new ActivityCategory("Uncategorized");

    // The categories offered to the categorizer (UNCATEGORIZED is deliberately left out).
    private static final List<ActivityCategory> DEFAULTS = List.of(
            STUDYING, ENTERTAINMENT, EATING, WORK, EXERCISE, SLEEP,
            SOCIAL, CHORES, TRAVEL, PERSONAL_CARE, OTHER);

    // The category's display name (trimmed, never blank).
    private final String name;

    /**
     * Creates a category with the given name.
     * Throws if the name is null or blank.
     */
    public ActivityCategory(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Category name cannot be blank");
        }
        this.name = name.trim();
    }
}
