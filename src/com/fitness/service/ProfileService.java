package com.fitness.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.fitness.db.DatabaseConnection;
import com.fitness.model.User;

public class ProfileService {

    public boolean updateProfile(User user) {
        if (user == null || user.getId() <= 0) {
            System.out.println("Invalid user profile.");
            return false;
        }

        if (user.getFullName() == null || user.getFullName().trim().length() < 3) {
            System.out.println("Full name must be at least 3 characters.");
            return false;
        }

        if (user.getAge() < 12 || user.getAge() > 100) {
            System.out.println("Age must be between 12 and 100.");
            return false;
        }

        if (user.getGoal() == null || user.getGoal().trim().length() < 3) {
            System.out.println("Fitness goal must be at least 3 characters.");
            return false;
        }

        String sql = "UPDATE users SET full_name = ?, age = ?, goal = ? WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getFullName().trim());
            statement.setInt(2, user.getAge());
            statement.setString(3, user.getGoal().trim());
            statement.setInt(4, user.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            System.out.println("Profile update failed: " + exception.getMessage());
            return false;
        }
    }
}
