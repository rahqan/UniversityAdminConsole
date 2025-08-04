package com.aurionpro.controller;

import com.aurionpro.model.Course;
import com.aurionpro.model.Student;
import com.aurionpro.service.CourseService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CourseController {
	
	

    private final CourseService courseService;
    private final Scanner scanner;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
		try {
			courseService = new CourseService();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        scanner = new Scanner(System.in);
    }

    public void courseMenu() {
        while (true) {
            System.out.println("\n--- Course Menu ---");
            System.out.println("1. Add Course");
            System.out.println("2. View All Courses");
            System.out.println("3. Add Subject to Course");
            System.out.println("4. View Students of a Course");
            System.out.println("5. Delete Course if Empty");
            System.out.println("6. Get Course by ID");
            System.out.println("7. Get Courses by Student ID");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1 -> addCourse();
                    case 2 -> getAllCourses();
                    case 3 -> addSubjectToCourse();
                    case 4 -> getCourseStudents();
                    case 5 -> deleteCourseIfEmpty();
                    case 6 -> getCourseById();
                    case 7 -> getCoursesByStudentId();
                    case 0 -> {
                        return;
                    }
                    default -> System.out.println("Invalid choice!");
                }
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void addCourse() throws SQLException {
        System.out.print("Enter course name: ");
        String name = scanner.nextLine();
        courseService.addCourse(name);
        System.out.println("Course added successfully.");
    }

    private void getAllCourses() throws SQLException {
        List<Course> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        for (Course course : courses) {
            System.out.println(course.getCourseId() + ": " + course.getName());
        }
    }

    private void addSubjectToCourse() throws SQLException {
        System.out.print("Enter course ID: ");
        int courseId = scanner.nextInt();
        System.out.print("Enter subject ID: ");
        int subjectId = scanner.nextInt();
        scanner.nextLine();
        courseService.addSubjectToCourse(courseId, subjectId);
        System.out.println("Subject added to course.");
    }

    private void getCourseStudents() throws SQLException {
        System.out.print("Enter course ID: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();
        List<Student> students = courseService.getCourseStudents(courseId);
        if (students.isEmpty()) {
            System.out.println("No students enrolled in this course.");
            return;
        }
        for (Student s : students) {
            System.out.println(s.getStudentId() + ": " + s.getName());
        }
    }

    private void deleteCourseIfEmpty() throws SQLException {
        System.out.print("Enter course ID: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();
        courseService.deleteCourseIfEmpty(courseId);
        System.out.println("Checked and deleted if course is empty.");
    }

    private void getCourseById() throws SQLException {
        System.out.print("Enter course ID: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();
        Course c = courseService.getCourseById(courseId);
        if (c == null) {
            System.out.println("Course not found.");
        } else {
            System.out.println("ID: " + c.getCourseId() + ", Name: " + c.getName());
        }
    }

    private void getCoursesByStudentId() throws SQLException {
        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();
        List<Course> courses = courseService.getCoursesByStudentId(studentId);
        if (courses.isEmpty()) {
            System.out.println("No courses found for this student.");
            return;
        }
        for (Course c : courses) {
            System.out.println("ID: " + c.getCourseId() + ", Name: " + c.getName());
        }
    }
}
