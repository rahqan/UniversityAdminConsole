package com.aurionpro.controller;

import java.util.List;
import java.util.Scanner;

import com.aurionpro.model.Teacher;
import com.aurionpro.model.TeacherProfile;
import com.aurionpro.model.Subject;
import com.aurionpro.service.TeacherService;

public class TeacherController {
    private final Scanner scanner = new Scanner(System.in);
    private final TeacherService teacherService = new TeacherService();

    public void teacherMenu() {
        while (true) {
            System.out.println("\n--- Teacher Menu ---");
            System.out.println("1. Add New Teacher");
            System.out.println("2. Show All Teachers");
            System.out.println("3. Search Teacher");
            System.out.println("4. Delete Teacher");
            System.out.println("5. Assign Subject to Teacher");
            System.out.println("6. View Subjects by Teacher");
            System.out.println("0. Exit");

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addNewTeacher();
                case 2 -> showAllTeachers();
                case 3 -> searchTeacher();
                case 4 -> deleteTeacher();
                case 5 -> assignSubjectToTeacher();
                case 6 -> viewSubjectsByTeacher();
                case 0 -> {
                    System.out.println("Exiting Teacher Menu...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void addNewTeacher() {
        System.out.print("Enter teacher name: ");
        String name = scanner.nextLine();

        Teacher teacher = new Teacher();
        teacher.setName(name);

        if (teacherService.addTeacher(teacher)) {
            System.out.println("Teacher added successfully with ID: " + teacher.getId());

            System.out.print("Enter address: ");
            String address = scanner.nextLine();
            System.out.print("Enter phone: ");
            String phone = scanner.nextLine();
            System.out.print("Enter department: ");
            String department = scanner.nextLine();

            TeacherProfile profile = new TeacherProfile(
                    teacher.getId(), address, phone, department
            );
            teacherService.addTeacherProfile(profile);

            System.out.println("Teacher profile saved.");
        } else {
            System.out.println("Failed to add teacher.");
        }
    }

    private void showAllTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        if (teachers.isEmpty()) {
            System.out.println("No teachers found.");
            return;
        }

        System.out.println("\n--------------------------------------------------------------------------------------------");
        System.out.printf("%-5s %-15s %-20s %-15s %-15s %-30s%n",
                "ID", "Name", "Address", "Phone", "Department", "Subjects");
        System.out.println("--------------------------------------------------------------------------------------------");

        for (Teacher teacher : teachers) {
            teacher = teacherService.getTeacherDetails(teacher.getId());

            String address = (teacher.getProfile() != null) ? teacher.getProfile().getAddress() : "N/A";
            String phone = (teacher.getProfile() != null) ? teacher.getProfile().getPhone() : "N/A";
            String department = (teacher.getProfile() != null) ? teacher.getProfile().getDepartment() : "N/A";

            String subjects = "None Assigned";
            if (teacher.getSubjects() != null && !teacher.getSubjects().isEmpty()) {
                subjects = teacher.getSubjects().stream()
                        .map(sub -> sub.getSubjectName() + " (" + sub.getSubjectId() + ")")
                        .reduce((s1, s2) -> s1 + ", " + s2)
                        .orElse("None Assigned");
            }

            System.out.printf("%-5d %-15s %-20s %-15s %-15s %-30s%n",
                    teacher.getId(), teacher.getName(), address, phone, department, subjects);
        }

        System.out.println("--------------------------------------------------------------------------------------------\n");
    }


    private void searchTeacher() {
        System.out.print("Enter teacher name to search: ");
        String keyword = scanner.nextLine();

        List<Teacher> teachers = teacherService.searchTeachers(keyword);

        if (teachers.isEmpty()) {
            System.out.println("No teacher found with that name.");
        } else {
        	 System.out.println("--- Search Results ---");
             System.out.printf("%-5s %-20s %-20s %-15s %-20s %-30s%n",
                     "ID", "Name", "Department", "Phone", "Address", "Subjects");
             System.out.println("---------------------------------------------------------------------------------------------------------------------------");

             for (Teacher teacher : teachers) {
                 teacher = teacherService.getTeacherDetails(teacher.getId());

                 String dept = (teacher.getProfile() != null) ? teacher.getProfile().getDepartment() : "N/A";
                 String phone = (teacher.getProfile() != null) ? teacher.getProfile().getPhone() : "N/A";
                 String address = (teacher.getProfile() != null) ? teacher.getProfile().getAddress() : "N/A";
                 String subjects = (teacher.getSubjects() != null && !teacher.getSubjects().isEmpty())
                         ? teacher.getSubjects().stream()
                             .map(sub -> sub.getSubjectName() + "(ID:" + sub.getSubjectId() + ")")
                             .reduce((a, b) -> a + ", " + b).orElse("None")
                         : "None";

                 System.out.printf("%-5d %-20s %-20s %-15s %-20s %-30s%n",
                         teacher.getId(), teacher.getName(), dept, phone, address, subjects);
             }

             System.out.println("---------------------------------------------------------------------------------------------------------------------------");
        }
    }

    private void deleteTeacher() {
    	System.out.println("Existing teachers: ");
    	showAllTeachers();
        System.out.print("Enter teacher ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            boolean deleted = teacherService.deleteTeacher(id);
            if (deleted) {
                System.out.println("Teacher and related profile deleted successfully.");

                System.out.println("\nUpdated Teachers List:");
                showAllTeachers();
            }
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private void assignSubjectToTeacher() {
    	System.out.println("Teachers: ");
    	showAllTeachers();
    	
        System.out.print("Enter teacher ID: ");
        int teacherId = scanner.nextInt();

        System.out.println("Subjects: ");
        
        System.out.print("Enter subject ID: ");
        int subjectId = scanner.nextInt();
        scanner.nextLine();

        try {
            boolean success = teacherService.assignSubjectToTeacher(subjectId, teacherId);
            if (success) {
                System.out.println("Subject assigned successfully.");
            }
        } catch (IllegalStateException e) {
            System.out.println("Assignment failed: " + e.getMessage());
        }
    }

    private void viewSubjectsByTeacher() {
        System.out.print("Enter teacher ID: ");
        int teacherId = scanner.nextInt();
        scanner.nextLine();

        List<Subject> subjects = teacherService.getSubjectsForTeacher(teacherId);
        if (subjects.isEmpty()) {
            System.out.println("No subjects assigned to this teacher.");
        } else {
            System.out.println("Subjects for Teacher ID " + teacherId + ":");
            for (Subject subject : subjects) {
                System.out.println("Subject ID: " + subject.getSubjectId() + ", Name: " + subject.getSubjectName());
            }
        }
    }
}
