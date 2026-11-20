package com.fitness.service;

public interface ProgressReporter {
    String getProgressMessage(int totalWorkouts, int totalMinutes, int totalCalories);
}
