# 🐠 Swimmly – Mood-Based Task & Notes App

**Swimmly** is a simple yet powerful Android app that helps you stay productive by managing your tasks and notes in a calm, lightweight interface. Built to reflect your mood and help you “just keep swimming 🐟,” this app makes task planning feel natural and stress-free.

---

## 📱 Features

- 📝 Create, view, and edit personal notes
- 📁 All data saved locally using Room database (even after app is closed!)
- 🗑️ Long-press to delete a note (with Snackbar notification)
- 📦 Clean and modular project structure
- 💡 Future roadmap includes:
  - Mood tagging & sorting
  - Daily focus planner
  - GPT-based smart assistant
  - Firebase or .NET backend sync
  - Pomodoro timer

---

## 🧱 Tech Stack

| Layer        | Tech Used             |
|--------------|------------------------|
| Language     | Java (Android SDK)     |
| Database     | Room (SQLite wrapper)  |
| UI Layout    | XML + ViewBinding      |
| Version Control | Git + GitHub        |

---

## 🛠 Folder Structure

app/
├── java/
│ └── com.personal.swimmly/
│ ├── MainActivity.java
│ ├── AddNoteActivity.java
│ ├── adapter/NoteAdapter.java
│ └── data/Note.java / NoteDao.java / NoteDatabase.java
├── res/
│ ├── layout/ (XML Screens)
│ ├── mipmap/ (App Icons)
│ ├── values/ (colors, strings, themes)

---

## 🚀 Getting Started

1. Clone this repository:

git clone https://github.com/nupurgupta30/Swimmly.git
2. Open in Android Studio
3. Click Run ▶️
Minimum SDK: Android 5.0 (API 21)

## Inspiration
“Just keep swimming.” — Dory
This app is your digital reminder to keep going — small steps, calm focus, daily wins. ✨

🧠 Made With
Built with ☕ Java, 💙 by Nupur Gupta
