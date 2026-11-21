package interface_adapter;


/** View model for the dashboard screen. */
public class DashboardViewModel extends ViewModel<DashboardState> {
    public static final String VIEW_NAME = "dashboard";

    public DashboardViewModel() {
        super(VIEW_NAME);
        setState(new DashboardState());
    }
}
