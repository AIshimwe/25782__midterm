package com.example.a25782__midterm.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    
    private static final String DATABASE_NAME = "AcademicManagement.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_STUDENTS = "students";
    public static final String TABLE_COURSES = "courses";
    public static final String TABLE_ENROLLMENTS = "enrollments";

    // Common Column
    public static final String COLUMN_ID = "id";

    // Students Table Columns
    public static final String COLUMN_STUDENT_NAME = "name";
    public static final String COLUMN_STUDENT_EMAIL = "email";
    public static final String COLUMN_STUDENT_PHONE = "phone";

    // Courses Table Columns
    public static final String COLUMN_COURSE_NAME = "courseName";
    public static final String COLUMN_COURSE_CODE = "courseCode";
    public static final String COLUMN_COURSE_CREDITS = "credits";

    // Enrollments Table Columns
    public static final String COLUMN_ENROLLMENT_STUDENT_ID = "student_id";
    public static final String COLUMN_ENROLLMENT_COURSE_ID = "course_id";
    public static final String COLUMN_ENROLLMENT_DATE = "date_enrolled";

    // Create Students Table
    private static final String CREATE_TABLE_STUDENTS = 
        "CREATE TABLE " + TABLE_STUDENTS + " (" +
        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COLUMN_STUDENT_NAME + " TEXT NOT NULL, " +
        COLUMN_STUDENT_EMAIL + " TEXT NOT NULL, " +
        COLUMN_STUDENT_PHONE + " TEXT NOT NULL)";

    // Create Courses Table
    private static final String CREATE_TABLE_COURSES = 
        "CREATE TABLE " + TABLE_COURSES + " (" +
        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COLUMN_COURSE_NAME + " TEXT NOT NULL, " +
        COLUMN_COURSE_CODE + " TEXT NOT NULL UNIQUE, " +
        COLUMN_COURSE_CREDITS + " INTEGER NOT NULL)";

    // Create Enrollments Table with Foreign Keys
    private static final String CREATE_TABLE_ENROLLMENTS = 
        "CREATE TABLE " + TABLE_ENROLLMENTS + " (" +
        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COLUMN_ENROLLMENT_STUDENT_ID + " INTEGER NOT NULL, " +
        COLUMN_ENROLLMENT_COURSE_ID + " INTEGER NOT NULL, " +
        COLUMN_ENROLLMENT_DATE + " TEXT NOT NULL, " +
        "FOREIGN KEY(" + COLUMN_ENROLLMENT_STUDENT_ID + ") REFERENCES " + 
        TABLE_STUDENTS + "(" + COLUMN_ID + ") ON DELETE CASCADE, " +
        "FOREIGN KEY(" + COLUMN_ENROLLMENT_COURSE_ID + ") REFERENCES " + 
        TABLE_COURSES + "(" + COLUMN_ID + ") ON DELETE CASCADE)";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
            
            // Create tables
            db.execSQL(CREATE_TABLE_STUDENTS);
            db.execSQL(CREATE_TABLE_COURSES);
            db.execSQL(CREATE_TABLE_ENROLLMENTS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENROLLMENTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
            onCreate(db);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        // Enable foreign key constraints every time the database is opened
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
}


