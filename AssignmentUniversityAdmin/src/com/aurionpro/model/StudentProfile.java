package com.aurionpro.model;

import java.sql.Date;

public class StudentProfile {
    private int studentID;
    private String address;
    private String phone;
    private Date dob;

    // Constructors
    public StudentProfile() {}

    public StudentProfile(int studentID, String address, String phone, Date dob) {
        this.studentID = studentID;
        this.address = address;
        this.phone = phone;
        this.dob = dob;
    }

    // Getters and Setters
    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "StudentProfile {" +
                "studentID=" + studentID +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", dob=" + dob +
                '}';
    }
}
