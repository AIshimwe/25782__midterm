# 25782__midterm - Academic Management System

## 📋 Project Overview
This is a complete Android application built using **Java only** that implements an Academic Management System. The app manages Students, Courses, and Enrollments with full CRUD operations, data persistence using SQLite, and CSV export functionality.

## 🏗️ Architecture

### Package Structure
```
com.example.a25782__midterm/
├── activities/          # All Activity classes
│   ├── MainActivity.java
│   ├── ManageStudentActivity.java
│   ├── ManageCourseActivity.java
│   ├── EnrollStudentActivity.java
│   └── ViewEnrollmentsActivity.java
├── models/              # Data model classes
│   ├── Student.java
│   ├── Course.java
│   └── Enrollment.java
├── dao/                 # Database access objects
│   ├── DBHelper.java
│   ├── StudentDAO.java
│   ├── CourseDAO.java
│   └── EnrollmentDAO.java
└── utils/               # Utility classes
    ├── CSVExporter.java
    └── PreferencesHelper.java
```

## 🗄️ Database Schema

### Students Table
- `id` (INTEGER, PRIMARY KEY, AUTOINCREMENT)
- `name` (TEXT, NOT NULL)
- `email` (TEXT, NOT NULL)
- `phone` (TEXT, NOT NULL)

### Courses Table
- `id` (INTEGER, PRIMARY KEY, AUTOINCREMENT)
- `courseName` (TEXT, NOT NULL)
- `courseCode` (TEXT, NOT NULL, UNIQUE)
- `credits` (INTEGER, NOT NULL)

### Enrollments Table
- `id` (INTEGER, PRIMARY KEY, AUTOINCREMENT)
- `student_id` (INTEGER, FOREIGN KEY → students.id)
- `course_id` (INTEGER, FOREIGN KEY → courses.id)
- `date_enrolled` (TEXT, NOT NULL)

**Note:** Foreign key constraints are enabled to maintain referential integrity.

## 📱 Features

### 1. MainActivity (Home Screen)
- Clean academic-style UI with gradient background
- Four main navigation buttons:
  - **Manage Student** → ManageStudentActivity
  - **Manage Course** → ManageCourseActivity
  - **Enroll Student** → EnrollStudentActivity
  - **View Enrollments** → ViewEnrollmentsActivity

### 2. ManageStudentActivity
**CRUD Operations for Students:**
- ✅ **Create:** Add new students with name, email, and phone
- ✅ **Read:** View all students in a ListView
- ✅ **Update:** Select a student from the list and update their information
- ✅ **Delete:** Remove students from the database
- ✅ Click on any student in the list to populate the form fields

### 3. ManageCourseActivity
**CRUD Operations for Courses:**
- ✅ **Create:** Add new courses with name, code, and credits
- ✅ **Read:** View all courses in a ListView
- ✅ **Update:** Select a course from the list and modify its details
- ✅ **Delete:** Remove courses from the database
- ✅ Course codes are unique (enforced by database constraint)

### 4. EnrollStudentActivity
**Enrollment Management:**
- 📋 Two Spinners (dropdowns) for selecting:
  - Student (populated from database)
  - Course (populated from database)
- ✅ **Enroll Button:** Creates enrollment with current date
- ✅ **Duplicate Prevention:** Checks if student is already enrolled in the course
- ✅ **ListView:** Displays all current enrollments with student names and course names

### 5. ViewEnrollmentsActivity
**View & Export:**
- 📋 ListView displaying all enrollments with full details:
  - Student Name → Course Name (Date)
- 📤 **Export to CSV:** Exports all data (students, courses, enrollments) to a CSV file
- 💾 CSV files are saved to internal storage with timestamp

## 🎨 UI Design

### Color Scheme
- **Primary Blue:** `#1565C0` (academic/professional tone)
- **Primary Dark:** `#0D47A1`
- **Accent Orange:** `#FFA726`
- **Background Gradient:** Light blue gradient (`#E3F2FD` → `#90CAF9`)

### UI Components
- **Rounded Buttons:** 12dp corner radius for main menu, 8dp for action buttons
- **EditText Fields:** Custom border with 8dp rounded corners
- **ListView:** Clean dividers for easy reading
- **Consistent Spacing:** 16dp padding throughout

## 💾 Data Persistence

### SQLite Database
- **Database Name:** `AcademicManagement.db`
- **Version:** 1
- **Features:**
  - Foreign key constraints enabled
  - CASCADE delete for enrollments when students/courses are deleted
  - Proper exception handling in all database operations
  - Automatic database connection closing

