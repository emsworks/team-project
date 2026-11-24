package data_access;

import entity.Activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/*
 * InMemoryActivityDataAccessObject
 * --------------------------------
 * Keeps each user's activities in memory (lost when the app closes).
 * Used in tests; the app itself uses FileActivityDataAccessObject.
 */
public class InMemoryActivityDataAccessObject implements ActivityDataAccessObject {
    // username -> (activity id -> activity), in insertion order.
    private final Map<String, Map<String, Activity>> byUser = new HashMap<>();

    @Override
    public Optional<Activity> getActivity(String username, String activityId) {
        return Optional.ofNullable(userActivities(username).get(activityId));
    }

    @Override
    public List<Activity> getActivities(String username) {
        return new ArrayList<>(userActivities(username).values());
    }

    @Override
    public boolean existsByName(String username, String activityName) {
        if (activityName == null) {
            return false;
        }
        for (Activity a : userActivities(username).values()) {
            if (a.getName().equalsIgnoreCase(activityName.trim())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void save(String username, Activity activity) {
        byUser.computeIfAbsent(username, u -> new LinkedHashMap<>())
                .put(activity.getId(), activity);
    }

    @Override
    public void delete(String username, String activityId) {
        userActivities(username).remove(activityId);
    }

    // Read-only view of one user's activities (empty if they have none).
    private Map<String, Activity> userActivities(String username) {
        return byUser.getOrDefault(username, Collections.emptyMap());
    }
}