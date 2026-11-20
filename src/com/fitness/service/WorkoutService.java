package com.fitness.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fitness.db.DatabaseConnection;
import com.fitness.model.Workout;

public class WorkoutService {
    private final ProgressReporter progressReporter;

    public WorkoutService(ProgressReporter progressReporter) {
        this.progressReporter = progressReporter;
    }

    public boolean addWorkout(Workout workout) {
        String validationError = validateWorkout(workout);
        if (validationError != null) {
            System.out.println(validationError);
            return false;
        }

        String sql = "INSERT INTO workouts(user_id, workout_name, duration_minutes, calories_burned, workout_date) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, workout.getUserId());
            statement.setString(2, workout.getWorkoutName().trim());
            statement.setInt(3, workout.getDurationMinutes());
            statement.setInt(4, workout.getCaloriesBurned());
            statement.setDate(5, Date.valueOf(workout.getWorkoutDate()));

            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            System.out.println("Could not save workout: " + exception.getMessage());
            return false;
        }
    }

    public List<Workout> getWorkoutsByUserId(int userId) {
        List<Workout> workouts = new ArrayList<>();
        String sql = "SELECT workout_id, user_id, workout_name, duration_minutes, calories_burned, workout_date FROM workouts WHERE user_id = ? ORDER BY workout_date DESC";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    workouts.add(new Workout(
                            resultSet.getInt("workout_id"),
                            resultSet.getInt("user_id"),
                            resultSet.getString("workout_name"),
                            resultSet.getInt("duration_minutes"),
                            resultSet.getInt("calories_burned"),
                            resultSet.getDate("workout_date").toLocalDate()));
                }
            }
        } catch (SQLException exception) {
            System.out.println("Could not load workouts: " + exception.getMessage());
        }

        return workouts;
    }

    public boolean updateWorkout(Workout workout) {
        String validationError = validateWorkout(workout);
        if (validationError != null || workout.getWorkoutId() <= 0) {
            System.out.println(validationError != null ? validationError : "Invalid workout selected.");
            return false;
        }

        String sql = "UPDATE workouts SET workout_name = ?, duration_minutes = ?, calories_burned = ?, workout_date = ? WHERE workout_id = ? AND user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, workout.getWorkoutName().trim());
            statement.setInt(2, workout.getDurationMinutes());
            statement.setInt(3, workout.getCaloriesBurned());
            statement.setDate(4, Date.valueOf(workout.getWorkoutDate()));
            statement.setInt(5, workout.getWorkoutId());
            statement.setInt(6, workout.getUserId());

            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            System.out.println("Could not update workout: " + exception.getMessage());
            return false;
        }
    }

    public boolean deleteWorkout(int workoutId, int userId) {
        String sql = "DELETE FROM workouts WHERE workout_id = ? AND user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, workoutId);
            statement.setInt(2, userId);

            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            System.out.println("Could not delete workout: " + exception.getMessage());
            return false;
        }
    }

    public String getProgressSummary(int userId) {
        String sql = "SELECT COUNT(*) AS total_workouts, COALESCE(SUM(duration_minutes), 0) AS total_minutes, COALESCE(SUM(calories_burned), 0) AS total_calories FROM workouts WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return progressReporter.getProgressMessage(
                            resultSet.getInt("total_workouts"),
                            resultSet.getInt("total_minutes"),
                            resultSet.getInt("total_calories"));
                }
            }
        } catch (SQLException exception) {
            return "Could not load progress summary: " + exception.getMessage();
        }

        return progressReporter.getProgressMessage(0, 0, 0);
    }

    private String validateWorkout(Workout workout) {
        if (workout == null || workout.getUserId() <= 0) {
            return "Invalid workout details.";
        }

        if (workout.getWorkoutName() == null || workout.getWorkoutName().trim().length() < 3) {
            return "Workout name must be at least 3 characters.";
        }

        if (workout.getDurationMinutes() <= 0 || workout.getDurationMinutes() > 600) {
            return "Duration must be between 1 and 600 minutes.";
        }

        if (workout.getCaloriesBurned() < 0 || workout.getCaloriesBurned() > 10000) {
            return "Calories must be between 0 and 10000.";
        }

        if (workout.getWorkoutDate() == null) {
            return "Workout date is required.";
        }

        return null;
    }
}
