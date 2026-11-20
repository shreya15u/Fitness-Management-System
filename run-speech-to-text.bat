@echo off
setlocal

cd /d "%~dp0speech-to-text-web"

where python >nul 2>nul
if %errorlevel% neq 0 (
  echo Python was not found.
  echo Open speech-to-text-web\index.html in Chrome or Edge instead.
  pause
  exit /b 1
)

echo Starting Speech to Text Converter at http://localhost:5500
echo Press Ctrl+C to stop the server.
start "" "http://localhost:5500"
python -m http.server 5500
