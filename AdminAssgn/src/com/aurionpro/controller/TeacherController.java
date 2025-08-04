package com.aurionpro.controller;

import java.util.List;
import java.util.Scanner;

import com.aurionpro.model.Teacher;
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

		System.out.print("Enter qualification: ");
		String qualification = scanner.nextLine();

		Teacher teacher = new Teacher();
		teacher.setName(name);
		teacher.setQualification(qualification);

		boolean success = teacherService.addTeacher(teacher);
		if (success) {
			System.out.println("Teacher added successfully with ID: " + teacher.getId());
		} else {
			System.out.println("Failed to add teacher.");
		}
	}

	private void showAllTeachers() {
		List<Teacher> teachers = teacherService.getAllTeachers();
		if (teachers.isEmpty()) {
			System.out.println("No teachers found.");
		} else {
			System.out.println("--- All Teachers ---");
			for (Teacher teacher : teachers) {
				System.out.println("ID: " + teacher.getId() + ", Name: " + teacher.getName() + ", Qualification: " + teacher.getQualification());
			}
		}
	}

	private void searchTeacher() {
		System.out.print("Enter teacher name to search: ");
		String keyword = scanner.nextLine();

		Teacher teacher = teacherService.searchTeacher(keyword);
		if (teacher != null) {
			System.out.println("Teacher Found: ID: " + teacher.getId() + ", Name: " + teacher.getName() + ", Qualification: " + teacher.getQualification());
		} else {
			System.out.println("No teacher found with that name.");
		}
	}

	private void deleteTeacher() {
		System.out.print("Enter teacher ID to delete: ");
		int id = scanner.nextInt();
		scanner.nextLine();

		try {
			boolean deleted = teacherService.deleteTeacher(id);
			if (deleted) {
				System.out.println("Teacher deleted successfully.");
			}
		} catch (IllegalStateException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private void assignSubjectToTeacher() {
		System.out.print("Enter teacher ID: ");
		int teacherId = scanner.nextInt();

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
