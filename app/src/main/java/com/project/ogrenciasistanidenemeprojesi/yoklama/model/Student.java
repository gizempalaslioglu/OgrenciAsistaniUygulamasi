package com.project.ogrenciasistanidenemeprojesi.yoklama.model;

import java.util.List;

public class Student {
    private String id;
    private String name;
    private String surname;
    private String email;
    private String number;
    private String imageURL;
    private String password;

    private List<String> registeredCourses;

    public Student() {}

    public Student(String id, String name, String surname, String email, String number, String imageURL, List<String> registeredCourses, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.number = number;
        this.imageURL = imageURL;
        this.registeredCourses = registeredCourses;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public List<String> getRegisteredCourses() {
        return registeredCourses;
    }

    public void setRegisteredCourses(List<String> registeredCourses) {
        this.registeredCourses = registeredCourses;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
