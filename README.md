# Online Fitness Platform

Console-based Java project for user authentication, profile management, and workout tracking using MySQL and JDBC.

## Features
- User registration and login
- Workout add, view, update, and delete
- Profile view and update
- Progress summary
- OOP concepts: abstraction, encapsulation, inheritance, polymorphism
- Password hashing with SHA-256
- Stronger validation for registration, profile updates, and workout entries

## Project Structure
- `database/schema.sql`
- `src/com/fitness/Main.java`
- `src/com/fitness/app/FitnessApp.java`
- `src/com/fitness/db/DatabaseConnection.java`
- `src/com/fitness/model/Person.java`
- `src/com/fitness/model/User.java`
- `src/com/fitness/model/Workout.java`
- `src/com/fitness/service/AuthenticationService.java`
- `src/com/fitness/service/ProfileService.java`
- `src/com/fitness/service/WorkoutService.java`
- `src/com/fitness/service/ProgressReporter.java`
- `src/com/fitness/service/BasicProgressReporter.java`

## MySQL Setup
1. Open MySQL.
2. Run the script inside `database/schema.sql`.
3. Update database credentials in `src/com/fitness/db/DatabaseConnection.java` if your username or password is different.

## Compile
Add MySQL Connector/J to your project classpath before compiling.

Example:

```powershell
javac -cp ".;lib/mysql-connector-j-9.2.0.jar" -d out (Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName })
```

## Run

```powershell
java -cp ".;out;lib/mysql-connector-j-9.2.0.jar" com.fitness.Main
```

## Run GUI Version

```powershell
java -cp ".;out;lib/mysql-connector-j-9.2.0.jar" com.fitness.GuiMain
```

## Problems Faced and Fixes
- Authentication issue: solved by validating duplicate emails and matching credentials through prepared statements.
- Workout mapping issue: solved by linking each workout with `user_id`.
- Maintainability issue: solved by separating models, services, and database code.
- Input issue: solved by adding number and date validation in the console app.

## Academic Documentation
- `docs/project-synopsis.md`
- `docs/project-report.md`
- `docs/architecture-and-flow.md`
- `docs/viva-questions.md`
- `docs/submission-guide.md`
- `docs/ppt-content.md`
- `docs/diagrams.md`
- `docs/demo-script.md`
- `docs/screenshots-template.md`

## GUI Files
- `src/com/fitness/GuiMain.java`
- `src/com/fitness/ui/LoginFrame.java`
- `src/com/fitness/ui/RegisterFrame.java`
- `src/com/fitness/ui/DashboardFrame.java`
- `src/com/fitness/ui/UiTheme.java`

## GUI Notes
- The Swing version uses a shared theme for cleaner colors, spacing, and buttons.
- Select a workout row in the dashboard table to auto-fill the form before updating it.

## Security Notes
- New registrations store passwords as SHA-256 hashes instead of plain text.
- Older accounts created before this upgrade are automatically migrated to hashed passwords after one successful login.

## Batch Launchers
- `run-gui.bat`: starts the GUI if the project is already compiled
- `compile-and-run-gui.bat`: recompiles the project and then starts the GUI
