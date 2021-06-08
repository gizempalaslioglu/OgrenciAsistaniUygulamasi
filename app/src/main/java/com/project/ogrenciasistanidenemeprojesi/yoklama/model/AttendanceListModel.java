package com.project.ogrenciasistanidenemeprojesi.yoklama.model;

public class AttendanceListModel {

    private Student student;
    private String weekPos;
    private String courseName;
    private String ifSuccess;


    public AttendanceListModel(Student student, String weekPos, String courseName, String ifSuccess) {
        this.student = student;
        this.weekPos = weekPos;
        this.courseName = courseName;
        this.ifSuccess = ifSuccess;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getWeekPos() {
        return weekPos;
    }

    public void setWeekPos(String weekPos) {
        this.weekPos = weekPos;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getIfSuccess() {
        return ifSuccess;
    }

    public void setIfSuccess(String ifSuccess) {
        this.ifSuccess = ifSuccess;
    }
}
