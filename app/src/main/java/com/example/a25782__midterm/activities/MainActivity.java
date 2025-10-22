package com.example.a25782__midterm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a25782__midterm.R;
import com.example.a25782__midterm.utils.PreferencesHelper;

public class MainActivity extends AppCompatActivity {

    private Button btnManageStudent, btnManageCourse, btnEnrollStudent, btnViewEnrollments;
    private PreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize PreferencesHelper
        preferencesHelper = new PreferencesHelper(this);
        preferencesHelper.saveLastScreen("MainActivity");

        // Initialize buttons
        btnManageStudent = findViewById(R.id.btnManageStudent);
        btnManageCourse = findViewById(R.id.btnManageCourse);
        btnEnrollStudent = findViewById(R.id.btnEnrollStudent);
        btnViewEnrollments = findViewById(R.id.btnViewEnrollments);

        // Set click listeners
        btnManageStudent.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ManageStudentActivity.class);
            startActivity(intent);
        });

        btnManageCourse.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ManageCourseActivity.class);
            startActivity(intent);
        });

        btnEnrollStudent.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EnrollStudentActivity.class);
            startActivity(intent);
        });

        btnViewEnrollments.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewEnrollmentsActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        preferencesHelper.saveLastScreen("MainActivity");
    }
}


