package use_case.create_activity;

/*
 * CreateActivityRequestData
 * --------------------------
 * Immutable data transferred from the controller to the Create Activity interactor.
 * Holds the username and activity name that the user provided.
 */
public class CreateActivityRequestData {

    private final String username;
    private final String activityName;

    /**
     * Creates a new create-activity request.
     *
     * @param username     the owner of the activity
     * @param activityName the name of the new activity
     */
    public CreateActivityRequestData(String username, String activityName) {
        this.username = username;
        this.activityName = activityName;
    }

    /** Returns the username. */
    public String getUsername() {
        return username;
    }

    /** Returns the activity name. */
    public String getActivityName() {
        return activityName;
    }
}
