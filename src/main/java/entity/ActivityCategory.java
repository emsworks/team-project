package entity;

/**
 * Represents a category used to classify an activity.
 */
public class ActivityCategory {

    private final String name;

    public ActivityCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
