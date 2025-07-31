package com.aurionpro.service;

package service;

import dao.StudentDAO;
import model.Student;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class StudentService {

    private final StudentDAO studentDAO;

    public StudentService() {
        this.studentDAO = new StudentDAO();
    }

    public void addNewStudent(Student student, StudentProfile profile) throws SQLException {
        studentDAO.addStudent(student); // sets studentId

        if (profile != null) {
            profile.setStudentId(student.getStudentId());

            // Default handling moved into DAO
            studentDAO.createProfile(profile);
        }
    }



    public List<Student> showAllStudents() {
        try {
            return studentDAO.getAllStudents();
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // or Collections.emptyList();
        }
    }

    public Student searchStudent(String keyword) {
        try {
            if (keyword.matches("\\d+")) {
                // Keyword is numeric — search by student ID
                int id = Integer.parseInt(keyword);
                return studentDAO.getStudentById(id);
            } else {
                // Keyword is alphanumeric — treat it as roll number
                return studentDAO.getStudentByRollNumber(keyword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void deleteStudent(int id) {
        try {
            Student student = studentDAO.getStudentById(id);
            if (student != null) {
                studentDAO.deleteStudent(id);
                System.out.println("Student deleted.");
            } else {
                System.out.println("Student not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void assignCourseToStudent(int studentId, int courseId) {
        try {
            studentDAO.assignCourse(studentId, courseId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String viewPersonalRecord(int studentId) {
        // You'll implement this later:
        // Fetch student, profile, course names, etc. from DAOs and format them
        return null;
    }
}

