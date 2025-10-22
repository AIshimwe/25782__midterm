package com.example.a25782__midterm.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.a25782__midterm.models.Course;
import com.example.a25782__midterm.models.Enrollment;
import com.example.a25782__midterm.models.Student;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CSVExporter {

    private Context context;

    public CSVExporter(Context context) {
        this.context = context;
    }

    // Helper method to get the appropriate directory for saving files
    private File getExportDirectory() {
        // For Android 10+ (API 29+), use the app's external files directory
        // This is accessible via file managers and doesn't require special permissions
        File appExternalDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (appExternalDir != null) {
            if (!appExternalDir.exists()) {
                appExternalDir.mkdirs();
            }
            Log.d("CSVExporter", "Using app external Downloads directory: " + appExternalDir.getAbsolutePath());
            return appExternalDir;
        }
        
        // Fallback to app's external files directory
        File externalFilesDir = context.getExternalFilesDir(null);
        if (externalFilesDir != null) {
            if (!externalFilesDir.exists()) {
                externalFilesDir.mkdirs();
            }
            Log.d("CSVExporter", "Using external files directory: " + externalFilesDir.getAbsolutePath());
            return externalFilesDir;
        }
        
        // Last resort: app's internal files directory
        Log.d("CSVExporter", "Using internal files directory: " + context.getFilesDir().getAbsolutePath());
        return context.getFilesDir();
    }

    // Export students to CSV
    public String exportStudents(List<Student> students) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "students_" + timestamp + ".csv";
        
        try {
            File exportDir = getExportDirectory();
            File file = new File(exportDir, fileName);
            FileWriter writer = new FileWriter(file);
            
            // Write header
            writer.append("ID,Name,Email,Phone\n");
            
            // Write data
            for (Student student : students) {
                writer.append(String.valueOf(student.getId())).append(",");
                writer.append(escapeCSV(student.getName())).append(",");
                writer.append(escapeCSV(student.getEmail())).append(",");
                writer.append(escapeCSV(student.getPhone())).append("\n");
            }
            
            writer.flush();
            writer.close();
            
            Log.d("CSVExporter", "Students exported to: " + file.getAbsolutePath());
            return file.getAbsolutePath();
        } catch (IOException e) {
            Log.e("CSVExporter", "Error exporting students", e);
            e.printStackTrace();
            return null;
        }
    }

    // Export courses to CSV
    public String exportCourses(List<Course> courses) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "courses_" + timestamp + ".csv";
        
        try {
            File exportDir = getExportDirectory();
            File file = new File(exportDir, fileName);
            FileWriter writer = new FileWriter(file);
            
            // Write header
            writer.append("ID,Course Name,Course Code,Credits\n");
            
            // Write data
            for (Course course : courses) {
                writer.append(String.valueOf(course.getId())).append(",");
                writer.append(escapeCSV(course.getCourseName())).append(",");
                writer.append(escapeCSV(course.getCourseCode())).append(",");
                writer.append(String.valueOf(course.getCredits())).append("\n");
            }
            
            writer.flush();
            writer.close();
            
            Log.d("CSVExporter", "Courses exported to: " + file.getAbsolutePath());
            return file.getAbsolutePath();
        } catch (IOException e) {
            Log.e("CSVExporter", "Error exporting courses", e);
            e.printStackTrace();
            return null;
        }
    }

    // Export enrollments to CSV
    public String exportEnrollments(List<Enrollment> enrollments) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "enrollments_" + timestamp + ".csv";
        
        try {
            File exportDir = getExportDirectory();
            File file = new File(exportDir, fileName);
            FileWriter writer = new FileWriter(file);
            
            // Write header
            writer.append("ID,Student ID,Student Name,Course ID,Course Name,Date Enrolled\n");
            
            // Write data
            for (Enrollment enrollment : enrollments) {
                writer.append(String.valueOf(enrollment.getId())).append(",");
                writer.append(String.valueOf(enrollment.getStudentId())).append(",");
                writer.append(escapeCSV(enrollment.getStudentName())).append(",");
                writer.append(String.valueOf(enrollment.getCourseId())).append(",");
                writer.append(escapeCSV(enrollment.getCourseName())).append(",");
                writer.append(escapeCSV(enrollment.getDateEnrolled())).append("\n");
            }
            
            writer.flush();
            writer.close();
            
            Log.d("CSVExporter", "Enrollments exported to: " + file.getAbsolutePath());
            return file.getAbsolutePath();
        } catch (IOException e) {
            Log.e("CSVExporter", "Error exporting enrollments", e);
            e.printStackTrace();
            return null;
        }
    }

    // Export all data to a single CSV file
    public String exportAllData(List<Student> students, List<Course> courses, List<Enrollment> enrollments) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "all_data_" + timestamp + ".csv";
        
        try {
            File exportDir = getExportDirectory();
            File file = new File(exportDir, fileName);
            FileWriter writer = new FileWriter(file);
            
            // Students section
            writer.append("=== STUDENTS ===\n");
            writer.append("ID,Name,Email,Phone\n");
            for (Student student : students) {
                writer.append(String.valueOf(student.getId())).append(",");
                writer.append(escapeCSV(student.getName())).append(",");
                writer.append(escapeCSV(student.getEmail())).append(",");
                writer.append(escapeCSV(student.getPhone())).append("\n");
            }
            writer.append("\n");
            
            // Courses section
            writer.append("=== COURSES ===\n");
            writer.append("ID,Course Name,Course Code,Credits\n");
            for (Course course : courses) {
                writer.append(String.valueOf(course.getId())).append(",");
                writer.append(escapeCSV(course.getCourseName())).append(",");
                writer.append(escapeCSV(course.getCourseCode())).append(",");
                writer.append(String.valueOf(course.getCredits())).append("\n");
            }
            writer.append("\n");
            
            // Enrollments section
            writer.append("=== ENROLLMENTS ===\n");
            writer.append("ID,Student ID,Student Name,Course ID,Course Name,Date Enrolled\n");
            for (Enrollment enrollment : enrollments) {
                writer.append(String.valueOf(enrollment.getId())).append(",");
                writer.append(String.valueOf(enrollment.getStudentId())).append(",");
                writer.append(escapeCSV(enrollment.getStudentName())).append(",");
                writer.append(String.valueOf(enrollment.getCourseId())).append(",");
                writer.append(escapeCSV(enrollment.getCourseName())).append(",");
                writer.append(escapeCSV(enrollment.getDateEnrolled())).append("\n");
            }
            
            writer.flush();
            writer.close();
            
            Log.d("CSVExporter", "All data exported to: " + file.getAbsolutePath());
            return file.getAbsolutePath();
        } catch (IOException e) {
            Log.e("CSVExporter", "Error exporting all data", e);
            e.printStackTrace();
            return null;
        }
    }

    // Helper method to escape CSV special characters
    private String escapeCSV(String value) {
        if (value == null) {
            return "";
        }
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}

