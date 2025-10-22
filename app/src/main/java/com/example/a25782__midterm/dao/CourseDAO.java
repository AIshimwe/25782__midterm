package com.example.a25782__midterm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.a25782__midterm.models.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    private DBHelper dbHelper;

    public CourseDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Create - Add a new course
    public long addCourse(Course course) {
        SQLiteDatabase db = null;
        long result = -1;
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_COURSE_NAME, course.getCourseName());
            values.put(DBHelper.COLUMN_COURSE_CODE, course.getCourseCode());
            values.put(DBHelper.COLUMN_COURSE_CREDITS, course.getCredits());
            
            result = db.insert(DBHelper.TABLE_COURSES, null, values);
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return result;
    }

    // Read - Get all courses
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.query(DBHelper.TABLE_COURSES, null, null, null, null, null, 
                            DBHelper.COLUMN_COURSE_CODE + " ASC");
            
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ID));
                    String courseName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_COURSE_NAME));
                    String courseCode = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_COURSE_CODE));
                    int credits = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_COURSE_CREDITS));
                    
                    courses.add(new Course(id, courseName, courseCode, credits));
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
        return courses;
    }

    // Read - Get course by ID
    public Course getCourseById(int id) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        Course course = null;
        
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.query(DBHelper.TABLE_COURSES, null, 
                            DBHelper.COLUMN_ID + "=?", 
                            new String[]{String.valueOf(id)}, 
                            null, null, null);
            
            if (cursor != null && cursor.moveToFirst()) {
                String courseName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_COURSE_NAME));
                String courseCode = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_COURSE_CODE));
                int credits = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_COURSE_CREDITS));
                
                course = new Course(id, courseName, courseCode, credits);
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
        return course;
    }

    // Update - Update course information
    public int updateCourse(Course course) {
        SQLiteDatabase db = null;
        int result = 0;
        
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_COURSE_NAME, course.getCourseName());
            values.put(DBHelper.COLUMN_COURSE_CODE, course.getCourseCode());
            values.put(DBHelper.COLUMN_COURSE_CREDITS, course.getCredits());
            
            result = db.update(DBHelper.TABLE_COURSES, values, 
                             DBHelper.COLUMN_ID + "=?", 
                             new String[]{String.valueOf(course.getId())});
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return result;
    }

    // Delete - Delete a course
    public int deleteCourse(int id) {
        SQLiteDatabase db = null;
        int result = 0;
        
        try {
            db = dbHelper.getWritableDatabase();
            result = db.delete(DBHelper.TABLE_COURSES, 
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

    // Get course count
    public int getCourseCount() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        int count = 0;
        
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery("SELECT COUNT(*) FROM " + DBHelper.TABLE_COURSES, null);
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


