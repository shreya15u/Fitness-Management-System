# Viva and Interview Questions

## 1. What is the main purpose of this project?
The main purpose of this project is to help users manage their fitness records digitally through registration, login, workout tracking, profile management, and progress summary features.

## 2. Which technologies did you use in this project?
I used Java for application development, MySQL for database storage, JDBC for connectivity, and OOP principles for system design.

## 3. Why did you choose MySQL?
I chose MySQL because it is easy to use, reliable, and suitable for storing structured data such as user details and workout records.

## 4. Which OOP concepts did you implement?
I implemented encapsulation, inheritance, abstraction, and polymorphism.

## 5. How did you use encapsulation in this project?
I used private data members in classes like `User` and `Workout` and accessed them using getter and setter methods.

## 6. How did you use inheritance?
The `User` class extends the abstract `Person` class, so common fields like id, name, and email are inherited.

## 7. How did you use abstraction?
I used an abstract `Person` class to define common properties and behavior for a user.

## 8. How did you use polymorphism?
I used the `ProgressReporter` interface and implemented it in `BasicProgressReporter`. This allows multiple progress summary behaviors in the future.

## 9. What problems did you face during development?
I faced issues with database connectivity, login validation, user-specific workout mapping, input validation, and keeping the project structure clean.

## 10. How did you solve database connection issues?
I verified the JDBC URL, username, password, and used a dedicated database connection class to handle connectivity properly.

## 11. Why did you use prepared statements?
I used prepared statements to execute SQL queries safely and clearly while avoiding direct string concatenation.

## 12. How are workouts linked to the user?
Each workout record is linked to a specific user through the `user_id` foreign key in the workouts table.

## 13. What are the main classes in this project?
The main classes are `Main`, `FitnessApp`, `DatabaseConnection`, `Person`, `User`, `Workout`, `AuthenticationService`, `ProfileService`, and `WorkoutService`.

## 14. What is the future scope of this project?
In future, the project can be extended with GUI support, password encryption, trainer/admin roles, diet plans, notifications, and report generation.

## 15. Why is this project important for learning?
This project is important because it combines Java programming, database integration, OOP design, and practical debugging in one complete application.
