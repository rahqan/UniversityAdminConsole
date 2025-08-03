package com.aurionpro.service;

import dao.CourseDAO;
import model.Course;

import java.sql.SQLException;
import java.util.List;

public class CourseService {
	private final CourseDAO courseDAO;

	public CourseService() throws SQLException {
		courseDAO = new CourseDAO();
	}

	public void addCourse(String name) throws SQLException {
		Course course = new Course(name);
		courseDAO.addCourse(course);
	}

	public List<Course> getAllCourses() throws SQLException {
		return courseDAO.getAllCourses();
	}

	public void addSubjectToCourse(int courseId, int subjectId) throws SQLException {
		courseDAO.addSubjectToCourse(courseId, subjectId);
	}

	public boolean deleteCourse(int courseId) throws SQLException {
		return courseDAO.softDeleteCourse(courseId);
	}
}
