package data_access;

import entity.Activity;
import entity.TimeEntry;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/*
 * FileActivityDataAccessObject
 * ----------------------------
 * Saves every user's activities and their time entries to a JSON file, so the
 * dashboard's data survives closing the app. Drop-in replacement for
 * InMemoryActivityDataAccessObject (same behaviour, plus saving to disk).
 *
 * How it works:
 *  - On startup, the whole file is loaded into memory.
 *  - Reads (getActivity, getActivities, existsByName) use the in-memory copy.
 *  - Every save/delete updates memory, then rewrites the file.
 *
 * File format:
 * {
 *   "users": {
 *     "arish": [
 *       {
 *         "id": "...", "name": "Gaming", "timeBudgetSeconds": 3600,   (null = no limit)
 *         "entries": [ {"id": "...", "start": "2026-11-24T13:00", "end": "2026-11-24T14:00"} ]
 *       }                                                              (end null = still running)
 *     ]
 *   }
 * }
 */
public class FileActivityDataAccessObject implements ActivityDataAccessObject {
    private final Path filePath;

    // username -> (activity id -> activity), in insertion order.
    private final Map<String, Map<String, Activity>> byUser = new LinkedHashMap<>();

    public FileActivityDataAccessObject(String filePath) {
        this.filePath = Path.of(filePath);
        if (Files.exists(this.filePath)) {
            load();
        }
    }

    // ---------- ActivityDataAccessInterface ----------

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
        writeToFile();
    }

    @Override
    public void delete(String username, String activityId) {
        Map<String, Activity> activities = byUser.get(username);
        if (activities != null && activities.remove(activityId) != null) {
            writeToFile();
        }
    }

    /** Removes all of a user's activities (for Delete Account). */
    public void deleteAllForUser(String username) {
        if (byUser.remove(username) != null) {
            writeToFile();
        }
    }

    // Read-only view of one user's activities (empty if they have none).
    private Map<String, Activity> userActivities(String username) {
        return byUser.getOrDefault(username, Collections.emptyMap());
    }

    // ---------- reading the file ----------

    private void load() {
        String text;
        try {
            text = Files.readString(filePath);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not read " + filePath, e);
        }
        if (text.isBlank()) {
            return;
        }
        JSONObject users = new JSONObject(text).getJSONObject("users");
        for (String username : users.keySet()) {
            Map<String, Activity> activities = new LinkedHashMap<>();
            JSONArray array = users.getJSONArray(username);
            for (int i = 0; i < array.length(); i++) {
                Activity activity = activityFromJson(array.getJSONObject(i));
                activities.put(activity.getId(), activity);
            }
            byUser.put(username, activities);
        }
    }

    private static Activity activityFromJson(JSONObject obj) {
        Duration budget = obj.isNull("timeBudgetSeconds")
                ? null : Duration.ofSeconds(obj.getLong("timeBudgetSeconds"));
        Activity activity = new Activity(obj.getString("id"), obj.getString("name"), budget);

        JSONArray entries = obj.getJSONArray("entries");
        for (int i = 0; i < entries.length(); i++) {
            JSONObject e = entries.getJSONObject(i);
            LocalDateTime start = LocalDateTime.parse(e.getString("start"));
            LocalDateTime end = e.isNull("end") ? null : LocalDateTime.parse(e.getString("end"));
            activity.addTimeEntry(new TimeEntry(e.getString("id"), start, end));
        }
        return activity;
    }

    // ---------- writing the file ----------

    private void writeToFile() {
        JSONObject users = new JSONObject();
        for (Map.Entry<String, Map<String, Activity>> user : byUser.entrySet()) {
            JSONArray array = new JSONArray();
            for (Activity activity : user.getValue().values()) {
                array.put(activityToJson(activity));
            }
            users.put(user.getKey(), array);
        }
        JSONObject root = new JSONObject();
        root.put("users", users);

        // Write to a temp file first, then swap it in, so a crash mid-write
        // can't leave a half-written (corrupt) activities.json behind.
        try {
            Path temp = filePath.resolveSibling(filePath.getFileName() + ".tmp");
            Files.writeString(temp, root.toString(2));
            Files.move(temp, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not write " + filePath, e);
        }
    }

    private static JSONObject activityToJson(Activity activity) {
        JSONObject obj = new JSONObject();
        obj.put("id", activity.getId());
        obj.put("name", activity.getName());
        obj.put("timeBudgetSeconds", activity.getTimeBudget()
                .<Object>map(Duration::getSeconds).orElse(JSONObject.NULL));

        JSONArray entries = new JSONArray();
        for (TimeEntry entry : activity.getTimeEntries()) {
            JSONObject e = new JSONObject();
            e.put("id", entry.getId());
            e.put("start", entry.getStart().toString());
            e.put("end", entry.getEnd() == null ? JSONObject.NULL : entry.getEnd().toString());
            entries.put(e);
        }
        obj.put("entries", entries);
        return obj;
    }
}
