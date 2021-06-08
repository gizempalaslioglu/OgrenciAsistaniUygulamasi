package com.project.ogrenciasistanidenemeprojesi.yoklama.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class Course implements Serializable {

    private String courseName;
    private String courseId;
    private String courseTeacher;
    private String teacherId;
    private int week;
    private int registeredStudents;
    private boolean active;

    private HashMap<String, List<String>> attendance;

    public Course() {
    }


    public Course(String courseName, String courseId, String courseTeacher, String teacherId, int week, int registeredStudents, boolean active, HashMap<String, List<String>> attendance) {
        this.courseName = courseName;
        this.courseId = courseId;
        this.courseTeacher = courseTeacher;
        this.teacherId = teacherId;
        this.week = week;
        this.registeredStudents = registeredStudents;
        this.active = active;
        this.attendance = attendance;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseTeacher() {
        return courseTeacher;
    }

    public void setCourseTeacher(String courseTeacher) {
        this.courseTeacher = courseTeacher;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public int getRegisteredStudents() {
        return registeredStudents;
    }

    public void setRegisteredStudents(int registeredStudents) {
        this.registeredStudents = registeredStudents;
    }

    public HashMap<String, List<String>> getAttendance() {
        return attendance;
    }

    public void setAttendance(HashMap<String, List<String>> attendance) {
        this.attendance = attendance;
    }
}
