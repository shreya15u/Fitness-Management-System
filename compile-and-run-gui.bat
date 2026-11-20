@echo off
setlocal
cd /d "%~dp0"

if not exist "lib\mysql-connector-j-9.6.0.jar" (
    echo MySQL JDBC driver not found at lib\mysql-connector-j-9.6.0.jar
    pause
    exit /b 1
)

if not exist "out" mkdir out

echo Compiling project...
javac -d out src\com\fitness\*.java src\com\fitness\app\*.java src\com\fitness\db\*.java src\com\fitness\model\*.java src\com\fitness\service\*.java src\com\fitness\ui\*.java src\com\fitness\util\*.java

if errorlevel 1 (
    echo.
    echo Compilation failed.
    pause
    exit /b 1
)

echo Starting GUI application...
java -cp ".;out;lib/mysql-connector-j-9.6.0.jar" com.fitness.GuiMain

if errorlevel 1 (
    echo.
    echo Application closed with an error.
    pause
)
