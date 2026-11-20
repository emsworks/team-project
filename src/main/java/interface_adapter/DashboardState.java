package interface_adapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Everything the dashboard screen needs to draw itself.
 * Plain data (strings and numbers) so the view never touches entities.
 */
public class DashboardState {
    private String username = "";
    private boolean pieChart = true;

    // activity name -> seconds spent in the selected period, most-used first
    private Map<String, Long> chartTotals = new LinkedHashMap<>();

    // each row: {activity, start, end, duration}, newest first
    private List<String[]> entryRows = new ArrayList<>();

    // names for the activity dropdown
    private List<String> activityNames = new ArrayList<>();

    // e.g. "Running: Gaming 0h 12m" or "Not punched in"
    private String runningText = "Not punched in";

    // message the view should show in a dialog, or null
    private String errorMessage;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public boolean isPieChart() { return pieChart; }
    public void setPieChart(boolean pieChart) { this.pieChart = pieChart; }

    public Map<String, Long> getChartTotals() { return chartTotals; }
    public void setChartTotals(Map<String, Long> chartTotals) { this.chartTotals = chartTotals; }

    public List<String[]> getEntryRows() { return entryRows; }
    public void setEntryRows(List<String[]> entryRows) { this.entryRows = entryRows; }

    public List<String> getActivityNames() { return activityNames; }
    public void setActivityNames(List<String> activityNames) { this.activityNames = activityNames; }

    public String getRunningText() { return runningText; }
    public void setRunningText(String runningText) { this.runningText = runningText; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
