# Architecture and Flow

## Architecture
- `Main` starts the program
- `FitnessApp` controls the menu and user interaction
- `AuthenticationService` handles registration and login
- `ProfileService` handles profile updates
- `WorkoutService` handles workout CRUD operations and progress summary
- `DatabaseConnection` provides JDBC access to MySQL
- `User` and `Workout` represent the system data

## Execution Flow
1. The program starts from `Main.java`
2. The user chooses Register, Login, or Exit
3. On registration, user data is validated and saved into the `users` table
4. On login, email and password are checked from the database
5. After login, the user enters the dashboard
6. The user can add, view, update, or delete workouts
7. Workout records are stored in the `workouts` table using `user_id`
8. The user can view a profile and update personal details
9. The progress summary is generated using aggregate database queries

## Why This Structure Was Chosen
- It keeps the code modular
- It avoids putting all logic in one file
- It makes debugging easier
- It clearly shows OOP usage for academic evaluation
