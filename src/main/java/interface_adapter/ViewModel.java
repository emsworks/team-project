package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Base class for every view model. Holds the current state of one screen and
 * tells listeners (the view) when it changes.
 *
 * @param <T> the state class for that screen (e.g. DashboardState)
 */
public class ViewModel<T> {
    public static final String STATE_PROPERTY = "state";

    private final String viewName;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private T state;

    public ViewModel(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public T getState() {
        return state;
    }

    public void setState(T state) {
        this.state = state;
    }

    /** Call after setState (usually from a presenter) so the view redraws. */
    public void firePropertyChanged() {
        // old value is null so the event always fires, even if the state looks equal
        support.firePropertyChange(STATE_PROPERTY, null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
