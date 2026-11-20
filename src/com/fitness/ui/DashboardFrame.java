package com.fitness.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.fitness.model.User;
import com.fitness.model.Workout;
import com.fitness.service.ProfileService;
import com.fitness.service.WorkoutService;
import com.fitness.service.BasicProgressReporter;

public class DashboardFrame extends JFrame {
    private final User user;
    private final WorkoutService workoutService;
    private final ProfileService profileService;
    private final DefaultTableModel tableModel;
    private final JTable workoutTable;
    private final JTextField workoutNameField;
    private final JTextField durationField;
    private final JTextField caloriesField;
    private final JTextField dateField;

    public DashboardFrame(User user) {
        this.user = user;
        workoutService = new WorkoutService(new BasicProgressReporter());
        profileService = new ProfileService();
        tableModel = new DefaultTableModel(new Object[] { "ID", "Workout", "Duration", "Calories", "Date" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        workoutTable = new JTable(tableModel);
        workoutTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        workoutTable.setRowHeight(28);
        workoutTable.setFont(UiTheme.BODY_FONT);
        workoutTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        workoutTable.getTableHeader().setBackground(UiTheme.PRIMARY);
        workoutTable.getTableHeader().setForeground(Color.WHITE);
        workoutTable.setGridColor(UiTheme.BORDER);
        workoutTable.getSelectionModel().addListSelectionListener(new WorkoutSelectionHandler());
        workoutNameField = new JTextField();
        durationField = new JTextField();
        caloriesField = new JTextField();
        dateField = new JTextField();
        UiTheme.styleTextField(workoutNameField);
        UiTheme.styleTextField(durationField);
        UiTheme.styleTextField(caloriesField);
        UiTheme.styleTextField(dateField);

        setTitle("Online Fitness Platform - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(980, 600));
        setLocationRelativeTo(null);

        JPanel rootPanel = UiTheme.createPagePanel();
        rootPanel.setLayout(new BorderLayout(12, 12));
        rootPanel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        rootPanel.add(buildHeader(), BorderLayout.NORTH);
        rootPanel.add(buildCenterPanel(), BorderLayout.CENTER);
        rootPanel.add(buildActionPanel(), BorderLayout.SOUTH);
        add(rootPanel);

        loadWorkouts();
    }

    private JPanel buildHeader() {
        JPanel panel = UiTheme.createCardPanel();
        panel.setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel(new BorderLayout(0, 4));
        leftPanel.setOpaque(false);
        JLabel welcomeLabel = new JLabel("Welcome, " + user.getFullName());
        welcomeLabel.setFont(UiTheme.TITLE_FONT);
        welcomeLabel.setForeground(UiTheme.TEXT);
        JLabel subtitleLabel = new JLabel("Manage workouts, profile details, and your training summary.");
        subtitleLabel.setFont(UiTheme.SUBTITLE_FONT);
        subtitleLabel.setForeground(UiTheme.MUTED_TEXT);
        leftPanel.add(welcomeLabel, BorderLayout.NORTH);
        leftPanel.add(subtitleLabel, BorderLayout.CENTER);
        panel.add(leftPanel, BorderLayout.WEST);

        JButton logoutButton = UiTheme.createSecondaryButton("Logout");
        logoutButton.addActionListener(event -> logout());
        panel.add(logoutButton, BorderLayout.EAST);
        return panel;
    }

    private JPanel buildCenterPanel() {
        JPanel panel = UiTheme.createPagePanel();
        panel.setLayout(new GridLayout(1, 2, 12, 12));

        JPanel tableCard = UiTheme.createCardPanel();
        tableCard.setLayout(new BorderLayout(0, 10));
        JLabel historyLabel = new JLabel("Workout History");
        historyLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        historyLabel.setForeground(UiTheme.TEXT);
        JScrollPane scrollPane = new JScrollPane(workoutTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        tableCard.add(historyLabel, BorderLayout.NORTH);
        tableCard.add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = UiTheme.createCardPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiTheme.BORDER),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JLabel formTitle = new JLabel("Workout Details");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        formTitle.setForeground(UiTheme.TEXT);
        formPanel.add(formTitle);
        formPanel.add(new JLabel(" "));
        formPanel.add(UiTheme.createFieldLabel("Workout Name"));
        formPanel.add(workoutNameField);
        formPanel.add(new JLabel(" "));
        formPanel.add(UiTheme.createFieldLabel("Duration (minutes)"));
        formPanel.add(durationField);
        formPanel.add(new JLabel(" "));
        formPanel.add(UiTheme.createFieldLabel("Calories Burned"));
        formPanel.add(caloriesField);
        formPanel.add(new JLabel(" "));
        formPanel.add(UiTheme.createFieldLabel("Date (YYYY-MM-DD)"));
        formPanel.add(dateField);

        panel.add(tableCard);
        panel.add(formPanel);
        return panel;
    }

    private JPanel buildActionPanel() {
        JPanel panel = UiTheme.createCardPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 6));

        JButton addButton = UiTheme.createPrimaryButton("Add Workout");
        JButton updateButton = UiTheme.createSecondaryButton("Update Workout");
        JButton deleteButton = UiTheme.createSecondaryButton("Delete Workout");
        JButton progressButton = UiTheme.createPrimaryButton("View Progress");
        JButton profileButton = UiTheme.createSecondaryButton("Profile");

        addButton.addActionListener(event -> addWorkout());
        updateButton.addActionListener(event -> updateWorkout());
        deleteButton.addActionListener(event -> deleteWorkout());
        progressButton.addActionListener(event -> showProgress());
        profileButton.addActionListener(event -> showProfileOptions());

        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(progressButton);
        panel.add(profileButton);
        return panel;
    }

    private void addWorkout() {
        Workout workout = buildWorkoutFromFields(0);
        if (workout == null) {
            return;
        }

        if (workoutService.addWorkout(workout)) {
            JOptionPane.showMessageDialog(this, "Workout added successfully.");
            clearWorkoutFields();
            loadWorkouts();
        } else {
            JOptionPane.showMessageDialog(this, "Could not add workout. Check the entered values.");
        }
    }

    private void updateWorkout() {
        int selectedRow = workoutTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a workout from the table first.");
            return;
        }

        int workoutId = (int) tableModel.getValueAt(selectedRow, 0);
        Workout workout = buildWorkoutFromFields(workoutId);
        if (workout == null) {
            return;
        }

        if (workoutService.updateWorkout(workout)) {
            JOptionPane.showMessageDialog(this, "Workout updated successfully.");
            clearWorkoutFields();
            loadWorkouts();
        } else {
            JOptionPane.showMessageDialog(this, "Could not update workout. Check the entered values.");
        }
    }

