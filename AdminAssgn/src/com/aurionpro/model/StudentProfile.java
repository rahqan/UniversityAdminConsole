package com.aurionpro.model;

import java.sql.Date;

public class StudentProfile {
    private int studentId; 
    private String address;
    private String phone;
    private Date dob;

    // Constructors
    public StudentProfile() {}

    public StudentProfile(int studentId, String address, String phone, Date dob) {
        this.studentId = studentId;
        this.address = address;
        this.phone = phone;
        this.dob = dob;
    }

    // Getters and Setters
    public int getStudentId() { 
        return studentId;
    }

    public void setStudentId(int studentId) { 
        this.studentId = studentId;
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
                "studentId=" + studentId + 
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", dob=" + dob +
                '}';
    }
}