package com.aurionpro.model;

import java.util.List;

public class Course {
	private int courseId;
	private String name;
	private Double courseFee;

	public Course(int courseId, String name, Double courseFee) {
		this.courseId = courseId;
		this.name = name;
		this.courseFee = courseFee;
	}

	public int getCourseId() {
		return courseId;
	}

	public String getName() {
		return name;
	}

	public Double getCourseFee() {
		return courseFee;
	}

	public void setCourseFee(Double courseFee) {
		this.courseFee = courseFee;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
