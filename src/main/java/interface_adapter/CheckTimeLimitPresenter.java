package interface_adapter;

import use_case.check_time_limit.CheckTimeLimitOutputBoundary;
import use_case.check_time_limit.CheckTimeLimitUserData;

/**
 * Turns the interactor's result into dashboard state. When an activity is over
 * its limit, it puts a reminder message in the state and notifies the view.
 */
public class CheckTimeLimitPresenter implements CheckTimeLimitOutputBoundary {
    private final DashboardViewModel viewModel;

    public CheckTimeLimitPresenter(DashboardViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void success(CheckTimeLimitUserData userData) {
        DashboardState state = viewModel.getState();
        if (userData.isOverBudget()) {
            state.setReminderActivityName(userData.getActivityName());
            state.setReminderMessage("You've gone over your limit for \""
                    + userData.getActivityName() + "\"!");
            viewModel.setState(state);
            viewModel.firePropertyChanged();
        }
        // Not over budget: nothing to show, so no redraw is needed.
    }

    @Override
    public void failure(String message) {
        DashboardState state = viewModel.getState();
        state.setErrorMessage(message);
        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }
}
