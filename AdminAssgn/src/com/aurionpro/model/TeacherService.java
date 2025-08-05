package com.aurionpro.model;

public class TeacherProfile {
    private int teacherId;
    private String address;
    private String phone;
    private String department;

    public TeacherProfile() {}

    public TeacherProfile(int teacherId, String address, String phone, String department) {
        this.teacherId = teacherId;
        this.address = address;
        this.phone = phone;
        this.department = department;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "TeacherProfile{" +
                "teacherId=" + teacherId +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
