const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;

const startBtn = document.getElementById("startBtn");
const stopBtn = document.getElementById("stopBtn");
const copyBtn = document.getElementById("copyBtn");
const downloadBtn = document.getElementById("downloadBtn");
const clearBtn = document.getElementById("clearBtn");
const languageSelect = document.getElementById("language");
const transcript = document.getElementById("transcript");
const statusText = document.getElementById("status");
const pulse = document.getElementById("pulse");

let recognition;
let finalText = "";

function setStatus(message, isListening = false) {
  statusText.textContent = message;
  pulse.classList.toggle("active", isListening);
  startBtn.disabled = isListening;
  stopBtn.disabled = !isListening;
}

function updateTranscript(interimText = "") {
  const spacer = finalText && interimText ? " " : "";
  transcript.value = `${finalText}${spacer}${interimText}`.trim();
}

function createRecognition() {
  const instance = new SpeechRecognition();
  instance.continuous = true;
  instance.interimResults = true;
  instance.lang = languageSelect.value;

  instance.onstart = () => {
    setStatus("Listening... speak clearly into your microphone.", true);
  };

  instance.onresult = (event) => {
    let interimText = "";

    for (let i = event.resultIndex; i < event.results.length; i += 1) {
      const result = event.results[i];
      const text = result[0].transcript.trim();

      if (result.isFinal) {
        finalText = `${finalText} ${text}`.trim();
      } else {
        interimText = `${interimText} ${text}`.trim();
      }
    }

    updateTranscript(interimText);
  };

  instance.onerror = (event) => {
    const message =
      event.error === "not-allowed"
        ? "Microphone permission was blocked. Allow microphone access and try again."
        : `Speech recognition error: ${event.error}`;

    setStatus(message, false);
  };

  instance.onend = () => {
    setStatus("Stopped. You can edit, copy, or download the transcript.", false);
  };

  return instance;
}

if (!SpeechRecognition) {
  setStatus("Speech recognition is not supported in this browser. Use Chrome or Microsoft Edge.");
  startBtn.disabled = true;
} else {
  recognition = createRecognition();
}

startBtn.addEventListener("click", () => {
  if (!recognition) {
    return;
  }

  recognition.lang = languageSelect.value;
  recognition.start();
});

stopBtn.addEventListener("click", () => {
  if (recognition) {
    recognition.stop();
  }
});

copyBtn.addEventListener("click", async () => {
  if (!transcript.value.trim()) {
    setStatus("There is no text to copy.");
    return;
  }

  await navigator.clipboard.writeText(transcript.value);
  setStatus("Transcript copied to clipboard.");
});

downloadBtn.addEventListener("click", () => {
  if (!transcript.value.trim()) {
    setStatus("There is no text to download.");
    return;
  }

  const blob = new Blob([transcript.value], { type: "text/plain" });
  const url = URL.createObjectURL(blob);
  const link = document.createElement("a");

  link.href = url;
  link.download = `speech-transcript-${new Date().toISOString().slice(0, 10)}.txt`;
  link.click();
  URL.revokeObjectURL(url);
  setStatus("Transcript downloaded.");
});

clearBtn.addEventListener("click", () => {
  finalText = "";
  transcript.value = "";
  setStatus("Transcript cleared. Press Start Listening to begin again.");
});

transcript.addEventListener("input", () => {
  finalText = transcript.value.trim();
});
