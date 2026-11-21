package view;

import interface_adapter.DashboardViewModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.logout.LogoutController;
import use_case.dao_interfaces.ActivityDataAccessInterface;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Window;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/*
 * LoggedInView
 * ------------
 * The screen shown after login. It holds the DashboardView.
 * The dashboard needs the username at construction, so it is built each time
 * a user logs in and torn down when they log out.
 */
public class LoggedInView extends JPanel implements PropertyChangeListener {
    private final LoggedInViewModel loggedInViewModel;
    private final ActivityDataAccessInterface activityDataAccess;
    private LogoutController logoutController;

    // The current dashboard; null when nobody is logged in.
    private DashboardView dashboard;

    // The user the current dashboard was built for ("" = nobody logged in).
    private String currentUsername = "";

    public LoggedInView(LoggedInViewModel loggedInViewModel,
                        ActivityDataAccessInterface activityDataAccess) {
        this.loggedInViewModel = loggedInViewModel;
        this.activityDataAccess = activityDataAccess;
        loggedInViewModel.addPropertyChangeListener(this);
        setLayout(new BorderLayout());
    }

    public String getViewName() {
        return loggedInViewModel.getViewName();
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }

    /** Runs when login/logout updates the logged-in state. */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String username = loggedInViewModel.getState().getUsername();
        if (username == null) {
            username = "";
        }

        // A failed logout (or a repeated event) fires this with the same username; ignore it.
        if (username.equals(currentUsername)) {
            return;
        }
        currentUsername = username;

        removeDashboard();
        if (!username.isBlank()) {
            showDashboard(username);
        }
    }

    /** Builds a fresh dashboard for this user and puts it on screen. */
    private void showDashboard(String username) {
        dashboard = new DashboardView(
                new DashboardViewModel(),
                activityDataAccess,
                username,
                () -> logoutController.execute(),
                // TODO: replace with a real Delete Account use case.
                // For now this only logs out; the user's record is NOT deleted.
                () -> logoutController.execute());
        add(dashboard, BorderLayout.CENTER);
        revalidate();
        repaint();

        // The window was sized for the login screen; resize it to fit the dashboard.
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.pack();
            window.setLocationRelativeTo(null);
        }
    }

    /** Stops the old dashboard's clock and removes it from the screen. */
    private void removeDashboard() {
        if (dashboard != null) {
            dashboard.stop();
            remove(dashboard);
            dashboard = null;
            revalidate();
            repaint();
        }
    }
}