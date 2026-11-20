package view;

import interface_adapter.ViewManagerModel;

import javax.swing.JPanel;
import java.awt.CardLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Listens to the ViewManagerModel and shows the matching screen.
 * Every screen must be added to the card panel under its view name, e.g.
 * views.add(dashboardView, dashboardViewModel.getViewName());
 */
public class ViewManager implements PropertyChangeListener {
    private final JPanel views;
    private final CardLayout cardLayout;

    public ViewManager(JPanel views, CardLayout cardLayout, ViewManagerModel viewManagerModel) {
        this.views = views;
        this.cardLayout = cardLayout;
        viewManagerModel.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(ViewManagerModel.STATE_PROPERTY)) {
            String viewName = (String) evt.getNewValue();
            cardLayout.show(views, viewName);
        }
    }
}
