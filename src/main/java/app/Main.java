package app;

import entity.Activity;
import entity.TimeEntry;
import interface_adapter.CheckTimeLimitController;
import interface_adapter.CheckTimeLimitPresenter;
import interface_adapter.DashboardViewModel;
import interface_adapter.ViewManagerModel;
import use_case.check_time_limit.CheckTimeLimitDataAccessInterface;
import use_case.check_time_limit.CheckTimeLimitInteractor;
import view.DashboardView;
import view.ViewManager;
import use_case.dao_interfaces.ActivityDataAccessInterface;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.CardLayout;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Entry point. For the prototype it skips login and opens the dashboard directly
 * with an in-memory DAO and some demo data.
 * TODO: once AppBuilder exists, move the wiring there and show the login screen first.
 */
public class Main {

    /** Minimal in-memory implementation of the shared activity DAO. */
    static class InMemoryActivityDAO implements ActivityDataAccessInterface, CheckTimeLimitDataAccessInterface {
        private final Map<String, Map<String, Activity>> data = new HashMap<>();

        private Map<String, Activity> of(String user) {
            return data.computeIfAbsent(user, u -> new HashMap<>());
        }

        public Optional<Activity> getActivity(String u, String id) {
            return Optional.ofNullable(of(u).get(id));
        }

        // used by the Check Time Limit use case
        public Optional<Activity> retrieve(String u, String id) {
            return getActivity(u, id);
        }

        public List<Activity> getActivities(String u) {
            return new ArrayList<>(of(u).values());
        }

        public boolean existsByName(String u, String name) {
            return of(u).values().stream().anyMatch(a -> a.getName().equalsIgnoreCase(name));
        }

        public void save(String u, Activity a) {
            of(u).put(a.getId(), a);
        }

        public void delete(String u, String id) {
            of(u).remove(id);
        }
    }

    private static void seed(ActivityDataAccessInterface dao, String user) {
        LocalDateTime t = LocalDate.now().atTime(8, 0);
        String[][] demo = {{"Studying", "90"}, {"Gaming", "150"}, {"Exercise", "45"}};
        Activity gaming = null;
        for (String[] d : demo) {
            Activity a = new Activity(d[0]);
            a.addTimeEntry(new TimeEntry(java.util.UUID.randomUUID().toString(), t,
                    t.plusMinutes(Long.parseLong(d[1]))));
            t = t.plusMinutes(Long.parseLong(d[1]) + 10);
            dao.save(user, a);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String user = "demo";
            InMemoryActivityDAO dao = new InMemoryActivityDAO();
            seed(dao, user);

            JFrame frame = new JFrame("Time Tracker (prototype)");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Runnable stub = () -> JOptionPane.showMessageDialog(frame,
                    "Not wired up yet - this should go back to the login screen.");

            // screen switching: ViewManager flips the CardLayout when the ViewManagerModel changes
            CardLayout cardLayout = new CardLayout();
            JPanel views = new JPanel(cardLayout);
            ViewManagerModel viewManagerModel = new ViewManagerModel();
            new ViewManager(views, cardLayout, viewManagerModel);

            // add each screen under its view name (login/signup go here too later)
            DashboardViewModel dashboardViewModel = new DashboardViewModel();
            CheckTimeLimitController checkTimeLimitController = new CheckTimeLimitController(
                    new CheckTimeLimitInteractor(dao, new CheckTimeLimitPresenter(dashboardViewModel)));
            views.add(new DashboardView(dashboardViewModel, checkTimeLimitController, dao, user, stub, stub),
                    dashboardViewModel.getViewName());

            frame.setContentPane(views);

            // show the first screen
            viewManagerModel.setState(dashboardViewModel.getViewName());
            viewManagerModel.firePropertyChanged();
            frame.setSize(900, 620);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
