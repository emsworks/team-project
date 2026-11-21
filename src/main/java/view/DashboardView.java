package view;

import entity.Activity;
import entity.TimeEntry;
import interface_adapter.DashboardState;
import interface_adapter.DashboardViewModel;
import use_case.dao_interfaces.ActivityDataAccessInterface;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Prototype dashboard: chart + entry list in the centre, punch in/out and
 * time-limit controls in the right-hand menu.
 * Talks to storage only through ActivityDataAccessInterface.
 * TODO: move the logic below into controllers/interactors as use cases get built.
 */
public class DashboardView extends JPanel {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final DashboardViewModel viewModel;
    private final ActivityDataAccessInterface dao;
    private final String username;
    private final Runnable onLogout;
    private final Runnable onDeleteAccount;

    private final ChartPanel chart = new ChartPanel();
    private final JComboBox<String> periodBox = new JComboBox<>(new String[]{"Daily", "Weekly", "Monthly"});
    private final JComboBox<String> chartBox = new JComboBox<>(new String[]{"Pie chart", "Bar chart"});
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"Activity", "Start", "End", "Duration"}, 0) {
        @Override
        public boolean isCellEditable(int r, int c) {
            return false;
        }
    };
    private final JTable table = new JTable(tableModel);
    private final List<Activity> rowActivity = new ArrayList<>();
    private final List<TimeEntry> rowEntry = new ArrayList<>();

    private final JComboBox<String> activityBox = new JComboBox<>();
    private final JLabel statusLabel = new JLabel("Not punched in");
    private final JTextField limitField = new JTextField("60", 4);
    private final Set<String> alreadyWarned = new HashSet<>();
    private final Timer timer = new Timer(1000, e -> tick());


    public DashboardView(DashboardViewModel viewModel, ActivityDataAccessInterface dao, String username,
                         Runnable onLogout, Runnable onDeleteAccount) {
        this.viewModel = viewModel;
        // redraw whenever the view model's state changes
        viewModel.addPropertyChangeListener(evt -> render(viewModel.getState()));
        this.dao = dao;
        this.username = username;
        this.onLogout = onLogout;
        this.onDeleteAccount = onDeleteAccount;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(buildCenter(), BorderLayout.CENTER);
        add(buildMenu(), BorderLayout.EAST);

        periodBox.addActionListener(e -> refresh());
        chartBox.addActionListener(e -> refresh());

        // Updates the running clock and checks the time limit once a second.
        timer.start();        
        refresh();
    }
    /** Stops the clock; called when the user logs out so it doesn't keep running. */
    public void stop() {
        timer.stop();
    }
    // ---------- layout ----------

    private JPanel buildCenter() {
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Dashboard - " + username + "   View:"));
        top.add(periodBox);
        top.add(chartBox);

        JPanel entriesButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton edit = new JButton("Edit selected entry");
        JButton delete = new JButton("Delete selected entry");
        edit.addActionListener(e -> editSelected());
        delete.addActionListener(e -> deleteSelected());
        entriesButtons.add(edit);
        entriesButtons.add(delete);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(500, 160));
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(scroll, BorderLayout.CENTER);
        bottom.add(entriesButtons, BorderLayout.SOUTH);

        JPanel center = new JPanel(new BorderLayout(5, 5));
        center.add(top, BorderLayout.NORTH);
        center.add(chart, BorderLayout.CENTER);
        center.add(bottom, BorderLayout.SOUTH);
        return center;
    }

    private JPanel buildMenu() {
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        menu.setPreferredSize(new Dimension(220, 0));

        activityBox.setEditable(true);
        JButton in = new JButton("Punch In");
        JButton out = new JButton("Punch Out");
        in.addActionListener(e -> punchIn());
        out.addActionListener(e -> punchOut());

        JButton setLimit = new JButton("Set limit");
        setLimit.addActionListener(e -> setLimit());
        JPanel limitRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        limitRow.add(limitField);
        limitRow.add(new JLabel("min"));
        limitRow.add(setLimit);

        JButton logout = new JButton("Log out");
        JButton delAcct = new JButton("Delete account");
        logout.addActionListener(e -> onLogout.run());
        delAcct.addActionListener(e -> deleteAccount());

        menu.add(label("Activity (type a new name to create)"));
        menu.add(activityBox);
        menu.add(Box.createVerticalStrut(8));
        JPanel punch = new JPanel(new GridLayout(1, 2, 5, 0));
        punch.add(in);
        punch.add(out);
        menu.add(punch);
        menu.add(Box.createVerticalStrut(8));
        menu.add(statusLabel);
        menu.add(Box.createVerticalStrut(20));
        menu.add(label("Time limit for selected activity"));
        menu.add(limitRow);
        menu.add(Box.createVerticalGlue());
        menu.add(logout);
        menu.add(Box.createVerticalStrut(5));
        menu.add(delAcct);
        for (Component c : menu.getComponents()) {
            if (c instanceof JComponent) {
                ((JComponent) c).setAlignmentX(Component.LEFT_ALIGNMENT);
            }
        }
        activityBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        in.setPreferredSize(new Dimension(90, 40));
        out.setPreferredSize(new Dimension(90, 40));
        punch.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        return menu;
    }

    private static JLabel label(String text) {
        return new JLabel(text);
    }

    // ---------- actions ----------

    private void punchIn() {
        String name = String.valueOf(activityBox.getEditor().getItem()).trim();
        if (name.isEmpty() || name.equals("null")) {
            error("Please enter or select an activity.");
            return;
        }
        if (findRunning().isPresent()) {
            error("An activity is already running. Punch out first.");
            return;
        }
        Activity activity = findByName(name).orElseGet(() -> new Activity(name));
        activity.addTimeEntry(new TimeEntry(LocalDateTime.now()));
        dao.save(username, activity);
        refresh();
    }

    private void punchOut() {
        Optional<Activity> running = findRunning();
        if (running.isEmpty()) {
            error("Nothing is running.");
            return;
        }
        Activity a = running.get();
        a.getRunningEntry().ifPresent(en -> en.stop(LocalDateTime.now()));
        dao.save(username, a);
        refresh();
    }

    private void setLimit() {
        String name = String.valueOf(activityBox.getEditor().getItem()).trim();
        Optional<Activity> a = findByName(name);
        if (a.isEmpty()) {
            error("Select an existing activity first.");
            return;
        }
        try {
            long minutes = Long.parseLong(limitField.getText().trim());
            if (minutes <= 0) {
                error("Limit must be greater than 0.");
                return;
            }
            a.get().setTimeBudget(Duration.ofMinutes(minutes));
            dao.save(username, a.get());
            alreadyWarned.remove(a.get().getId());
            JOptionPane.showMessageDialog(this, "Limit for " + a.get().getName() + " set to " + minutes + " min.");
        } catch (NumberFormatException ex) {
            error("Enter the limit as a whole number of minutes.");
        }
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            error("Select an entry first.");
            return;
        }
        TimeEntry entry = rowEntry.get(row);
        JTextField start = new JTextField(entry.getStart().format(FMT));
        JTextField end = new JTextField(entry.getEnd() == null ? "" : entry.getEnd().format(FMT));
        JPanel p = new JPanel(new GridLayout(0, 1));
        p.add(new JLabel("Start (yyyy-MM-dd HH:mm)"));
        p.add(start);
        p.add(new JLabel("End (blank = still running)"));
        p.add(end);
        if (JOptionPane.showConfirmDialog(this, p, "Edit entry", JOptionPane.OK_CANCEL_OPTION)
                != JOptionPane.OK_OPTION) {
            return;
        }
        try {
            LocalDateTime s = LocalDateTime.parse(start.getText().trim(), FMT);
            LocalDateTime e = end.getText().isBlank() ? null : LocalDateTime.parse(end.getText().trim(), FMT);
            entry.setTimes(s, e);
            dao.save(username, rowActivity.get(row));
            refresh();
        } catch (DateTimeParseException ex) {
            error("Use the format yyyy-MM-dd HH:mm.");
        } catch (IllegalArgumentException ex) {
            error(ex.getMessage());
        }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            error("Select an entry first.");
            return;
        }
        Activity a = rowActivity.get(row);
        a.removeTimeEntry(rowEntry.get(row).getId());
        dao.save(username, a);
        refresh();
    }

    private void deleteAccount() {
        if (JOptionPane.showConfirmDialog(this, "Delete your account and all data?",
                "Delete account", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            for (Activity a : dao.getActivities(username)) {
                dao.delete(username, a.getId());
            }
            onDeleteAccount.run();
        }
    }

    // ---------- refresh ----------

    private void tick() {
        for (Activity a : dao.getActivities(username)) {
            if (a.isOverBudget() && alreadyWarned.add(a.getId())) {
                JOptionPane.showMessageDialog(this, "You've gone over your limit for \"" + a.getName() + "\"!",
                        "Time limit reminder", JOptionPane.WARNING_MESSAGE);
            }
        }
        // keeps the running clock ticking
        if (findRunning().isPresent()) {
            refresh();
        }
    }

    private LocalDateTime periodStart() {
        LocalDateTime today = LocalDateTime.now().toLocalDate().atStartOfDay();
        switch (periodBox.getSelectedIndex()) {
            case 1: return today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            case 2: return today.withDayOfMonth(1);
            default: return today;
        }
    }

    /** Builds a fresh DashboardState from storage and hands it to the view model. */
    private void refresh() {
        LocalDateTime from = periodStart();
        rowActivity.clear();
        rowEntry.clear();

        List<Activity> activities = dao.getActivities(username);
        List<Object[]> pairs = new ArrayList<>();
        for (Activity act : activities) {
            for (TimeEntry en : act.getTimeEntries()) {
                if (!en.getStart().isBefore(from)) {
                    pairs.add(new Object[]{act, en});
                }
            }
        }
        pairs.sort(Comparator.comparing((Object[] p) -> ((TimeEntry) p[1]).getStart()).reversed());

        Map<String, Long> unsorted = new LinkedHashMap<>();
        List<String[]> rows = new ArrayList<>();
        for (Object[] p : pairs) {
            Activity act = (Activity) p[0];
            TimeEntry en = (TimeEntry) p[1];
            unsorted.merge(act.getName(), en.getDuration().getSeconds(), Long::sum);
            rowActivity.add(act);
            rowEntry.add(en);
            rows.add(new String[]{act.getName(), en.getStart().format(FMT),
                    en.isRunning() ? "(running)" : en.getEnd().format(FMT),
                    ChartPanel.format(en.getDuration().getSeconds())});
        }
        // most-used activities first
        Map<String, Long> totals = new LinkedHashMap<>();
        unsorted.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(e -> totals.put(e.getKey(), e.getValue()));

        List<String> names = new ArrayList<>();
        for (Activity act : activities) {
            names.add(act.getName());
        }

        Optional<Activity> running = findRunning();
        String runningText = "Not punched in";
        if (running.isPresent()) {
            Activity act = running.get();
            runningText = "Running: " + act.getName() + "  "
                    + ChartPanel.format(act.getRunningEntry().get().getDuration().getSeconds());
        }

        DashboardState state = new DashboardState();
        state.setUsername(username);
        state.setPieChart(chartBox.getSelectedIndex() == 0);
        state.setChartTotals(totals);
        state.setEntryRows(rows);
        state.setActivityNames(names);
        state.setRunningText(runningText);
        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }

    /** Draws the screen from the state. This is the only place the widgets get updated. */
    private void render(DashboardState s) {
        chart.setData(s.getChartTotals(), s.isPieChart());
        statusLabel.setText(s.getRunningText());

        int selected = table.getSelectedRow();
        tableModel.setRowCount(0);
        for (String[] row : s.getEntryRows()) {
            tableModel.addRow(row);
        }
        if (selected >= 0 && selected < tableModel.getRowCount()) {
            table.setRowSelectionInterval(selected, selected);
        }

        // keep the activity dropdown in sync without losing what the user typed
        Object typed = activityBox.getEditor().getItem();
        activityBox.removeAllItems();
        for (String name : s.getActivityNames()) {
            activityBox.addItem(name);
        }
        activityBox.getEditor().setItem(typed);
    }

    // ---------- helpers ----------

    private Optional<Activity> findByName(String name) {
        return dao.getActivities(username).stream()
                .filter(a -> a.getName().equalsIgnoreCase(name)).findFirst();
    }

    private Optional<Activity> findRunning() {
        return dao.getActivities(username).stream()
                .filter(a -> a.getRunningEntry().isPresent()).findFirst();
    }

    private void error(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Oops", JOptionPane.ERROR_MESSAGE);
    }
}
