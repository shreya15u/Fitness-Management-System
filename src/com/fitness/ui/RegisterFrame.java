package com.fitness.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.fitness.model.User;
import com.fitness.service.AuthenticationService;

public class RegisterFrame extends JFrame {
    private final AuthenticationService authenticationService;
    private final JTextField nameField;
    private final JTextField emailField;
    private final JPasswordField passwordField;
    private final JTextField ageField;
    private final JTextField goalField;

    public RegisterFrame() {
        authenticationService = new AuthenticationService();
        nameField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();
        ageField = new JTextField();
        goalField = new JTextField();
        UiTheme.styleTextField(nameField);
        UiTheme.styleTextField(emailField);
        UiTheme.styleTextField(passwordField);
        UiTheme.styleTextField(ageField);
        UiTheme.styleTextField(goalField);

        setTitle("Online Fitness Platform - Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(560, 560));
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(520, 500));

        JPanel rootPanel = UiTheme.createPagePanel();
        rootPanel.setLayout(new BorderLayout());
        rootPanel.setBorder(BorderFactory.createEmptyBorder(28, 28, 28, 28));

        JPanel cardPanel = UiTheme.createCardPanel();
        cardPanel.setLayout(new BorderLayout(0, 18));

        JPanel titlePanel = new JPanel(new BorderLayout(0, 6));
        titlePanel.setOpaque(false);
        titlePanel.add(UiTheme.createTitleLabel("Create Your Account"), BorderLayout.NORTH);
        titlePanel.add(UiTheme.createSubtitleLabel("Set up your fitness profile to start tracking workouts."),
                BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        addField(formPanel, 0, "Full Name", nameField);
        addField(formPanel, 2, "Email", emailField);
        addField(formPanel, 4, "Password", passwordField);
        addField(formPanel, 6, "Age", ageField);
        addField(formPanel, 8, "Fitness Goal", goalField);

        JButton registerButton = UiTheme.createPrimaryButton("Create Account");
        JButton backButton = UiTheme.createSecondaryButton("Back to Login");

        registerButton.addActionListener(event -> register());
        backButton.addActionListener(event -> backToLogin());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);

        cardPanel.add(titlePanel, BorderLayout.NORTH);
        cardPanel.add(formPanel, BorderLayout.CENTER);
        cardPanel.add(buttonPanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(cardPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setBackground(UiTheme.PAGE_BACKGROUND);
        rootPanel.add(scrollPane, BorderLayout.CENTER);
        add(rootPanel);
    }

    private void addField(JPanel panel, int gridY, String labelText, JTextField field) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = gridY;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(6, 0, 4, 0);
        panel.add(UiTheme.createFieldLabel(labelText), constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = gridY + 1;
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 0, 8, 0);
        panel.add(field, constraints);
    }

    private void register() {
        String fullName = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String ageText = ageField.getText().trim();
        String goal = goalField.getText().trim();

        int age;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "Please enter a valid age.");
            return;
        }

        User user = new User(fullName, email, password, age, goal);
        boolean success = authenticationService.registerUser(user);

        if (success) {
            JOptionPane.showMessageDialog(this, "Registration successful. Please log in.");
            backToLogin();
        } else {
            String errorMessage = authenticationService.getLastErrorMessage();
            JOptionPane.showMessageDialog(
                    this,
                    errorMessage != null ? errorMessage
                            : "Registration failed. Please check the entered details and try again.");
        }
    }

    private void backToLogin() {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
        dispose();
    }
}