### SharedPreferences
The `PreferencesHelper` class provides:
- **Last Screen Tracking:** Saves which screen was last opened
- **Theme Preference:** Dark/light theme toggle (placeholder)
- **Reminders:** User preference for showing reminders
- **First Launch Detection:** Tracks if this is the first time the app is opened
- **Custom Key-Value Storage:** Generic methods for saving preferences

## 📤 CSV Export

The `CSVExporter` class provides:
- **Export All Data:** Single CSV file with all students, courses, and enrollments
- **Individual Exports:** Separate methods for students, courses, or enrollments
- **Timestamp Naming:** Files named with format `all_data_YYYYMMDD_HHMMSS.csv`
- **Location:** Files saved to app's internal storage
- **CSV Escaping:** Proper handling of commas and quotes in data

## 🔧 Technical Specifications

### Build Configuration
- **Language:** Java (100% - no Kotlin)
- **Minimum SDK:** 31 (Android 12)
- **Target SDK:** 36
- **Compile SDK:** 36
- **Java Version:** 11

### Dependencies
- `androidx.appcompat`
- `com.google.android.material`
- `androidx.activity`
- `androidx.constraintlayout`

## 🚀 How to Run

1. **Open in Android Studio:**
   ```
   File → Open → Select this project folder
   ```

2. **Sync Gradle:**
   - Android Studio will automatically sync Gradle files
   - Wait for the build to complete

3. **Run the App:**
   - Connect an Android device (API 31+) or start an emulator
   - Click the "Run" button or press `Shift + F10`

## 📖 Usage Instructions

### Adding Students
1. Open "Manage Student"
2. Fill in Name, Email, and Phone
3. Click "Save"
4. Student appears in the list below

### Updating Students
1. Open "Manage Student"
2. Click on a student in the list
3. Modify the fields
4. Click "Update"

### Adding Courses
1. Open "Manage Course"
2. Fill in Course Name, Course Code, and Credits
3. Click "Save"
4. Course appears in the list below

### Enrolling Students
1. Open "Enroll Student"
2. Select a student from the dropdown
3. Select a course from the dropdown
4. Click "Enroll Student"
5. Enrollment appears in the list with current date

### Exporting Data
1. Open "View Enrollments"
2. Click "Export to CSV"
3. Toast message shows the file location
4. File can be found in app's internal storage

## 🔒 Error Handling

- ✅ **SQLite Exceptions:** All database operations wrapped in try-catch-finally
- ✅ **Input Validation:** Checks for empty fields before saving
- ✅ **Duplicate Prevention:** Prevents duplicate enrollments
- ✅ **Foreign Key Constraints:** Database enforces referential integrity
- ✅ **User Feedback:** Toast messages for all operations (success/failure)

## 📝 Code Quality

- ✅ **Clean Code:** Well-organized with clear naming conventions
- ✅ **Separation of Concerns:** Activities, Models, DAO, and Utils properly separated
- ✅ **Resource Management:** Database connections properly closed
- ✅ **DRY Principle:** Reusable DAO and utility classes
- ✅ **Consistent Styling:** Uniform UI across all screens
- ✅ **No Linter Errors:** Code passes all Android Studio linter checks

## 🎯 Requirements Checklist

- ✅ Java only (no Kotlin)
- ✅ SQLite database with 3 tables
- ✅ CRUD operations for Students and Courses
- ✅ Enrollment management with foreign keys
- ✅ ListView displays with simple_list_item_1
- ✅ Spinners for student/course selection
- ✅ CSV export functionality
- ✅ SharedPreferences implementation
- ✅ Intent-based navigation between activities
- ✅ Proper package organization (activities, models, dao, utils)
- ✅ Clean academic-style UI with background
- ✅ Rounded buttons with consistent styling
- ✅ All activities have Back buttons
- ✅ Exception handling in all database operations
- ✅ AndroidManifest properly configured

## 🔍 Testing

### Manual Testing Checklist
- [ ] Add multiple students
- [ ] Update student information
- [ ] Delete a student
- [ ] Add multiple courses
- [ ] Update course information
- [ ] Delete a course
- [ ] Enroll student in course
- [ ] Try to enroll same student in same course (should fail)
- [ ] View all enrollments
- [ ] Export data to CSV
- [ ] Navigate between all screens
- [ ] Test Back buttons

## 🌟 Additional Features

- **Automatic Date Recording:** Enrollment date is automatically set to current date
- **Sorted Lists:** Students sorted by name, courses by code
- **User-Friendly Display:** ListView items show ID, name/code, and relevant details
- **Last Screen Tracking:** App remembers which screen was last visited
- **Timestamp Tracking:** Last update time saved in SharedPreferences

## 📞 Support

For issues or questions about this project, please refer to the code comments and this README.

---

**Project Name:** 25782__midterm  
**Type:** Android Application (Java)  
**Purpose:** Academic Management System  
**Date:** October 2025


