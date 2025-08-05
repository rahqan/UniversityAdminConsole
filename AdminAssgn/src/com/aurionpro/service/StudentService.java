package com.aurionpro.service;

import com.aurionpro.dao.StudentDAO;
import com.aurionpro.model.Student;
import com.aurionpro.model.StudentProfile;
import com.aurionpro.model.Course;
import java.sql.SQLException;
import java.util.List;

public class StudentService {

    private final StudentDAO studentDAO;
    private final CourseService courseService;

    public StudentService() throws SQLException {
        this.studentDAO = new StudentDAO();
        this.courseService = new CourseService();
    }

    public void createStudent(Student student, StudentProfile profile) throws SQLException {
        try {
            // Here student and profile still don't have studentID in them
            studentDAO.addStudent(student);
            profile.setStudentId(student.getStudentId()); // Changed from setStudentID to setStudentId
            studentDAO.createProfile(profile);
        } catch (SQLException e) {
            if (e.getMessage().contains("already exists")) {
                System.out.println("❌ " + e.getMessage());
                throw e; // Re-throw to let controller handle it
            } else {
                throw e; // Re-throw other SQL exceptions
            }
        }
    }

    public StudentProfile getStudentProfile(int studentId) {
        try {
            return studentDAO.getStudentProfile(studentId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateStudentProfile(StudentProfile profile) {
        try {
            studentDAO.updateProfile(profile);
        } catch (SQLException e) {
            e.printStackTrace();
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

    public void displayCourses() throws SQLException {
        List<Course> courses = courseService.getAllCourses();
        System.out.println("Available Courses:");
        for (Course course : courses) {
            System.out.println("Course ID: " + course.getCourseId() + ", Name: " + course.getName() + " Fee: " + course.getCourseFee());
        }
    }

    public void assignCourseToStudent(int studentId, int courseId) {
        try {
            Course course = courseService.getCourseById(courseId);
            if (course == null) {
                System.out.println(" Invalid course ID. Course not found.");
                return;
            }

            studentDAO.assignCourse(studentId, courseId);
            System.out.println(" Course assigned successfully.");
        } catch (SQLException e) {
            if (e.getMessage().contains("already assigned")) {
                System.out.println(" " + e.getMessage());
            } else {
                System.out.println(" Error assigning course: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public String viewPersonalRecord(int studentId) {
        try {
            Student student = studentDAO.getStudentById(studentId);
            StudentProfile profile = studentDAO.getStudentProfile(studentId);
            List<Course> courses = courseService.getCoursesByStudentId(studentId);

            if (student == null || profile == null) {
                return " Student or profile not found.";
            }

            StringBuilder sb = new StringBuilder();
            sb.append("📘 Student Record:\n");
            sb.append("ID: ").append(student.getStudentId()).append("\n");
            sb.append("Name: ").append(student.getName()).append("\n");
            sb.append("Roll Number: ").append(student.getRollNumber()).append("\n");

            sb.append("\n Profile:\n");
            sb.append("Address: ").append(profile.getAddress()).append("\n");
            sb.append("Phone: ").append(profile.getPhone()).append("\n");
            sb.append("DOB: ").append(profile.getDob()).append("\n");

            sb.append("\n Enrolled Courses:\n");
            if (courses == null || courses.isEmpty()) {
                sb.append("No courses assigned.\n");
            } else {
                for (Course c : courses) {
                    sb.append("- ").append(c.getName()).append(" (ID: ").append(c.getCourseId()).append(")\n");
                }
            }

            return sb.toString();

        } catch (SQLException e) {
            e.printStackTrace();
            return " Error retrieving student record.";
        }
    }
}
