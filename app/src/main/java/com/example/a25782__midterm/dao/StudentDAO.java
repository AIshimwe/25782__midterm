package com.example.a25782__midterm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.a25782__midterm.models.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private DBHelper dbHelper;

    public StudentDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Create - Add a new student
    public long addStudent(Student student) {
        SQLiteDatabase db = null;
        long result = -1;
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_STUDENT_NAME, student.getName());
            values.put(DBHelper.COLUMN_STUDENT_EMAIL, student.getEmail());
            values.put(DBHelper.COLUMN_STUDENT_PHONE, student.getPhone());
            
            result = db.insert(DBHelper.TABLE_STUDENTS, null, values);
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return result;
    }

    // Read - Get all students
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.query(DBHelper.TABLE_STUDENTS, null, null, null, null, null, 
                            DBHelper.COLUMN_STUDENT_NAME + " ASC");
            
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_STUDENT_NAME));
                    String email = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_STUDENT_EMAIL));
                    String phone = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_STUDENT_PHONE));
                    
                    students.add(new Student(id, name, email, phone));
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return students;
    }

    // Read - Get student by ID
    public Student getStudentById(int id) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        Student student = null;
        
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.query(DBHelper.TABLE_STUDENTS, null, 
                            DBHelper.COLUMN_ID + "=?", 
                            new String[]{String.valueOf(id)}, 
                            null, null, null);
            
            if (cursor != null && cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_STUDENT_NAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_STUDENT_EMAIL));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_STUDENT_PHONE));
                
                student = new Student(id, name, email, phone);
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return student;
    }

    // Update - Update student information
    public int updateStudent(Student student) {
        SQLiteDatabase db = null;
        int result = 0;
        
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_STUDENT_NAME, student.getName());
            values.put(DBHelper.COLUMN_STUDENT_EMAIL, student.getEmail());
            values.put(DBHelper.COLUMN_STUDENT_PHONE, student.getPhone());
            
            result = db.update(DBHelper.TABLE_STUDENTS, values, 
                             DBHelper.COLUMN_ID + "=?", 
                             new String[]{String.valueOf(student.getId())});
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return result;
    }

    // Delete - Delete a student
    public int deleteStudent(int id) {
        SQLiteDatabase db = null;
        int result = 0;
        
        try {
            db = dbHelper.getWritableDatabase();
            result = db.delete(DBHelper.TABLE_STUDENTS, 
                             DBHelper.COLUMN_ID + "=?", 
                             new String[]{String.valueOf(id)});
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return result;
    }

    // Get student count
    public int getStudentCount() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        int count = 0;
        
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery("SELECT COUNT(*) FROM " + DBHelper.TABLE_STUDENTS, null);
            if (cursor != null && cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return count;
    }
}


