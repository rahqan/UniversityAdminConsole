package com.aurionpro.model;

public class Student {

	private int studentId;
	private String name;
	private String rollNumber;

	public Student(String name, String rollNumber) {

		this.name = name;
		this.rollNumber = rollNumber;

		this.courses = courses;
	}

	// Getters and Setters
	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}

	@Override
	public String toString() {
		return "Student{" + "studentId=" + studentId + ", name='" + name + '\'' + ", rollNumber='" + rollNumber + '\'' +

				'}';
	}

}
