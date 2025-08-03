package com.aurionpro.dao;

import model.Course;
import model.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
	private final Connection connection;

	public CourseDAO() throws SQLException {
		connection = Database.getInstance().getConnection();
	}

	public void addCourse(Course course) throws SQLException {
		String sql = "INSERT INTO course (name) VALUES (?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, course.getName());
			stmt.executeUpdate();
		}
	}

	public List<Course> getAllCourses() throws SQLException {
		List<Course> courses = new ArrayList<>();
		String sql = "SELECT course_id, name FROM course WHERE isActive = TRUE";
		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				courses.add(new Course(rs.getInt("course_id"), rs.getString("name")));
			}
		}
		return courses;
	}

	public void addSubjectToCourse(int courseId, int subjectId) throws SQLException {
		String sql = "INSERT INTO course_subject (course_id, subject_id) VALUES (?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, courseId);
			stmt.setInt(2, subjectId);
			stmt.executeUpdate();
		}
	}

	public boolean softDeleteCourse(int courseId) throws SQLException {
		String checkSql = "SELECT COUNT(*) FROM student_course WHERE course_id = ? AND isActive = TRUE";
		try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
			checkStmt.setInt(1, courseId);
			ResultSet rs = checkStmt.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				return false; // Students still enrolled
			}
		}

		String sql = "UPDATE course SET isActive = FALSE WHERE course_id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, courseId);
			stmt.executeUpdate();
		}
		return true;
	}
	
	public Course getCourseById(int courseId) throws SQLException {
	    String sql = "SELECT course_id, name FROM course WHERE course_id = ? AND is_active = true";
	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setInt(1, courseId);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return new Course(rs.getInt("course_id"), rs.getString("name"));
	            }
	        }
	    }
	    return null;
	}
	
	public List<Course> getCoursesByStudentId(int studentId) throws SQLException {
	    List<Course> courses = new ArrayList<>();
	    String sql = """
	        SELECT c.course_id, c.name
	        FROM course c
	        JOIN student_course sc ON c.course_id = sc.course_id
	        WHERE sc.student_id = ? AND c.is_active = true
	    """;

	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setInt(1, studentId);
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Course course = new Course(rs.getInt("course_id"), rs.getString("name"));
	                courses.add(course);
	            }
	        }
	    }
	    return courses;
	}


}
