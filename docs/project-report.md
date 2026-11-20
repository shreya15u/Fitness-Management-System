

# Online Fitness Platform Project Report

## 1. Introduction
The Online Fitness Platform is a mini-project developed using Java and MySQL. The aim of the project is to provide a system where users can register, log in, and maintain their workout details in a structured way. The project also demonstrates strong use of Object-Oriented Programming concepts and JDBC-based database connectivity.

## 2. Motivation
Fitness tracking is an important part of maintaining a healthy lifestyle. Many people fail to keep consistent records of their activities because they depend on manual tracking methods. This project was created to simplify fitness record management and provide a digital way to store workout information.

## 3. Problem Definition
Manual tracking of workout records is time-consuming and unorganized. Users may lose records or fail to analyze progress over time. Existing systems can also be too complex for beginners. Therefore, a simple fitness platform was required that supports secure authentication and easy workout management.

## 4. Objectives
- To create a secure user registration and login system
- To allow users to add, view, update, and delete workout records
- To connect Java with MySQL using JDBC
- To apply OOP concepts to create a modular and maintainable system
- To generate workout progress summaries for users

## 5. Tools and Technologies
- Java: for application logic
- MySQL: for database storage
- JDBC: for connection between Java and MySQL
- OOP: for system design and modular coding

## 6. System Modules

### 6.1 Registration Module
Allows a new user to create an account by entering personal details such as name, email, password, age, and fitness goal.

### 6.2 Login Module
Checks user credentials and grants access only if the entered email and password match a valid record in the database.

### 6.3 Workout Tracking Module
Enables users to add workout name, duration, calories burned, and workout date. It also supports update and delete operations.

### 6.4 Profile Module
Displays user details and allows the user to update personal information such as name, age, and goal.

### 6.5 Progress Summary Module
Calculates the total number of workouts, total time spent, and total calories burned by a user.

## 7. Database Design

### Users Table
- `user_id`
- `full_name`
- `email`
- `password`
- `age`
- `goal`

### Workouts Table
- `workout_id`
- `user_id`
- `workout_name`
- `duration_minutes`
- `calories_burned`
- `workout_date`

The `user_id` in the workouts table acts as a foreign key to map workouts to the correct user.

## 8. System Design
The application follows a layered structure:
- Model layer for entity classes such as `User` and `Workout`
- Service layer for business logic such as authentication and workout operations
- Database layer for connection handling
- Application layer for console-based interaction

## 9. Development Process

### Step 1: Requirement Analysis
The first step was identifying the main requirements of the project: registration, login, workout tracking, and progress monitoring.

### Step 2: Database Planning
The database schema was designed with two main tables: users and workouts.

### Step 3: OOP-Based Class Design
The application structure was divided into classes such as `Person`, `User`, `Workout`, `AuthenticationService`, `WorkoutService`, and `ProfileService`.

### Step 4: JDBC Integration
Java was connected with MySQL using JDBC and prepared statements were used for database operations.

### Step 5: Console UI Development
A menu-driven interface was created to help users interact with the application.

### Step 6: Testing and Debugging
Each module was tested separately, then the full system was tested end to end.

## 10. Problems Faced and Solutions

### Problem 1: Poor Initial Code Structure
At first, writing all logic inside one place made the project difficult to manage.

Solution:
The code was divided into models, services, and database classes to improve readability and maintenance.

### Problem 2: Database Connection Errors
The application initially failed to connect to MySQL because of driver and credential setup issues.

Solution:
The JDBC connection string, username, and password were verified carefully and kept in a dedicated connection class.

### Problem 3: Login Validation Issues
The login module did not correctly validate credentials in the beginning.

Solution:
Prepared statements were used with proper SQL conditions to securely match stored records.

### Problem 4: User-Specific Workout Mapping
Workout records were not easy to separate user-wise without proper mapping.

Solution:
The workouts table was linked to the users table using `user_id` as a foreign key.

### Problem 5: Input Handling Problems
Invalid numeric input and incorrect date formats caused runtime issues.

Solution:
Validation was added in the console application for integers and date values.

## 11. OOP Principles Implemented

### Encapsulation
Private fields are used in the `User` and `Workout` classes with public getter and setter methods.

### Inheritance
The `User` class extends the abstract `Person` class and reuses common attributes.

### Abstraction
The `Person` class provides common structure while hiding implementation details.

### Polymorphism
The `ProgressReporter` interface allows different implementations for generating progress summaries.

## 12. Testing
The following test scenarios were covered:
- Registering a new user
- Preventing duplicate email registration
- Logging in with valid and invalid credentials
- Adding a workout
- Viewing workout history
- Updating a workout record
- Deleting a workout record
- Updating user profile
- Viewing progress summary

## 13. Output Summary
The final application successfully allows users to:
- Register and log in
- Manage workout records
- Update profiles
- Track total progress

## 14. Future Scope
- Add password encryption
- Build a Java Swing or web-based GUI
- Add trainer or admin roles
- Add diet plans and notifications
- Generate printable reports

## 15. Conclusion
The Online Fitness Platform was successfully developed as a Java and MySQL mini-project. It achieved its goal of simplifying workout record management and provided practical learning in software design, database handling, and debugging. The project also demonstrates how OOP concepts can be applied in a real application.
