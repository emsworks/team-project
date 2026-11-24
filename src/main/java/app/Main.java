package app;

import data_access.FileActivityDataAccessObject;
import data_access.FileUserDataAccessObject;
import data_access.UserDataAccessObject;
import entity.UserFactory;
import use_case.dao_interfaces.ActivityDataAccessInterface;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Entry point. Swap storage by changing the lines below
 * (e.g. new InMemoryUserDataAccessObject() for a throwaway session).
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserDataAccessObject userDataAccess = new FileUserDataAccessObject("users.json", new UserFactory());
            ActivityDataAccessInterface activityDataAccess = new FileActivityDataAccessObject("activities.json");

            JFrame application = new AppBuilder(userDataAccess, activityDataAccess)
                    .addLoginView()
                    .addSignupView()
                    .addLoggedInView()
                    .addSignupUseCase()
                    .addLoginUseCase()
                    .addLogoutUseCase()
                    .build();

            application.pack();
            application.setLocationRelativeTo(null);
            application.setVisible(true);
        });
    }
}