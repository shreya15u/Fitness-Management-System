# Diagrams

## Flowchart

```mermaid
flowchart TD
    A["Start Application"] --> B["Show Main Menu"]
    B --> C{"Choose Option"}
    C -->|"Register"| D["Enter User Details"]
    C -->|"Login"| E["Enter Email and Password"]
    C -->|"Exit"| F["End Application"]
    D --> G["Save User in Database"]
    G --> B
    E --> H{"Credentials Valid?"}
    H -->|"No"| I["Show Error Message"]
    I --> B
    H -->|"Yes"| J["Open User Dashboard"]
    J --> K{"Select Dashboard Option"}
    K -->|"Add Workout"| L["Save Workout"]
    K -->|"View Workout"| M["Display Workout List"]
    K -->|"Update Workout"| N["Modify Workout Record"]
    K -->|"Delete Workout"| O["Remove Workout Record"]
    K -->|"View Progress"| P["Generate Summary"]
    K -->|"View Profile"| Q["Display Profile"]
    K -->|"Update Profile"| R["Update User Details"]
    K -->|"Logout"| S["Return to Main Menu"]
    L --> J
    M --> J
    N --> J
    O --> J
    P --> J
    Q --> J
    R --> J
    S --> B
```

## ER Diagram

```mermaid
flowchart LR
    U["users
    user_id (PK)
    full_name
    email
    password
    age
    goal"] -->|"1 to many"| W["workouts
    workout_id (PK)
    user_id (FK)
    workout_name
    duration_minutes
    calories_burned
    workout_date"]
```

## Use Case Summary
- Register account
- Login to system
- Add workout
- View workouts
- Update workout
- Delete workout
- View profile
- Update profile
- View progress summary

## Class Diagram Summary
- `Person`
- `User extends Person`
- `Workout`
- `AuthenticationService`
- `ProfileService`
- `WorkoutService`
- `DatabaseConnection`
- `FitnessApp`
