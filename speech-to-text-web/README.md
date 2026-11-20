# Speech to Text Converter

A small browser-based project that converts microphone speech into editable text.

## Features

- Start and stop live speech recognition
- Shows interim and final transcript text
- Supports English India, English US, Hindi, Bengali, Tamil, and Telugu
- Copy transcript to clipboard
- Download transcript as a `.txt` file
- Clear and edit transcript

## Requirements

- Google Chrome or Microsoft Edge
- Working microphone
- Microphone permission enabled in the browser

## Run

Open `index.html` directly in Chrome or Edge.

If microphone access is blocked when opening the file directly, run a local server:

```powershell
cd "C:\Users\hp\Desktop\Fitness Management System\speech-to-text-web"
python -m http.server 5500
```

Then open:

```text
http://localhost:5500
```

## How It Works

This project uses the browser `SpeechRecognition` API. The audio is handled by the browser speech service, then the recognized text is displayed in the transcript box.

## Files

- `index.html`: page structure
- `style.css`: user interface styling
- `script.js`: speech recognition logic
