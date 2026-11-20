package com.fitness.model;

import java.time.LocalDate;

public class Workout {
    private int workoutId;
    private int userId;
    private String workoutName;
    private int durationMinutes;
    private int caloriesBurned;
    private LocalDate workoutDate;

    public Workout() {
    }

    public Workout(int workoutId, int userId, String workoutName, int durationMinutes, int caloriesBurned,
            LocalDate workoutDate) {
        this.workoutId = workoutId;
        this.userId = userId;
        this.workoutName = workoutName;
        this.durationMinutes = durationMinutes;
        this.caloriesBurned = caloriesBurned;
        this.workoutDate = workoutDate;
    }

    public Workout(int userId, String workoutName, int durationMinutes, int caloriesBurned, LocalDate workoutDate) {
        this(0, userId, workoutName, durationMinutes, caloriesBurned, workoutDate);
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public LocalDate getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(LocalDate workoutDate) {
        this.workoutDate = workoutDate;
    }
}
