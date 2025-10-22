package com.example.a25782__midterm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.a25782__midterm.models.Enrollment;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {
    private DBHelper dbHelper;

    public EnrollmentDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Create - Add a new enrollment
    public long addEnrollment(Enrollment enrollment) {
        SQLiteDatabase db = null;
        long result = -1;
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_ENROLLMENT_STUDENT_ID, enrollment.getStudentId());
            values.put(DBHelper.COLUMN_ENROLLMENT_COURSE_ID, enrollment.getCourseId());
            values.put(DBHelper.COLUMN_ENROLLMENT_DATE, enrollment.getDateEnrolled());
            
            result = db.insert(DBHelper.TABLE_ENROLLMENTS, null, values);
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return result;
    }

    // Read - Get all enrollments
    public List<Enrollment> getAllEnrollments() {
        List<Enrollment> enrollments = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.query(DBHelper.TABLE_ENROLLMENTS, null, null, null, null, null, 
                            DBHelper.COLUMN_ENROLLMENT_DATE + " DESC");
            
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ID));
                    int studentId = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ENROLLMENT_STUDENT_ID));
                    int courseId = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ENROLLMENT_COURSE_ID));
                    String date = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ENROLLMENT_DATE));
                    
                    enrollments.add(new Enrollment(id, studentId, courseId, date));
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
        return enrollments;
    }

    // Read - Get all enrollments with student and course details
    public List<Enrollment> getAllEnrollmentsWithDetails() {
        List<Enrollment> enrollments = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        
        try {
            db = dbHelper.getReadableDatabase();
            String query = "SELECT e." + DBHelper.COLUMN_ID + ", " +
                          "e." + DBHelper.COLUMN_ENROLLMENT_STUDENT_ID + ", " +
                          "e." + DBHelper.COLUMN_ENROLLMENT_COURSE_ID + ", " +
                          "e." + DBHelper.COLUMN_ENROLLMENT_DATE + ", " +
                          "s." + DBHelper.COLUMN_STUDENT_NAME + ", " +
                          "c." + DBHelper.COLUMN_COURSE_NAME + " " +
                          "FROM " + DBHelper.TABLE_ENROLLMENTS + " e " +
                          "INNER JOIN " + DBHelper.TABLE_STUDENTS + " s ON e." + 
                          DBHelper.COLUMN_ENROLLMENT_STUDENT_ID + " = s." + DBHelper.COLUMN_ID + " " +
                          "INNER JOIN " + DBHelper.TABLE_COURSES + " c ON e." + 
                          DBHelper.COLUMN_ENROLLMENT_COURSE_ID + " = c." + DBHelper.COLUMN_ID + " " +
                          "ORDER BY e." + DBHelper.COLUMN_ENROLLMENT_DATE + " DESC";
            
            cursor = db.rawQuery(query, null);
            
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);
                    int studentId = cursor.getInt(1);
                    int courseId = cursor.getInt(2);
                    String date = cursor.getString(3);
                    String studentName = cursor.getString(4);
                    String courseName = cursor.getString(5);
                    
                    enrollments.add(new Enrollment(id, studentId, courseId, date, studentName, courseName));
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
        return enrollments;
    }

    // Delete - Delete an enrollment
    public int deleteEnrollment(int id) {
        SQLiteDatabase db = null;
        int result = 0;
        
        try {
            db = dbHelper.getWritableDatabase();
            result = db.delete(DBHelper.TABLE_ENROLLMENTS, 
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

    // Check if student is already enrolled in a course
    public boolean isStudentEnrolled(int studentId, int courseId) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        boolean isEnrolled = false;
        
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.query(DBHelper.TABLE_ENROLLMENTS, null, 
                            DBHelper.COLUMN_ENROLLMENT_STUDENT_ID + "=? AND " + 
                            DBHelper.COLUMN_ENROLLMENT_COURSE_ID + "=?", 
                            new String[]{String.valueOf(studentId), String.valueOf(courseId)}, 
                            null, null, null);
            
            isEnrolled = cursor != null && cursor.getCount() > 0;
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
        return isEnrolled;
    }

    // Get enrollment count
    public int getEnrollmentCount() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        int count = 0;
        
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery("SELECT COUNT(*) FROM " + DBHelper.TABLE_ENROLLMENTS, null);
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


