# 25782__midterm - Academic Management System

## About This Project

I built this Android app for my midterm project - it's a complete academic management system that handles students, courses, and enrollments. Everything is written in Java (no Kotlin) and uses SQLite for data storage.

## What It Does

The app lets you:
- Add, edit, and delete students
- Manage courses with unique course codes
- Enroll students in courses
- View all enrollments
- Export everything to CSV

## Project Structure

I organized the code into clear packages:

```
com.example.a25782__midterm/
├── activities/          # All the screens
├── models/              # Student, Course, Enrollment classes
├── dao/                 # Database operations
└── utils/               # CSV export and preferences
```

## Database Design

I used SQLite with 3 tables:

**Students:** id, name, email, phone
**Courses:** id, courseName, courseCode (unique), credits  
**Enrollments:** id, student_id, course_id, date_enrolled

The database has foreign key constraints - when you delete a student or course, all their enrollments are automatically removed too.

## Key Features

### Main Screen
Clean interface with 4 main buttons to navigate to different sections.

### Student Management
- Add new students with name, email, and phone
- Click on any student in the list to edit their info
- Delete students (enrollments are automatically removed)

### Course Management  
- Add courses with name, code, and credits
- Course codes must be unique
- Edit or delete courses

### Enrollment System
- Two dropdowns to select student and course
- Prevents duplicate enrollments
- Shows all current enrollments with dates

### Data Export
- Export all data to CSV with timestamp
- Files saved to app storage

## Technical Details

**Database:** SQLite with foreign keys enabled
**Storage:** SharedPreferences for app settings
**UI:** Custom drawable shapes, gradient backgrounds
**Validation:** Input checking and duplicate prevention

## How to Run

1. Open in Android Studio
2. Sync Gradle
3. Run on emulator or device (API 31+)

## Usage

**Adding Students:**
1. Go to "Manage Student"
2. Fill in the form
3. Click "Save"

**Enrolling Students:**
1. Go to "Enroll Student" 
2. Pick a student and course from dropdowns
3. Click "Enroll Student"

**Exporting Data:**
1. Go to "View Enrollments"
2. Click "Export to CSV"
3. Check the toast message for file location

## What I Learned

This project helped me understand:
- SQLite database design with foreign keys
- Android activity lifecycle
- SharedPreferences for settings
- File I/O for CSV export
- Proper exception handling
- Clean code organization

## Requirements Met

✅ Java only (no Kotlin)
✅ SQLite with 3 tables and foreign keys
✅ Full CRUD operations
✅ ListView displays
✅ Spinner selections
✅ CSV export
✅ SharedPreferences
✅ Intent navigation
✅ Clean UI design

---

**Built by:** [Your Name]  
**Course:** 25782 Midterm  
**Date:** October 2025