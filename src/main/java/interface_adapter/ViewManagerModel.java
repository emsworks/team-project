package interface_adapter;

/**
 * Tracks which screen is currently showing. The state is the view name of that
 * screen (e.g. DashboardViewModel.VIEW_NAME). Presenters change it to switch
 * screens; ViewManager listens and flips the CardLayout.
 */
public class ViewManagerModel extends ViewModel<String> {

    public ViewManagerModel() {
        super("view manager");
        setState("");
    }
}
