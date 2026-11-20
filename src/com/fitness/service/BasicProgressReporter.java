package com.fitness.service;

public class BasicProgressReporter implements ProgressReporter {
    @Override
    public String getProgressMessage(int totalWorkouts, int totalMinutes, int totalCalories) {
        return String.format(
                "Progress Summary: %d workouts completed, %d total minutes trained, %d total calories burned.",
                totalWorkouts, totalMinutes, totalCalories);
    }
}
