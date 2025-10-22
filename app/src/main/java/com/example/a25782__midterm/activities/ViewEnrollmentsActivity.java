package com.example.a25782__midterm.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a25782__midterm.R;
import com.example.a25782__midterm.dao.CourseDAO;
import com.example.a25782__midterm.dao.EnrollmentDAO;
import com.example.a25782__midterm.dao.StudentDAO;
import com.example.a25782__midterm.models.Course;
import com.example.a25782__midterm.models.Enrollment;
import com.example.a25782__midterm.models.Student;
import com.example.a25782__midterm.utils.CSVExporter;
import com.example.a25782__midterm.utils.PreferencesHelper;

import java.util.ArrayList;
import java.util.List;

public class ViewEnrollmentsActivity extends AppCompatActivity {

    private ListView lvAllEnrollments;
    private Button btnExportCSV, btnBack;
    
    private EnrollmentDAO enrollmentDAO;
    private StudentDAO studentDAO;
    private CourseDAO courseDAO;
    private CSVExporter csvExporter;
    private PreferencesHelper preferencesHelper;
    
    private List<Enrollment> enrollmentList;
    private ArrayAdapter<Enrollment> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_enrollments);

        // Initialize components
        initializeViews();
        
        enrollmentDAO = new EnrollmentDAO(this);
        studentDAO = new StudentDAO(this);
        courseDAO = new CourseDAO(this);
        csvExporter = new CSVExporter(this);
        preferencesHelper = new PreferencesHelper(this);
        preferencesHelper.saveLastScreen("ViewEnrollmentsActivity");
        
        enrollmentList = new ArrayList<>();
        
        // Setup ListView
        setupListView();
        
        // Load enrollments
        loadEnrollments();
        
        // Set click listeners
        setupClickListeners();
    }

    private void initializeViews() {
        lvAllEnrollments = findViewById(R.id.lvAllEnrollments);
        btnExportCSV = findViewById(R.id.btnExportCSV);
        btnBack = findViewById(R.id.btnBackView);
    }

    private void setupListView() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, enrollmentList);
        lvAllEnrollments.setAdapter(adapter);
    }

    private void setupClickListeners() {
        btnExportCSV.setOnClickListener(v -> exportAllDataToCSV());
        btnBack.setOnClickListener(v -> finish());
    }

    private void loadEnrollments() {
        enrollmentList.clear();
        enrollmentList.addAll(enrollmentDAO.getAllEnrollmentsWithDetails());
        adapter.notifyDataSetChanged();
    }

    private void exportAllDataToCSV() {
        // Get all data
        List<Student> students = studentDAO.getAllStudents();
        List<Course> courses = courseDAO.getAllCourses();
        List<Enrollment> enrollments = enrollmentDAO.getAllEnrollmentsWithDetails();

        if (students.isEmpty() && courses.isEmpty() && enrollments.isEmpty()) {
            Toast.makeText(this, "No data to export", Toast.LENGTH_SHORT).show();
            return;
        }

        // Export to CSV
        String filePath = csvExporter.exportAllData(students, courses, enrollments);

        if (filePath != null) {
            // Extract just the filename and directory for a cleaner message
            String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
            
            String message = "✅ CSV exported successfully!\n" +
                           "📁 File: " + fileName + "\n" +
                           "📍 Location: Downloads folder\n" +
                           "💡 Tip: Open with Excel, Google Sheets, or any text editor";
            
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            preferencesHelper.saveString("last_export_path", filePath);
        } else {
            Toast.makeText(this, "❌ Failed to export data", Toast.LENGTH_SHORT).show();
        }
    }
}


