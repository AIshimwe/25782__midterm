package com.example.a25782__midterm.models;

public class Enrollment {
    private int id;
    private int studentId;
    private int courseId;
    private String dateEnrolled;
    private String studentName;  // For display purposes
    private String courseName;   // For display purposes

    // Constructor without ID (for new enrollments)
    public Enrollment(int studentId, int courseId, String dateEnrolled) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.dateEnrolled = dateEnrolled;
    }

    // Constructor with ID (for existing enrollments)
    public Enrollment(int id, int studentId, int courseId, String dateEnrolled) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.dateEnrolled = dateEnrolled;
    }

    // Constructor with names (for display)
    public Enrollment(int id, int studentId, int courseId, String dateEnrolled, 
                     String studentName, String courseName) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.dateEnrolled = dateEnrolled;
        this.studentName = studentName;
        this.courseName = courseName;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getDateEnrolled() {
        return dateEnrolled;
    }

    public void setDateEnrolled(String dateEnrolled) {
        this.dateEnrolled = dateEnrolled;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public String toString() {
        if (studentName != null && courseName != null) {
            return studentName + " → " + courseName + " (" + dateEnrolled + ")";
        }
        return "Student #" + studentId + " → Course #" + courseId + " (" + dateEnrolled + ")";
    }
}


