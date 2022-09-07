package com.project.adminapp.model;

public class StudentDataStore {
    String name, email, phone_number, enrollment, branch, CGPA, password;

    public StudentDataStore() {
    }


    public StudentDataStore(String name, String email, String phone_number, String enrollment, String branch, String CGPA, String password) {
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
        this.enrollment = enrollment;
        this.branch = branch;
        this.CGPA = CGPA;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(String enrollment) {
        this.enrollment = enrollment;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCGPA() {
        return CGPA;
    }

    public void setCGPA(String CGPA) {
        this.CGPA = CGPA;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
