package com.aurionpro.controller;

import com.aurionpro.model.Student;
import com.aurionpro.model.StudentProfile;
import com.aurionpro.service.StudentService;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class StudentController {

    private final StudentService studentService;
    private final Scanner scanner;

    public StudentController() {
        this.studentService = new StudentService(); // or handle exception if needed
        this.scanner = new Scanner(System.in);
    }

    public void studentMenu() {
        while (true) {
            System.out.println("\n--- Student Menu ---");
            System.out.println("1. Add New Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Edit Student Profile");
            System.out.println("5. Delete Student");
            System.out.println("6. Assign Course to Student");
            System.out.println("7. View Personal Record");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            
            int choice = Integer.parseInt(scanner.nextLine());

            try {
                switch (choice) {
                    case 1 -> addNewStudent();
                    case 2 -> showAllStudents();
                    case 3 -> searchStudent();
                    case 4 -> editProfile();
                    case 5 -> deleteStudent();
                    case 6 -> assignCourseToStudent();
                    case 7 -> viewPersonalRecord();
                    case 0 -> {
                        return;
                    }
                    default -> System.out.println("Invalid choice!");
                }
            } catch (SQLException e) {
                System.out.println("❌ Database error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
    }

    private void addNewStudent() throws SQLException {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) name = "Unknown";

        System.out.print("Enter roll number: ");
        String rollInput = scanner.nextLine().trim();
        int rollNumber = rollInput.isEmpty() ? 0 : Integer.parseInt(rollInput);

        Student student = new Student(name, rollNumber);
        StudentProfile profile = new StudentProfile();

        System.out.print("Enter address: ");
        profile.setAddress(scanner.nextLine().trim());

        System.out.print("Enter phone number: ");
        profile.setPhone(scanner.nextLine().trim());

        System.out.print("Enter DOB (yyyy-mm-dd): ");
        String dobInput = scanner.nextLine().trim();
        LocalDate dob = dobInput.isEmpty() ? LocalDate.of(1970, 1, 1) : LocalDate.parse(dobInput);
        profile.setDob(Date.valueOf(dob));

        studentService.createStudent(student, profile);
        System.out.println("✅ Student added with ID: " + student.getStudentId());

        boolean continueAssigning = true;
        while (continueAssigning) {
            studentService.displayCourses();
            System.out.println("Enter course ID to assign (or 'exit'):");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) break;
            try {
                int courseId = Integer.parseInt(input);
                studentService.assignCourseToStudent(student.getStudentId(), courseId);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        }
    }

    private void showAllStudents() throws SQLException {
        List<Student> students = studentService.showAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        for (Student s : students) {
            System.out.println(s);
        }
    }

    private void searchStudent() throws SQLException {
        System.out.print("Enter student ID or roll number: ");
        String keyword = scanner.nextLine().trim();
        Student student = studentService.searchStudent(keyword);
        if (student != null) {
            System.out.println("Student found:\n" + student);
        } else {
            System.out.println("Student not found.");
        }
    }

    private void editProfile() throws SQLException {
        System.out.print("Enter student ID to edit profile: ");
        int studentId = Integer.parseInt(scanner.nextLine());

        StudentProfile currentProfile = studentService.getStudentProfile(studentId);
        if (currentProfile == null) {
            System.out.println("Student profile not found.");
            return;
        }

        System.out.println("Leave blank to keep existing value.");

        System.out.print("Current Address: " + currentProfile.getAddress() + "\nNew Address: ");
        String address = scanner.nextLine().trim();
        if (address.isEmpty()) address = currentProfile.getAddress();

        System.out.print("Current Phone: " + currentProfile.getPhone() + "\nNew Phone: ");
        String phone = scanner.nextLine().trim();
        if (phone.isEmpty()) phone = currentProfile.getPhone();

        System.out.print("Current DOB: " + currentProfile.getDob() + "\nNew DOB (yyyy-mm-dd): ");
        String dobInput = scanner.nextLine().trim();
        Date dob = dobInput.isEmpty() ? currentProfile.getDob() : Date.valueOf(dobInput);

        StudentProfile updated = new StudentProfile(studentId, address, phone, dob);
        studentService.updateStudentProfile(updated);
        System.out.println("✅ Profile updated.");
    }

    private void deleteStudent() throws SQLException {
        System.out.print("Enter student ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        studentService.deleteStudent(id);
        System.out.println("Student deleted.");
    }

    private void assignCourseToStudent() throws SQLException {
        System.out.print("Enter student ID: ");
        int studentId = Integer.parseInt(scanner.nextLine());

        studentService.displayCourses();
        System.out.print("Enter course ID: ");
        int courseId = Integer.parseInt(scanner.nextLine());

        studentService.assignCourseToStudent(studentId, courseId);
        System.out.println("Course assigned.");
    }

    private void viewPersonalRecord() throws SQLException {
        System.out.print("Enter student ID: ");
        int studentId = Integer.parseInt(scanner.nextLine());
        String record = studentService.viewPersonalRecord(studentId);
        System.out.println(record);
    }
}
