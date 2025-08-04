package com.aurionpro.model;

import java.util.List;

public class Course {
	private int courseId;
	private String name;

	public Course(int courseId, String name) {
		this.courseId = courseId;
		this.name = name;
	}

	public int getCourseId() {
		return courseId;
	}

	public String getName() {
		return name;
	}
}
