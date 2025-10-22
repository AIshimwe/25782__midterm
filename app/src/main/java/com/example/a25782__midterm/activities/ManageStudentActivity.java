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
import com.example.a25782__midterm.dao.StudentDAO;
import com.example.a25782__midterm.models.Student;
import com.example.a25782__midterm.utils.PreferencesHelper;

import java.util.ArrayList;
import java.util.List;

public class ManageStudentActivity extends AppCompatActivity {

    private EditText etStudentName, etStudentEmail, etStudentPhone;
    private Button btnSave, btnUpdate, btnDelete, btnClear, btnBack;
    private ListView lvStudents;
    
    private StudentDAO studentDAO;
    private PreferencesHelper preferencesHelper;
    private List<Student> studentList;
    private ArrayAdapter<Student> adapter;
    private Student selectedStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_student);

        // Initialize components
        initializeViews();
        
        studentDAO = new StudentDAO(this);
        preferencesHelper = new PreferencesHelper(this);
        preferencesHelper.saveLastScreen("ManageStudentActivity");
        
        studentList = new ArrayList<>();
        
        // Setup ListView
        setupListView();
        
        // Load students
        loadStudents();
        
        // Set click listeners
        setupClickListeners();
    }

    private void initializeViews() {
        etStudentName = findViewById(R.id.etStudentName);
        etStudentEmail = findViewById(R.id.etStudentEmail);
        etStudentPhone = findViewById(R.id.etStudentPhone);
        btnSave = findViewById(R.id.btnSave);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnClear = findViewById(R.id.btnClear);
        btnBack = findViewById(R.id.btnBack);
        lvStudents = findViewById(R.id.lvStudents);
    }

    private void setupListView() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        lvStudents.setAdapter(adapter);
        
        lvStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedStudent = studentList.get(position);
                displayStudentData(selectedStudent);
            }
        });
    }

    private void setupClickListeners() {
        btnSave.setOnClickListener(v -> saveStudent());
        btnUpdate.setOnClickListener(v -> updateStudent());
        btnDelete.setOnClickListener(v -> deleteStudent());
        btnClear.setOnClickListener(v -> clearFields());
        btnBack.setOnClickListener(v -> finish());
    }

    private void loadStudents() {
        studentList.clear();
        studentList.addAll(studentDAO.getAllStudents());
        adapter.notifyDataSetChanged();
    }

    private void saveStudent() {
        String studentName = etStudentName.getText().toString().trim();
        String studentEmail = etStudentEmail.getText().toString().trim();
        String studentPhone = etStudentPhone.getText().toString().trim();

        if (studentName.isEmpty() || studentEmail.isEmpty() || studentPhone.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Basic email validation
        if (!studentEmail.contains("@")) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        Student student = new Student(studentName, studentEmail, studentPhone);
        long result = studentDAO.addStudent(student);

        if (result != -1) {
            Toast.makeText(this, "Student added successfully", Toast.LENGTH_SHORT).show();
            clearFields();
            loadStudents();
            preferencesHelper.saveLastUpdate(System.currentTimeMillis());
        } else {
            Toast.makeText(this, "Failed to add student", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateStudent() {
        if (selectedStudent == null) {
            Toast.makeText(this, "Please select a student to update", Toast.LENGTH_SHORT).show();
            return;
        }

        String studentName = etStudentName.getText().toString().trim();
        String studentEmail = etStudentEmail.getText().toString().trim();
        String studentPhone = etStudentPhone.getText().toString().trim();

        if (studentName.isEmpty() || studentEmail.isEmpty() || studentPhone.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Basic email validation
        if (!studentEmail.contains("@")) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        selectedStudent.setName(studentName);
        selectedStudent.setEmail(studentEmail);
        selectedStudent.setPhone(studentPhone);

        int result = studentDAO.updateStudent(selectedStudent);

        if (result > 0) {
            Toast.makeText(this, "Student updated successfully", Toast.LENGTH_SHORT).show();
            clearFields();
            loadStudents();
            selectedStudent = null;
            preferencesHelper.saveLastUpdate(System.currentTimeMillis());
        } else {
            Toast.makeText(this, "Failed to update student", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteStudent() {
        if (selectedStudent == null) {
            Toast.makeText(this, "Please select a student to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        int result = studentDAO.deleteStudent(selectedStudent.getId());

        if (result > 0) {
            Toast.makeText(this, "Student deleted successfully", Toast.LENGTH_SHORT).show();
            clearFields();
            loadStudents();
            selectedStudent = null;
            preferencesHelper.saveLastUpdate(System.currentTimeMillis());
        } else {
            Toast.makeText(this, "Failed to delete student", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayStudentData(Student student) {
        etStudentName.setText(student.getName());
        etStudentEmail.setText(student.getEmail());
        etStudentPhone.setText(student.getPhone());
    }

    private void clearFields() {
        etStudentName.setText("");
        etStudentEmail.setText("");
        etStudentPhone.setText("");
        selectedStudent = null;
    }
}

