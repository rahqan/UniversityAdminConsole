package com.aurionpro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aurionpro.database.Database;
import com.aurionpro.model.Student;
import com.aurionpro.model.StudentProfile;

public class StudentDAO {

    private final Connection connection;

    public StudentDAO() {
        this.connection = Database.getInstance().getConnection();
    }

    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO student (name, roll_number) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getRollNumber());
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    student.setStudentId(resultSet.getInt(1));
                }
            }
        }
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student WHERE isActive = TRUE";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Student student = new Student(
                    resultSet.getString("name"),
                    resultSet.getString("roll_number")
                );
                student.setStudentId(resultSet.getInt("student_id"));
                students.add(student);
            }
        }

        return students;
    }

    public Student getStudentById(int id) throws SQLException {
        String sql = "SELECT * FROM student WHERE student_id = ? AND isActive = TRUE";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Student student = new Student(
                        resultSet.getString("name"),
                        resultSet.getString("roll_number")
                    );
                    student.setStudentId(resultSet.getInt("student_id"));
                    return student;
                }
            }
        }
        return null;
    }

    public void deleteStudent(int id) throws SQLException {
        String sql = "UPDATE student SET isActive = FALSE WHERE student_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public void assignCourse(int studentId, int courseId) throws SQLException {
        String sql = "INSERT INTO student_course (student_id, course_id) VALUES (?, ?) "
                + "ON DUPLICATE KEY UPDATE isActive = TRUE";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            statement.executeUpdate();
        }
    }

    public Student getStudentByRollNumber(String rollNumber) throws SQLException {
        String sql = "SELECT * FROM student WHERE roll_number = ? AND isActive = TRUE";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, rollNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Student student = new Student(
                        resultSet.getString("name"),
                        resultSet.getString("roll_number")
                    );
                    student.setStudentId(resultSet.getInt("student_id"));
                    return student;
                }
            }
        }

        return null;
    }

    public StudentProfile getStudentProfile(int studentId) throws SQLException {
        String sql = "SELECT * FROM student_profile WHERE student_id = ? AND isActive = TRUE";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new StudentProfile(
                        studentId,
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getDate("dob")
                    );
                }
            }
        }
        return null;
    }

    public void updateProfile(StudentProfile profile) throws SQLException {
        String sql = "UPDATE student_profile SET address = ?, phone = ?, dob = ? WHERE student_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, profile.getAddress());
            stmt.setString(2, profile.getPhone());
            stmt.setDate(3, new java.sql.Date(profile.getDob().getTime()));
            stmt.setInt(4, profile.getStudentId());
            stmt.executeUpdate();
        }
    }

    public void createProfile(StudentProfile profile) throws SQLException {
        String sql = "INSERT INTO student_profile (student_id, address, phone, dob) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, profile.getStudentId());
            statement.setString(2, profile.getAddress());
            statement.setString(3, profile.getPhone());
            statement.setDate(4, profile.getDob());
            statement.executeUpdate();
        }
    }
}