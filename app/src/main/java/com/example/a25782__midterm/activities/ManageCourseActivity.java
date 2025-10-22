package com.example.a25782__midterm.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a25782__midterm.R;
import com.example.a25782__midterm.dao.CourseDAO;
import com.example.a25782__midterm.models.Course;
import com.example.a25782__midterm.utils.PreferencesHelper;

import java.util.ArrayList;
import java.util.List;

public class ManageCourseActivity extends AppCompatActivity {

    private EditText etCourseName, etCourseCode, etCredits;
    private Button btnSave, btnUpdate, btnDelete, btnClear, btnBack;
    private ListView lvCourses;
    
    private CourseDAO courseDAO;
    private PreferencesHelper preferencesHelper;
    private List<Course> courseList;
    private ArrayAdapter<Course> adapter;
    private Course selectedCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_course);

        // Initialize components
        initializeViews();
        
        courseDAO = new CourseDAO(this);
        preferencesHelper = new PreferencesHelper(this);
        preferencesHelper.saveLastScreen("ManageCourseActivity");
        
        courseList = new ArrayList<>();
        
        // Setup ListView
        setupListView();
        
        // Load courses
        loadCourses();
        
        // Set click listeners
        setupClickListeners();
    }

    private void initializeViews() {
        etCourseName = findViewById(R.id.etCourseName);
        etCourseCode = findViewById(R.id.etCourseCode);
        etCredits = findViewById(R.id.etCredits);
        btnSave = findViewById(R.id.btnSaveCourse);
        btnUpdate = findViewById(R.id.btnUpdateCourse);
        btnDelete = findViewById(R.id.btnDeleteCourse);
        btnClear = findViewById(R.id.btnClearCourse);
        btnBack = findViewById(R.id.btnBackCourse);
        lvCourses = findViewById(R.id.lvCourses);
    }

    private void setupListView() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseList);
        lvCourses.setAdapter(adapter);
        
        lvCourses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCourse = courseList.get(position);
                displayCourseData(selectedCourse);
            }
        });
    }

    private void setupClickListeners() {
        btnSave.setOnClickListener(v -> saveCourse());
        btnUpdate.setOnClickListener(v -> updateCourse());
        btnDelete.setOnClickListener(v -> deleteCourse());
        btnClear.setOnClickListener(v -> clearFields());
        btnBack.setOnClickListener(v -> finish());
    }

    private void loadCourses() {
        courseList.clear();
        courseList.addAll(courseDAO.getAllCourses());
        adapter.notifyDataSetChanged();
    }

    private void saveCourse() {
        String courseName = etCourseName.getText().toString().trim();
        String courseCode = etCourseCode.getText().toString().trim();
        String creditsStr = etCredits.getText().toString().trim();

        if (courseName.isEmpty() || courseCode.isEmpty() || creditsStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int credits;
        try {
            credits = Integer.parseInt(creditsStr);
            if (credits <= 0) {
                Toast.makeText(this, "Credits must be greater than 0", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid credits value", Toast.LENGTH_SHORT).show();
            return;
        }

        Course course = new Course(courseName, courseCode, credits);
        long result = courseDAO.addCourse(course);

        if (result != -1) {
            Toast.makeText(this, "Course added successfully", Toast.LENGTH_SHORT).show();
            clearFields();
            loadCourses();
            preferencesHelper.saveLastUpdate(System.currentTimeMillis());
        } else {
            Toast.makeText(this, "Failed to add course (code may already exist)", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateCourse() {
        if (selectedCourse == null) {
            Toast.makeText(this, "Please select a course to update", Toast.LENGTH_SHORT).show();
            return;
        }

        String courseName = etCourseName.getText().toString().trim();
        String courseCode = etCourseCode.getText().toString().trim();
        String creditsStr = etCredits.getText().toString().trim();

        if (courseName.isEmpty() || courseCode.isEmpty() || creditsStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int credits;
        try {
            credits = Integer.parseInt(creditsStr);
            if (credits <= 0) {
                Toast.makeText(this, "Credits must be greater than 0", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid credits value", Toast.LENGTH_SHORT).show();
            return;
        }

        selectedCourse.setCourseName(courseName);
        selectedCourse.setCourseCode(courseCode);
        selectedCourse.setCredits(credits);

        int result = courseDAO.updateCourse(selectedCourse);

        if (result > 0) {
            Toast.makeText(this, "Course updated successfully", Toast.LENGTH_SHORT).show();
            clearFields();
            loadCourses();
            selectedCourse = null;
            preferencesHelper.saveLastUpdate(System.currentTimeMillis());
        } else {
            Toast.makeText(this, "Failed to update course", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteCourse() {
        if (selectedCourse == null) {
            Toast.makeText(this, "Please select a course to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        int result = courseDAO.deleteCourse(selectedCourse.getId());

        if (result > 0) {
            Toast.makeText(this, "Course deleted successfully", Toast.LENGTH_SHORT).show();
            clearFields();
            loadCourses();
            selectedCourse = null;
            preferencesHelper.saveLastUpdate(System.currentTimeMillis());
        } else {
            Toast.makeText(this, "Failed to delete course", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayCourseData(Course course) {
        etCourseName.setText(course.getCourseName());
        etCourseCode.setText(course.getCourseCode());
        etCredits.setText(String.valueOf(course.getCredits()));
    }

    private void clearFields() {
        etCourseName.setText("");
        etCourseCode.setText("");
        etCredits.setText("");
        selectedCourse = null;
    }
}



