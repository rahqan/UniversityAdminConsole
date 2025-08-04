package com.aurionpro.dao;

import java.sql.*;
import java.util.*;
import com.aurionpro.database.Database;
import com.aurionpro.model.Teacher;
import com.aurionpro.model.Subject;

public class TeacherDAO {
	private final Database database = Database.getInstance();

	public boolean addTeacher(Teacher teacher) {
		String query = "INSERT INTO teachers (teacher_name, qualification) VALUES (?, ?)";
		try (Connection connection = database.getConnection();
		     PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

			statement.setString(1, teacher.getName());
			statement.setString(2, teacher.getQualification());

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected == 0) return false;

			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				teacher.setId(resultSet.getInt(1));
			}
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Teacher> getAllTeachers() {
		List<Teacher> teachers = new ArrayList<>();
		String query = "SELECT * FROM teachers WHERE isActive=true";

		try (Connection connection = database.getConnection();
		     PreparedStatement stmt = connection.prepareStatement(query);
		     ResultSet resultSet = stmt.executeQuery()) {

			while (resultSet.next()) {
				Teacher teacher = new Teacher(
						resultSet.getInt("teacher_id"),
						resultSet.getString("teacher_name"),
						resultSet.getString("qualification")
				);
				teachers.add(teacher);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teachers;
	}

	public Teacher getTeacherById(int id) {
		String query = "SELECT * FROM teachers WHERE teacher_id = ? AND isActive = true";
		try (Connection connection = database.getConnection();
		     PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				return new Teacher(
						resultSet.getInt("teacher_id"),
						resultSet.getString("teacher_name"),
						resultSet.getString("qualification")
				);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Teacher searchTeacher(String keyword) {
		String query = "SELECT * FROM teachers WHERE teacher_name LIKE ? AND isActive=true";

		try (Connection connection = database.getConnection();
		     PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setString(1, "%" + keyword + "%");
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				return new Teacher(
						resultSet.getInt("teacher_id"),
						resultSet.getString("teacher_name"),
						resultSet.getString("qualification")
				);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Subject> getSubjectsForTeacher(int teacherId) {
		List<Subject> subjects = new ArrayList<>();
		String query = """
			SELECT s.subject_id, s.subject_name
			FROM teacher_subject ts
			JOIN subjects s ON s.subject_id = ts.subject_id
			WHERE ts.teacher_id = ? AND s.isActive = true AND ts.isActive = true
		""";

		try (Connection connection = database.getConnection();
		     PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, teacherId);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Subject subject = new Subject();
				subject.setSubjectId(resultSet.getInt("subject_id"));
				subject.setSubjectName(resultSet.getString("subject_name"));
				subjects.add(subject);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subjects;
	}

	public boolean deleteTeacher(int teacherId) {
		String query = "UPDATE teachers SET isActive = false WHERE teacher_id = ?";
		try (Connection connection = database.getConnection();
		     PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, teacherId);
			int rows = statement.executeUpdate();
			return rows > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean assignSubjectToTeacher(int subjectId, int teacherId) {
		String query = "INSERT INTO teacher_subject (teacher_id, subject_id) VALUES (?, ?)";
		try (Connection connection = database.getConnection();
		     PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, teacherId);
			statement.setInt(2, subjectId);
			int rows = statement.executeUpdate();
			return rows > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Teacher> getTeachersBySubjectId(int subjectId) {
		List<Teacher> teachers = new ArrayList<>();
		String query = """
			SELECT t.teacher_id, t.teacher_name, t.qualification
			FROM teacher_subject ts
			JOIN teachers t ON t.teacher_id = ts.teacher_id
			WHERE ts.subject_id = ? AND ts.isActive = true AND t.isActive = true
		""";

		try (Connection connection = database.getConnection();
		     PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, subjectId);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				teachers.add(new Teacher(
						resultSet.getInt("teacher_id"),
						resultSet.getString("teacher_name"),
						resultSet.getString("qualification")
				));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teachers;
	}
}
