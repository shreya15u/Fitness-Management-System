package com.fitness.app;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import com.fitness.model.User;
import com.fitness.model.Workout;
import com.fitness.service.AuthenticationService;
import com.fitness.service.BasicProgressReporter;
import com.fitness.service.ProfileService;
import com.fitness.service.WorkoutService;

public class FitnessApp {
    private final Scanner scanner;
    private final AuthenticationService authenticationService;
    private final WorkoutService workoutService;
    private final ProfileService profileService;

    public FitnessApp() {
        scanner = new Scanner(System.in);
        authenticationService = new AuthenticationService();
        workoutService = new WorkoutService(new BasicProgressReporter());
        profileService = new ProfileService();
    }

    public void start() {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Online Fitness Platform ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = readInt();
            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    running = false;
                    System.out.println("Thank you for using the Online Fitness Platform.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }

        scanner.close();
    }

    private void registerUser() {
        System.out.println("\n--- User Registration ---");
        System.out.print("Full name: ");
        String fullName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Age: ");
        int age = readInt();
        System.out.print("Fitness goal: ");
        String goal = scanner.nextLine();

        User user = new User(fullName, email, password, age, goal);
        boolean isRegistered = authenticationService.registerUser(user);

        if (isRegistered) {
            System.out.println("Registration successful. You can now log in.");
        } else {
            System.out.println("Registration unsuccessful.");
        }
    }

    private void loginUser() {
        System.out.println("\n--- User Login ---");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = authenticationService.loginUser(email, password);
        if (user != null) {
            System.out.println("Login successful. Welcome, " + user.getFullName() + ".");
            userDashboard(user);
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    private void userDashboard(User user) {
        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("\n=== User Dashboard ===");
            System.out.println("1. Add Workout");
            System.out.println("2. View Workouts");
            System.out.println("3. Update Workout");
            System.out.println("4. Delete Workout");
            System.out.println("5. View Progress Summary");
            System.out.println("6. View Profile");
            System.out.println("7. Update Profile");
            System.out.println("8. Logout");
            System.out.print("Choose an option: ");

            int choice = readInt();
            switch (choice) {
                case 1:
                    addWorkout(user);
                    break;
                case 2:
                    viewWorkouts(user);
                    break;
                case 3:
                    updateWorkout(user);
                    break;
                case 4:
                    deleteWorkout(user);
                    break;
                case 5:
                    System.out.println(workoutService.getProgressSummary(user.getId()));
                    break;
                case 6:
                    viewProfile(user);
                    break;
                case 7:
                    updateProfile(user);
                    break;
                case 8:
                    loggedIn = false;
                    System.out.println("You have been logged out.");
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
                    break;
            }
        }
    }

    private void addWorkout(User user) {
        System.out.println("\n--- Add Workout ---");
        System.out.print("Workout name: ");
        String workoutName = scanner.nextLine();
        System.out.print("Duration in minutes: ");
        int durationMinutes = readInt();
        System.out.print("Calories burned: ");
        int caloriesBurned = readInt();
        LocalDate workoutDate = readDate("Workout date (YYYY-MM-DD): ");

        Workout workout = new Workout(user.getId(), workoutName, durationMinutes, caloriesBurned, workoutDate);
        boolean saved = workoutService.addWorkout(workout);
        System.out.println(saved ? "Workout saved successfully." : "Workout could not be saved.");
    }

    private void viewWorkouts(User user) {
        System.out.println("\n--- Workout History ---");
        List<Workout> workouts = workoutService.getWorkoutsByUserId(user.getId());

        if (workouts.isEmpty()) {
            System.out.println("No workouts found.");
            return;
        }

        for (Workout workout : workouts) {
            System.out.printf("ID: %d | Name: %s | Duration: %d mins | Calories: %d | Date: %s%n",
                    workout.getWorkoutId(),
                    workout.getWorkoutName(),
                    workout.getDurationMinutes(),
                    workout.getCaloriesBurned(),
                    workout.getWorkoutDate());
        }
    }

    private void updateWorkout(User user) {
        viewWorkouts(user);
        System.out.print("Enter workout ID to update: ");
        int workoutId = readInt();
        System.out.print("New workout name: ");
        String workoutName = scanner.nextLine();
        System.out.print("New duration in minutes: ");
        int durationMinutes = readInt();
        System.out.print("New calories burned: ");
        int caloriesBurned = readInt();
        LocalDate workoutDate = readDate("New workout date (YYYY-MM-DD): ");

        Workout workout = new Workout(workoutId, user.getId(), workoutName, durationMinutes, caloriesBurned, workoutDate);
        boolean updated = workoutService.updateWorkout(workout);
        System.out.println(updated ? "Workout updated successfully." : "Workout update failed.");
    }

    private void deleteWorkout(User user) {
        viewWorkouts(user);
        System.out.print("Enter workout ID to delete: ");
        int workoutId = readInt();

        boolean deleted = workoutService.deleteWorkout(workoutId, user.getId());
        System.out.println(deleted ? "Workout deleted successfully." : "Workout delete failed.");
    }

    private void viewProfile(User user) {
        System.out.println("\n--- Profile ---");
        System.out.println("Name: " + user.getFullName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Age: " + user.getAge());
        System.out.println("Goal: " + user.getGoal());
        System.out.println("Role: " + user.getRoleDescription());
    }

    private void updateProfile(User user) {
        System.out.println("\n--- Update Profile ---");
        System.out.print("New full name: ");
        user.setFullName(scanner.nextLine());
        System.out.print("New age: ");
        user.setAge(readInt());
        System.out.print("New fitness goal: ");
        user.setGoal(scanner.nextLine());

        boolean updated = profileService.updateProfile(user);
        System.out.println(updated ? "Profile updated successfully." : "Profile update failed.");
    }

    private int readInt() {
        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if (value < 0) {
                    System.out.print("Enter a valid positive number: ");
                    continue;
                }
                return value;
            } catch (NumberFormatException exception) {
                System.out.print("Enter a valid number: ");
            }
        }
    }

    private LocalDate readDate(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException exception) {
                System.out.println("Please enter the date in YYYY-MM-DD format.");
            }
        }
    }
}
