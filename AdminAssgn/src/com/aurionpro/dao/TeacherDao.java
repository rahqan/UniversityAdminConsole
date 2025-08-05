package com.aurionpro.dao;

import java.sql.*;
import java.util.*;
import com.aurionpro.database.Database;
import com.aurionpro.model.Teacher;
import com.aurionpro.model.TeacherProfile;
import com.aurionpro.model.Subject;

public class TeacherDao {
	private final Database database = Database.getInstance();

	public boolean addTeacher(Teacher teacher) {
		String query = "INSERT INTO teacher (name) VALUES (?)";
		try {
			Connection connection = database.getConnection();
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			statement.setString(1, teacher.getName());

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected == 0)
				return false;

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
		String query = "SELECT * FROM teacher WHERE isActive=true";

		try {
			Connection connection = database.getConnection();
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				Teacher teacher = new Teacher(resultSet.getInt("teacher_id"), resultSet.getString("name"));
				teachers.add(teacher);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teachers;
	}

	public Teacher getTeacherById(int id) {
		String query = "SELECT * FROM teacher WHERE teacher_id = ? AND isActive = true";
		try {
			Connection connection = database.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);

			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				return new Teacher(resultSet.getInt("teacher_id"), resultSet.getString("name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Teacher> searchTeachers(String keyword) {
		List<Teacher> teachers = new ArrayList<>();
		String query = "SELECT * FROM teacher WHERE name LIKE ? AND isActive = TRUE";

		try {

			Connection connection = database.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);

			statement.setString(1, "%" + keyword + "%");
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				teachers.add(new Teacher(resultSet.getInt("teacher_id"), resultSet.getString("name")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teachers;
	}

	public List<Subject> getSubjectsForTeacher(int teacherId) {
		List<Subject> subjects = new ArrayList<>();
		String query = """
					SELECT s.subject_id, s.name
					FROM teacher_subject ts
					JOIN subject s ON s.subject_id = ts.subject_id
					WHERE ts.teacher_id = ? AND s.isActive = true AND ts.isActive = true
				""";

		try {
			Connection connection = database.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);

			statement.setInt(1, teacherId);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Subject subject = new Subject();
				subject.setSubjectId(resultSet.getInt("subject_id"));
				subject.setSubjectName(resultSet.getString("name"));
				subjects.add(subject);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subjects;
	}

	public boolean deleteTeacher(int teacherId) {
		String deactivateTeacher = "UPDATE teacher SET isActive = false WHERE teacher_id = ?";
		String deactivateProfile = "UPDATE teacher_profile SET isActive = false WHERE teacher_id = ?";
		String deactivateSubjects = "UPDATE teacher_subject SET isActive = false WHERE teacher_id = ?";

		try {
			Connection connection = database.getConnection();
			connection.setAutoCommit(false);

			try {
				PreparedStatement stmtTeacher = connection.prepareStatement(deactivateTeacher);
				PreparedStatement stmtProfile = connection.prepareStatement(deactivateProfile);
				PreparedStatement stmtSubjects = connection.prepareStatement(deactivateSubjects);
				// deleting teahcer
				stmtTeacher.setInt(1, teacherId);
				int rowsTeacher = stmtTeacher.executeUpdate();

				if (rowsTeacher == 0) {
					connection.rollback();
					return false;
				}

				// delete profile
				stmtProfile.setInt(1, teacherId);
				stmtProfile.executeUpdate();

				// delete all subjects
				stmtSubjects.setInt(1, teacherId);
				stmtSubjects.executeUpdate();

				connection.commit();
				return true;
			} catch (SQLException e) {
				connection.rollback();
				e.printStackTrace();
				return false;
			} finally {
				connection.setAutoCommit(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean assignSubjectToTeacher(int subjectId, int teacherId) {
		String query = "INSERT INTO teacher_subject (teacher_id, subject_id) VALUES (?, ?)";
		try {
			Connection connection = database.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);

			statement.setInt(1, teacherId);
			statement.setInt(2, subjectId);
			int rows = statement.executeUpdate();
			return rows > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Teacher> getTeacherBySubjectId(int subjectId) {
		List<Teacher> teachers = new ArrayList<>();
		String query = """
					SELECT t.teacher_id, t.name
					FROM teacher_subject ts
					JOIN teacher t ON t.teacher_id = ts.teacher_id
					WHERE ts.subject_id = ? AND ts.isActive = true AND t.isActive = true
				""";

		try {
			Connection connection = database.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);

			statement.setInt(1, subjectId);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				teachers.add(new Teacher(resultSet.getInt("teacher_id"), resultSet.getString("name")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teachers;
	}

	public boolean addTeacherProfile(TeacherProfile profile) {
		String query = """
				    INSERT INTO teacher_profile (teacher_id, address, phone, department)
				    VALUES (?, ?, ?, ?)
				""";

		try {
			Connection connection = database.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);

			statement.setInt(1, profile.getTeacherId());
			statement.setString(2, profile.getAddress());
			statement.setString(3, profile.getPhone());
			statement.setString(4, profile.getDepartment());

			return statement.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateTeacherProfile(TeacherProfile profile) {
		String query = """
				    UPDATE teacher_profile
				    SET address = ?, phone = ?, department = ?, updated_at = CURRENT_TIMESTAMP
				    WHERE teacher_id = ? AND isActive = TRUE
				""";

		try {
			Connection connection = database.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);

			statement.setString(1, profile.getAddress());
			statement.setString(2, profile.getPhone());
			statement.setString(3, profile.getDepartment());
			statement.setInt(4, profile.getTeacherId());

			return statement.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public TeacherProfile getTeacherProfile(int teacherId) {
		String query = """
				    SELECT * FROM teacher_profile
				    WHERE teacher_id = ? AND isActive = TRUE
				""";

		try {
			Connection connection = database.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);

			statement.setInt(1, teacherId);
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				return new TeacherProfile(rs.getInt("teacher_id"), rs.getString("address"), rs.getString("phone"),
						rs.getString("department"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean deleteTeacherProfile(int teacherId) {
		String query = "UPDATE teacher_profile SET isActive = FALSE WHERE teacher_id = ?";
		try {
			Connection connection = database.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);

			statement.setInt(1, teacherId);
			return statement.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// everything
	public Teacher getTeacherDetails(int teacherId) {
		Teacher teacher = getTeacherById(teacherId);
		if (teacher == null)
			return null;

		teacher.setProfile(getTeacherProfile(teacherId));
		teacher.setSubjects(getSubjectsForTeacher(teacherId));
		return teacher;
	}

}
