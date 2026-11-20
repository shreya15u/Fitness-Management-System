@echo off
setlocal
cd /d "%~dp0"

if not exist "out\com\fitness\GuiMain.class" (
    echo Compiled classes not found. Please run compile-and-run-gui.bat first.
    pause
    exit /b 1
)

if not exist "lib\mysql-connector-j-9.6.0.jar" (
    echo MySQL JDBC driver not found at lib\mysql-connector-j-9.6.0.jar
    pause
    exit /b 1
)

java -cp ".;out;lib/mysql-connector-j-9.6.0.jar" com.fitness.GuiMain

if errorlevel 1 (
    echo.
    echo Application closed with an error.
    pause
)
