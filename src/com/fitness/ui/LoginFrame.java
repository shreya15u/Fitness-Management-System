package com.fitness.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.fitness.model.User;
import com.fitness.service.AuthenticationService;

public class LoginFrame extends JFrame {
    private final AuthenticationService authenticationService;
    private final JTextField emailField;
    private final JPasswordField passwordField;

    public LoginFrame() {
        authenticationService = new AuthenticationService();
        emailField = new JTextField();
        passwordField = new JPasswordField();
        UiTheme.styleTextField(emailField);
        UiTheme.styleTextField(passwordField);

        setTitle("Online Fitness Platform - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(860, 500));
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel rootPanel = UiTheme.createPagePanel();
        rootPanel.setLayout(new BorderLayout());
        rootPanel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JButton loginButton = UiTheme.createPrimaryButton("Login");
        JButton registerButton = UiTheme.createSecondaryButton("Create Account");

        loginButton.addActionListener(event -> login());
        registerButton.addActionListener(event -> openRegister());

        JPanel shellPanel = UiTheme.createCardPanel();
        shellPanel.setLayout(new GridLayout(1, 2, 0, 0));
        shellPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiTheme.BORDER),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));

        JPanel heroPanel = new JPanel(new BorderLayout(0, 20));
        heroPanel.setBackground(UiTheme.PRIMARY);
        heroPanel.setBorder(BorderFactory.createEmptyBorder(34, 34, 34, 34));

        JPanel heroTextPanel = new JPanel(new GridBagLayout());
        heroTextPanel.setOpaque(false);
        GridBagConstraints heroConstraints = new GridBagConstraints();
        heroConstraints.gridx = 0;
        heroConstraints.gridy = 0;
        heroConstraints.anchor = GridBagConstraints.WEST;
        heroConstraints.insets = new Insets(0, 0, 12, 0);

        JLabel eyebrowLabel = new JLabel("Fitness Management System");
        eyebrowLabel.setFont(UiTheme.LABEL_FONT);
        eyebrowLabel.setForeground(new Color(226, 240, 203));
        heroTextPanel.add(eyebrowLabel, heroConstraints);

        heroConstraints.gridy++;
        JLabel heroTitle = new JLabel("<html>Train smarter.<br>Track better.</html>");
        heroTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 30));
        heroTitle.setForeground(Color.WHITE);
        heroTextPanel.add(heroTitle, heroConstraints);

        heroConstraints.gridy++;
        heroConstraints.insets = new Insets(0, 0, 18, 0);
        JLabel heroSubtitle = new JLabel(
                "<html>Manage workouts, monitor progress, and keep your fitness records organized in one place.</html>");
        heroSubtitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 15));
        heroSubtitle.setForeground(new Color(237, 245, 234));
        heroTextPanel.add(heroSubtitle, heroConstraints);

        heroConstraints.gridy++;
        heroConstraints.insets = new Insets(0, 0, 8, 0);
        heroTextPanel.add(createFeatureLabel("Secure login and account management"), heroConstraints);
        heroConstraints.gridy++;
        heroTextPanel.add(createFeatureLabel("Workout history and progress summary"), heroConstraints);
        heroConstraints.gridy++;
        heroTextPanel.add(createFeatureLabel("Simple desktop interface for quick access"), heroConstraints);

        heroPanel.add(heroTextPanel, BorderLayout.CENTER);

        JPanel formWrapper = new JPanel(new BorderLayout(0, 18));
        formWrapper.setBackground(Color.WHITE);
        formWrapper.setBorder(BorderFactory.createEmptyBorder(36, 36, 36, 36));

        JPanel titlePanel = new JPanel(new BorderLayout(0, 6));
        titlePanel.setOpaque(false);
        JLabel loginTitle = UiTheme.createTitleLabel("Welcome Back");
        loginTitle.setHorizontalAlignment(JLabel.LEFT);
        JLabel loginSubtitle = UiTheme.createSubtitleLabel("Sign in to continue to your dashboard.");
        loginSubtitle.setHorizontalAlignment(JLabel.LEFT);
        titlePanel.add(loginTitle, BorderLayout.NORTH);
        titlePanel.add(loginSubtitle, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(8, 0, 5, 0);
        formPanel.add(UiTheme.createFieldLabel("Email Address"), constraints);

        constraints.gridy++;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.insets = new Insets(0, 0, 14, 0);
        formPanel.add(emailField, constraints);

        constraints.gridy++;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.insets = new Insets(8, 0, 5, 0);
        formPanel.add(UiTheme.createFieldLabel("Password"), constraints);

        constraints.gridy++;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.insets = new Insets(0, 0, 18, 0);
        formPanel.add(passwordField, constraints);

        JPanel hintPanel = new JPanel(new BorderLayout());
        hintPanel.setOpaque(false);
        JLabel hintLabel = new JLabel("New here? Create an account to start tracking your workouts.");
        hintLabel.setFont(UiTheme.SUBTITLE_FONT);
        hintLabel.setForeground(UiTheme.MUTED_TEXT);
        hintPanel.add(hintLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        formWrapper.add(titlePanel, BorderLayout.NORTH);
        formWrapper.add(formPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(0, 14));
        bottomPanel.setOpaque(false);
        bottomPanel.add(hintPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        formWrapper.add(bottomPanel, BorderLayout.SOUTH);

        shellPanel.add(heroPanel);
        shellPanel.add(formWrapper);
        rootPanel.add(shellPanel, BorderLayout.CENTER);
        add(rootPanel);
    }

    private JLabel createFeatureLabel(String text) {
        JLabel label = new JLabel("\u2022 " + text);
        label.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        label.setForeground(new Color(240, 247, 236));
        return label;
    }

    private void login() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter email and password.");
            return;
        }

        User user = authenticationService.loginUser(email, password);
        if (user == null) {
            JOptionPane.showMessageDialog(this, "Invalid email or password.");
            return;
        }

        DashboardFrame dashboardFrame = new DashboardFrame(user);
        dashboardFrame.setVisible(true);
        dispose();
    }

    private void openRegister() {
        SwingUtilities.invokeLater(() -> {
            RegisterFrame registerFrame = new RegisterFrame();
            registerFrame.setVisible(true);
        });
        dispose();
    }
}
