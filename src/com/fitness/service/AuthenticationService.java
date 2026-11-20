package com.fitness.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import com.fitness.db.DatabaseConnection;
import com.fitness.model.User;
import com.fitness.util.PasswordUtil;

public class AuthenticationService {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private String lastErrorMessage;

    public boolean registerUser(User user) {
        lastErrorMessage = null;
        String validationError = validateRegistration(user);
        if (validationError != null) {
            lastErrorMessage = validationError;
            System.out.println(validationError);
            return false;
        }

        if (emailExists(user.getEmail())) {
            lastErrorMessage = "A user with this email already exists.";
            System.out.println(lastErrorMessage);
            return false;
        }

        String sql = "INSERT INTO users(full_name, email, password, age, goal) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getFullName().trim());
            statement.setString(2, user.getEmail().trim().toLowerCase());
            statement.setString(3, PasswordUtil.hashPassword(user.getPassword()));
            statement.setInt(4, user.getAge());
            statement.setString(5, user.getGoal().trim());

            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            lastErrorMessage = "Registration failed: " + exception.getMessage();
            System.out.println(lastErrorMessage);
            return false;
        }
    }

    public User loginUser(String email, String password) {
        lastErrorMessage = null;
        if (isNullOrBlank(email) || isNullOrBlank(password)) {
            lastErrorMessage = "Please enter email and password.";
            return null;
        }

        String sql = "SELECT user_id, full_name, email, password, age, goal FROM users WHERE email = ?";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email.trim().toLowerCase());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("password");
                    boolean passwordMatches = PasswordUtil.matches(password, storedPassword)
                            || password.equals(storedPassword);

                    if (!passwordMatches) {
                        return null;
                    }

                    if (password.equals(storedPassword)) {
                        upgradeStoredPassword(resultSet.getInt("user_id"), password);
                    }

                    return new User(
                            resultSet.getInt("user_id"),
                            resultSet.getString("full_name"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getInt("age"),
                            resultSet.getString("goal"));
                }
            }
        } catch (SQLException exception) {
            lastErrorMessage = "Login failed: " + exception.getMessage();
            System.out.println(lastErrorMessage);
        }

        return null;
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }

    private boolean emailExists(String email) {
        String sql = "SELECT 1 FROM users WHERE email = ?";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email.trim().toLowerCase());

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException exception) {
            System.out.println("Could not validate email: " + exception.getMessage());
            return false;
        }
    }

    private boolean isNullOrBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private String validateRegistration(User user) {
        if (user == null) {
            return "Invalid user details.";
        }

        if (isNullOrBlank(user.getFullName()) || isNullOrBlank(user.getEmail()) || isNullOrBlank(user.getPassword())
                || isNullOrBlank(user.getGoal())) {
            return "Please fill all registration fields.";
        }

        if (user.getFullName().trim().length() < 3) {
            return "Full name must be at least 3 characters.";
        }

        if (!EMAIL_PATTERN.matcher(user.getEmail().trim()).matches()) {
            return "Please enter a valid email address.";
        }

        if (user.getPassword().length() < 8) {
            return "Password must be at least 8 characters long.";
        }

        if (user.getAge() < 12 || user.getAge() > 100) {
            return "Age must be between 12 and 100.";
        }

        if (user.getGoal().trim().length() < 3) {
            return "Fitness goal must be at least 3 characters.";
        }

        return null;
    }

    private void upgradeStoredPassword(int userId, String rawPassword) {
        String sql = "UPDATE users SET password = ? WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, PasswordUtil.hashPassword(rawPassword));
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println("Could not upgrade password security: " + exception.getMessage());
        }
    }
}
