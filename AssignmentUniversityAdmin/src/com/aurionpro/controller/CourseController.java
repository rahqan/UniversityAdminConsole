package com.aurionpro.controller;

import model.Course;
import model.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

	private final Connection conn;

	public CourseDAO() throws SQLException {
		conn = Database.getInstance().getConnection();
	}

	public void addCourse(Course course) throws SQLException {
		String sql = "INSERT INTO course(name, isActive) VALUES (?, true)";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, course.getName());
		stmt.executeUpdate();
	}

	public List<Course> getAllCourses() throws SQLException {
		List<Course> courses = new ArrayList<>();
		String sql = "SELECT * FROM course WHERE isActive = true";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			Course c = new Course(rs.getInt("course_id"), rs.getString("name"));
			courses.add(c);
		}

		return courses;
	}

	public void addSubjectToCourse(int courseId, int subjectId) throws SQLException {
		String sql = "INSERT INTO course_subject(course_id, subject_id, isActive) VALUES (?, ?, true) "
				+ "ON CONFLICT (course_id, subject_id) DO NOTHING";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, courseId);
		stmt.setInt(2, subjectId);
		stmt.executeUpdate();
	}

	public List<Integer> getStudentIdsForCourse(int courseId) throws SQLException {
		List<Integer> studentIds = new ArrayList<>();
		String sql = "SELECT student_id FROM student_course WHERE course_id = ? AND isActive = true";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, courseId);
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			studentIds.add(rs.getInt("student_id"));
		}

		return studentIds;
	}

	public boolean softDeleteCourse(int courseId) throws SQLException {
		List<Integer> students = getStudentIdsForCourse(courseId);
		if (students.isEmpty()) {
			String sql = "UPDATE course SET isActive = false WHERE course_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, courseId);
			return stmt.executeUpdate() > 0;
		}
		return false;
	}
}
