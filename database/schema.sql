CREATE DATABASE IF NOT EXISTS fitness_db;
USE fitness_db;

CREATE TABLE IF NOT EXISTS users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    goal VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS workouts (
    workout_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    workout_name VARCHAR(100) NOT NULL,
    duration_minutes INT NOT NULL,
    calories_burned INT NOT NULL,
    workout_date DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
