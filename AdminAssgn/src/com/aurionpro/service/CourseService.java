package com.aurionpro.service;

import java.sql.SQLException;
import java.util.List;

import com.aurionpro.dao.CourseDAO;
import com.aurionpro.model.Course;
import com.aurionpro.model.Student;

public class CourseService {
	private final CourseDAO courseDAO;
	private CourseDAO courseDao;

	public CourseService() throws SQLException {
		this.courseDAO = new CourseDAO();
	}

	public void addCourse(String name) throws SQLException {
		courseDAO.addCourse(new Course(0, name)); // ID auto-generated
	}

	public List<Course> getAllCourses() throws SQLException {
		return courseDAO.getAllCourses();
	}

	public void addSubjectToCourse(int courseId, int subjectId) throws SQLException {
		courseDAO.addSubjectToCourse(courseId, subjectId);
	}

	public List<Student> getCourseStudents(int courseId) throws SQLException {
		return courseDAO.getCourseStudents(courseId);
	}

	public void deleteCourseIfEmpty(int courseId) throws SQLException {
		courseDAO.deleteCourseIfEmpty(courseId);
	}

	public Course getCourseById(int courseId) throws SQLException {
		return courseDAO.getCourseById(courseId);
	}

	public List<Course> getCoursesByStudentId(int studentId) throws SQLException {
		return courseDAO.getCoursesByStudentId(studentId);
	}
}
