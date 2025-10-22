package com.example.a25782__midterm.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a25782__midterm.R;
import com.example.a25782__midterm.dao.CourseDAO;
import com.example.a25782__midterm.dao.EnrollmentDAO;
import com.example.a25782__midterm.dao.StudentDAO;
import com.example.a25782__midterm.models.Course;
import com.example.a25782__midterm.models.Enrollment;
import com.example.a25782__midterm.models.Student;
import com.example.a25782__midterm.utils.PreferencesHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EnrollStudentActivity extends AppCompatActivity {

    private Spinner spinnerStudents, spinnerCourses;
    private Button btnEnroll, btnBack;
    private ListView lvEnrollments;
    
    private StudentDAO studentDAO;
    private CourseDAO courseDAO;
    private EnrollmentDAO enrollmentDAO;
    private PreferencesHelper preferencesHelper;
    
    private List<Student> studentList;
    private List<Course> courseList;
    private List<Enrollment> enrollmentList;
    private ArrayAdapter<Student> studentAdapter;
    private ArrayAdapter<Course> courseAdapter;
    private ArrayAdapter<Enrollment> enrollmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_student);

        // Initialize components
        initializeViews();
        
        studentDAO = new StudentDAO(this);
        courseDAO = new CourseDAO(this);
        enrollmentDAO = new EnrollmentDAO(this);
        preferencesHelper = new PreferencesHelper(this);
        preferencesHelper.saveLastScreen("EnrollStudentActivity");
        
        studentList = new ArrayList<>();
        courseList = new ArrayList<>();
        enrollmentList = new ArrayList<>();
        
        // Setup spinners and list
        setupSpinners();
        setupListView();
        
        // Load data
        loadData();
        
        // Set click listeners
        setupClickListeners();
    }

    private void initializeViews() {
        spinnerStudents = findViewById(R.id.spinnerStudents);
        spinnerCourses = findViewById(R.id.spinnerCourses);
        btnEnroll = findViewById(R.id.btnEnroll);
        btnBack = findViewById(R.id.btnBackEnroll);
        lvEnrollments = findViewById(R.id.lvEnrollments);
    }

    private void setupSpinners() {
        studentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, studentList);
        studentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStudents.setAdapter(studentAdapter);
        
        courseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseList);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourses.setAdapter(courseAdapter);
    }

    private void setupListView() {
        enrollmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, enrollmentList);
        lvEnrollments.setAdapter(enrollmentAdapter);
    }

    private void setupClickListeners() {
        btnEnroll.setOnClickListener(v -> enrollStudent());
        btnBack.setOnClickListener(v -> finish());
    }

    private void loadData() {
        // Load students
        studentList.clear();
        studentList.addAll(studentDAO.getAllStudents());
        studentAdapter.notifyDataSetChanged();
        
        // Load courses
        courseList.clear();
        courseList.addAll(courseDAO.getAllCourses());
        courseAdapter.notifyDataSetChanged();
        
        // Load enrollments
        loadEnrollments();
    }

    private void loadEnrollments() {
        enrollmentList.clear();
        enrollmentList.addAll(enrollmentDAO.getAllEnrollmentsWithDetails());
        enrollmentAdapter.notifyDataSetChanged();
    }

    private void enrollStudent() {
        if (studentList.isEmpty()) {
            Toast.makeText(this, "No students available. Please add students first.", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (courseList.isEmpty()) {
            Toast.makeText(this, "No courses available. Please add courses first.", Toast.LENGTH_SHORT).show();
            return;
        }

        Student selectedStudent = (Student) spinnerStudents.getSelectedItem();
        Course selectedCourse = (Course) spinnerCourses.getSelectedItem();

        if (selectedStudent == null || selectedCourse == null) {
            Toast.makeText(this, "Please select both student and course", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if student is already enrolled in this course
        if (enrollmentDAO.isStudentEnrolled(selectedStudent.getId(), selectedCourse.getId())) {
            Toast.makeText(this, "Student is already enrolled in this course", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get current date
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // Create enrollment
        Enrollment enrollment = new Enrollment(selectedStudent.getId(), selectedCourse.getId(), currentDate);
        long result = enrollmentDAO.addEnrollment(enrollment);

        if (result != -1) {
            Toast.makeText(this, "Student enrolled successfully", Toast.LENGTH_SHORT).show();
            loadEnrollments();
            preferencesHelper.saveLastUpdate(System.currentTimeMillis());
        } else {
            Toast.makeText(this, "Failed to enroll student", Toast.LENGTH_SHORT).show();
        }
    }
}