    private void deleteWorkout() {
        int selectedRow = workoutTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a workout from the table first.");
            return;
        }

        int workoutId = (int) tableModel.getValueAt(selectedRow, 0);
        if (workoutService.deleteWorkout(workoutId, user.getId())) {
            JOptionPane.showMessageDialog(this, "Workout deleted successfully.");
            clearWorkoutFields();
            loadWorkouts();
        } else {
            JOptionPane.showMessageDialog(this, "Could not delete workout.");
        }
    }

    private void showProgress() {
        JOptionPane.showMessageDialog(this, workoutService.getProgressSummary(user.getId()));
    }

    private void showProfileOptions() {
        String profileDetails = String.format(
                "Name: %s%nEmail: %s%nAge: %d%nGoal: %s%nRole: %s",
                user.getFullName(),
                user.getEmail(),
                user.getAge(),
                user.getGoal(),
                user.getRoleDescription());

        String newName = JOptionPane.showInputDialog(this, profileDetails + "\n\nEnter new name:", user.getFullName());
        if (newName == null) {
            return;
        }

        String newAgeText = JOptionPane.showInputDialog(this, "Enter new age:", user.getAge());
        if (newAgeText == null) {
            return;
        }

        String newGoal = JOptionPane.showInputDialog(this, "Enter new fitness goal:", user.getGoal());
        if (newGoal == null) {
            return;
        }

        try {
            user.setFullName(newName.trim());
            user.setAge(Integer.parseInt(newAgeText.trim()));
            user.setGoal(newGoal.trim());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "Please enter a valid age.");
            return;
        }

        if (profileService.updateProfile(user)) {
            JOptionPane.showMessageDialog(this, "Profile updated successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Could not update profile. Check the entered values.");
        }
    }

    private Workout buildWorkoutFromFields(int workoutId) {
        String workoutName = workoutNameField.getText().trim();
        String durationText = durationField.getText().trim();
        String caloriesText = caloriesField.getText().trim();
        String dateText = dateField.getText().trim();

        if (workoutName.isEmpty() || durationText.isEmpty() || caloriesText.isEmpty() || dateText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all workout fields.");
            return null;
        }

        try {
            int duration = Integer.parseInt(durationText);
            int calories = Integer.parseInt(caloriesText);
            LocalDate workoutDate = LocalDate.parse(dateText);
            return new Workout(workoutId, user.getId(), workoutName, duration, calories, workoutDate);
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "Duration and calories must be numbers.");
            return null;
        } catch (DateTimeParseException exception) {
            JOptionPane.showMessageDialog(this, "Date must be in YYYY-MM-DD format.");
            return null;
        }
    }

    private void loadWorkouts() {
        tableModel.setRowCount(0);
        List<Workout> workouts = workoutService.getWorkoutsByUserId(user.getId());
        for (Workout workout : workouts) {
            tableModel.addRow(new Object[] {
                    workout.getWorkoutId(),
                    workout.getWorkoutName(),
                    workout.getDurationMinutes(),
                    workout.getCaloriesBurned(),
                    workout.getWorkoutDate()
            });
        }
    }

    private void clearWorkoutFields() {
        workoutNameField.setText("");
        durationField.setText("");
        caloriesField.setText("");
        dateField.setText("");
    }

    private void logout() {
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
        dispose();
    }

    private class WorkoutSelectionHandler implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }

            int selectedRow = workoutTable.getSelectedRow();
            if (selectedRow == -1) {
                return;
            }

            workoutNameField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 1)));
            durationField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 2)));
            caloriesField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 3)));
            dateField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 4)));
        }
    }
}
