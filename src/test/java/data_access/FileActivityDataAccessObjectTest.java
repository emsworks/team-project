package data_access;

import entity.Activity;
import entity.TimeEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileActivityDataAccessObjectTest {
    private static final LocalDateTime NINE = LocalDateTime.of(2026, 11, 24, 9, 0);
    private static final LocalDateTime TEN = LocalDateTime.of(2026, 11, 24, 10, 0);

    @Test
    void activitiesAndEntriesSurviveReload(@TempDir Path dir) {
        String file = dir.resolve("activities.json").toString();

        FileActivityDataAccessObject first = new FileActivityDataAccessObject(file);
        Activity gaming = new Activity("Gaming", Duration.ofMinutes(90));
        gaming.addTimeEntry(new TimeEntry("e1", NINE, TEN));       // finished session
        gaming.addTimeEntry(new TimeEntry("e2", TEN, null));       // still running
        first.save("arish", gaming);

        FileActivityDataAccessObject reloaded = new FileActivityDataAccessObject(file);
        Activity loaded = reloaded.getActivity("arish", gaming.getId()).orElseThrow();

        assertEquals("Gaming", loaded.getName());
        assertEquals(Duration.ofMinutes(90), loaded.getTimeBudget().orElseThrow());
        assertEquals(2, loaded.getTimeEntries().size());
        assertEquals(TEN, loaded.getTimeEntry("e1").orElseThrow().getEnd());
        assertTrue(loaded.getTimeEntry("e2").orElseThrow().isRunning());
    }

    @Test
    void noBudgetIsSavedAsNoBudget(@TempDir Path dir) {
        String file = dir.resolve("activities.json").toString();
        Activity study = new Activity("Study");
        new FileActivityDataAccessObject(file).save("arish", study);

        Activity loaded = new FileActivityDataAccessObject(file).getActivity("arish", study.getId()).orElseThrow();
        assertTrue(loaded.getTimeBudget().isEmpty());
    }

    @Test
    void usersOnlySeeTheirOwnActivities(@TempDir Path dir) {
        FileActivityDataAccessObject dao = new FileActivityDataAccessObject(dir.resolve("a.json").toString());
        dao.save("arish", new Activity("Gaming"));
        dao.save("liam", new Activity("Gym"));

        List<Activity> arishActivities = dao.getActivities("arish");
        assertEquals(1, arishActivities.size());
        assertEquals("Gaming", arishActivities.get(0).getName());
        assertTrue(dao.existsByName("liam", "gym"));      // case-insensitive
        assertFalse(dao.existsByName("arish", "Gym"));
        assertFalse(dao.existsByName("arish", null));
        assertTrue(dao.getActivities("nobody").isEmpty());
    }

    @Test
    void deleteRemovesFromFile(@TempDir Path dir) {
        String file = dir.resolve("activities.json").toString();
        FileActivityDataAccessObject dao = new FileActivityDataAccessObject(file);
        Activity gaming = new Activity("Gaming");
        dao.save("arish", gaming);
        dao.save("arish", new Activity("Study"));

        dao.delete("arish", gaming.getId());
        dao.delete("arish", "does-not-exist");   // no error
        dao.delete("nobody", "x");               // no error

        FileActivityDataAccessObject reloaded = new FileActivityDataAccessObject(file);
        assertTrue(reloaded.getActivity("arish", gaming.getId()).isEmpty());
        assertEquals(1, reloaded.getActivities("arish").size());
    }

    @Test
    void deleteAllForUserRemovesOnlyThatUser(@TempDir Path dir) {
        String file = dir.resolve("activities.json").toString();
        FileActivityDataAccessObject dao = new FileActivityDataAccessObject(file);
        dao.save("arish", new Activity("Gaming"));
        dao.save("liam", new Activity("Gym"));

        dao.deleteAllForUser("arish");
        dao.deleteAllForUser("nobody");          // no error

        FileActivityDataAccessObject reloaded = new FileActivityDataAccessObject(file);
        assertTrue(reloaded.getActivities("arish").isEmpty());
        assertEquals(1, reloaded.getActivities("liam").size());
    }

    @Test
    void useCaseNamesMapToSharedMethods(@TempDir Path dir) {
        FileActivityDataAccessObject dao = new FileActivityDataAccessObject(dir.resolve("a.json").toString());
        Activity gaming = new Activity("Gaming");
        dao.save("arish", gaming);

        assertEquals(gaming, dao.retrieve("arish", gaming.getId()).orElseThrow());
        assertEquals(1, dao.retrieveAll("arish").size());
    }

    @Test
    void missingFileStartsEmpty(@TempDir Path dir) {
        FileActivityDataAccessObject dao = new FileActivityDataAccessObject(dir.resolve("none.json").toString());
        assertTrue(dao.getActivities("arish").isEmpty());
    }
}
