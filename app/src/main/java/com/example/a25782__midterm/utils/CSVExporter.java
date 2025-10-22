package com.example.a25782__midterm.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.example.a25782__midterm.models.Course;
import com.example.a25782__midterm.models.Enrollment;
import com.example.a25782__midterm.models.Student;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CSVExporter {

    private Context context;

    public CSVExporter(Context context) {
        this.context = context;
    }

    // Helper method to save file to public Downloads folder using MediaStore
    private String saveToDownloads(String fileName, String content) {
        try {
            ContentResolver resolver = context.getContentResolver();
            ContentValues contentValues = new ContentValues();
            
            // Ensure filename has .csv extension
            if (!fileName.endsWith(".csv")) {
                fileName = fileName + ".csv";
            }
            
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "text/csv");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);
            contentValues.put(MediaStore.MediaColumns.IS_PENDING, 1);
            
            Uri uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);
            if (uri != null) {
                OutputStream outputStream = resolver.openOutputStream(uri);
                if (outputStream != null) {
                    outputStream.write(content.getBytes("UTF-8"));
                    outputStream.close();
                    
                    // Mark as no longer pending
                    contentValues.clear();
                    contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0);
                    resolver.update(uri, contentValues, null, null);
                    
                    Log.d("CSVExporter", "File saved to Downloads: " + fileName);
                    return "Downloads/" + fileName;
                }
            }
        } catch (IOException e) {
            Log.e("CSVExporter", "Error saving to Downloads", e);
        }
        return null;
    }

    // Export students to CSV
    public String exportStudents(List<Student> students) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "students_" + timestamp + ".csv";
        
        try {
            StringBuilder csvContent = new StringBuilder();
            
            // Write header
            csvContent.append("ID,Name,Email,Phone\n");
            
            // Write data
            for (Student student : students) {
                csvContent.append(String.valueOf(student.getId())).append(",");
                csvContent.append(escapeCSV(student.getName())).append(",");
                csvContent.append(escapeCSV(student.getEmail())).append(",");
                csvContent.append(escapeCSV(student.getPhone())).append("\n");
            }
            
            String result = saveToDownloads(fileName, csvContent.toString());
            if (result != null) {
                Log.d("CSVExporter", "Students exported to: " + result);
                return result;
            }
            return null;
        } catch (Exception e) {
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
            StringBuilder csvContent = new StringBuilder();
            
            // Write header
            csvContent.append("ID,Course Name,Course Code,Credits\n");
            
            // Write data
            for (Course course : courses) {
                csvContent.append(String.valueOf(course.getId())).append(",");
                csvContent.append(escapeCSV(course.getCourseName())).append(",");
                csvContent.append(escapeCSV(course.getCourseCode())).append(",");
                csvContent.append(String.valueOf(course.getCredits())).append("\n");
            }
            
            String result = saveToDownloads(fileName, csvContent.toString());
            if (result != null) {
                Log.d("CSVExporter", "Courses exported to: " + result);
                return result;
            }
            return null;
        } catch (Exception e) {
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
            StringBuilder csvContent = new StringBuilder();
            
            // Write header
            csvContent.append("ID,Student ID,Student Name,Course ID,Course Name,Date Enrolled\n");
            
            // Write data
            for (Enrollment enrollment : enrollments) {
                csvContent.append(String.valueOf(enrollment.getId())).append(",");
                csvContent.append(String.valueOf(enrollment.getStudentId())).append(",");
                csvContent.append(escapeCSV(enrollment.getStudentName())).append(",");
                csvContent.append(String.valueOf(enrollment.getCourseId())).append(",");
                csvContent.append(escapeCSV(enrollment.getCourseName())).append(",");
                csvContent.append(escapeCSV(enrollment.getDateEnrolled())).append("\n");
            }
            
            String result = saveToDownloads(fileName, csvContent.toString());
            if (result != null) {
                Log.d("CSVExporter", "Enrollments exported to: " + result);
                return result;
            }
            return null;
        } catch (Exception e) {
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
            StringBuilder csvContent = new StringBuilder();
            
            // Students section
            csvContent.append("=== STUDENTS ===\n");
            csvContent.append("ID,Name,Email,Phone\n");
            for (Student student : students) {
                csvContent.append(String.valueOf(student.getId())).append(",");
                csvContent.append(escapeCSV(student.getName())).append(",");
                csvContent.append(escapeCSV(student.getEmail())).append(",");
                csvContent.append(escapeCSV(student.getPhone())).append("\n");
            }
            csvContent.append("\n");
            
            // Courses section
            csvContent.append("=== COURSES ===\n");
            csvContent.append("ID,Course Name,Course Code,Credits\n");
            for (Course course : courses) {
                csvContent.append(String.valueOf(course.getId())).append(",");
                csvContent.append(escapeCSV(course.getCourseName())).append(",");
                csvContent.append(escapeCSV(course.getCourseCode())).append(",");
                csvContent.append(String.valueOf(course.getCredits())).append("\n");
            }
            csvContent.append("\n");
            
            // Enrollments section
            csvContent.append("=== ENROLLMENTS ===\n");
            csvContent.append("ID,Student ID,Student Name,Course ID,Course Name,Date Enrolled\n");
            for (Enrollment enrollment : enrollments) {
                csvContent.append(String.valueOf(enrollment.getId())).append(",");
                csvContent.append(String.valueOf(enrollment.getStudentId())).append(",");
                csvContent.append(escapeCSV(enrollment.getStudentName())).append(",");
                csvContent.append(String.valueOf(enrollment.getCourseId())).append(",");
                csvContent.append(escapeCSV(enrollment.getCourseName())).append(",");
                csvContent.append(escapeCSV(enrollment.getDateEnrolled())).append("\n");
            }
            
            String result = saveToDownloads(fileName, csvContent.toString());
            if (result != null) {
                Log.d("CSVExporter", "All data exported to: " + result);
                return result;
            }
            return null;
        } catch (Exception e) {
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

