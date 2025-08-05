package com.aurionpro.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
 
import com.aurionpro.service.FeesService;

public class FeesController {

	private FeesService feesService;
	private Scanner scanner;

	public FeesController() throws SQLException {
		this.feesService = new FeesService();
		this.scanner = new Scanner(System.in);
	}

	public void feesMenu() {
		while (true) {
			System.out.println("\n=== Fees Management Menu ===");
			System.out.println("1. Pay Fees");
			System.out.println("2. Modify Course Fee");
			System.out.println("3. View Remaining Fees - All Students");
			System.out.println("4. View Remaining Fees - Specific Student");
			System.out.println("0. Back to Main Menu");
			System.out.print("Choose: ");

			String choice = scanner.nextLine().trim();

			switch (choice) {
			case "1" -> payFeesMenu();
			case "2" -> modifyCourseFeeMenu();
			case "3" -> viewRemainingFeesAll();
			case "4" -> viewRemainingFeesForStudent();
			case "0" -> {
				System.out.println("Returning to main menu...");
				return;
			}
			default -> System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	private void payFeesMenu() {
		System.out.println("Fees left: ");
		viewRemainingFeesAll();
		System.out.println("\n=== Pay Fees ===");
		

		try {
			System.out.print("Enter Student ID: ");
			int studentId = Integer.parseInt(scanner.nextLine().trim());

			System.out.print("Enter Course ID: ");
			int courseId = Integer.parseInt(scanner.nextLine().trim());

			System.out.print("Enter Payment Amount: ");
			BigDecimal amount = new BigDecimal(scanner.nextLine().trim());

			String result = feesService.payFees(studentId, courseId, amount);
			System.out.println("✓ " + result);

		} catch (NumberFormatException e) {
			System.out.println("✗ Invalid input format. Please enter valid numbers.");
		} catch (RuntimeException e) {
			System.out.println("✗ Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("✗ Unexpected error: " + e.getMessage());
		}
	}

	private void modifyCourseFeeMenu() {
		System.out.println("\n=== Modify Course Fee ===");

		try {
			System.out.print("Enter Course ID: ");
			int courseId = Integer.parseInt(scanner.nextLine().trim());

			System.out.print("Enter New Fee Amount: ");
			BigDecimal newFee = new BigDecimal(scanner.nextLine().trim());

			feesService.modifyCourseFee(courseId, newFee);
			System.out.println("✓ Course fee modified successfully");

		} catch (NumberFormatException e) {
			System.out.println("✗ Invalid input format. Please enter valid numbers.");
		} catch (RuntimeException e) {
			System.out.println("✗ Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("✗ Unexpected error: " + e.getMessage());
		}
	}

	private void viewRemainingFeesAll() {
		System.out.println("\n=== Remaining Fees - All Students ===");

		try {
			List<Map<String, Object>> fees = feesService.getRemainingFeesForAll();

			if (fees.isEmpty()) {
				System.out.println("No fee records found.");
				return;
			}

			System.out.println("================================================================================");
			System.out.printf("%-10s %-15s %-10s %-15s %-12s %-12s %-12s %-10s%n", "Student ID", "Student Name",
					"Course ID", "Course Name", "Original Fee", "Paid Amount", "Remaining", "Fully Paid");
			System.out.println("================================================================================");

			for (Map<String, Object> fee : fees) {
				System.out.printf("%-10s %-15s %-10s %-15s %-12s %-12s %-12s %-10s%n", fee.get("student_id"),
						fee.get("student_name"), fee.get("course_id"), fee.get("course_name"), fee.get("original_fee"),
						fee.get("paid_amount"), fee.get("remaining_amount"), fee.get("is_fully_paid"));
			}
			System.out.println("================================================================================");

		} catch (Exception e) {
			System.out.println("✗ Error retrieving fee records: " + e.getMessage());
		}
	}

	private void viewRemainingFeesForStudent() {
		System.out.println("\n=== Remaining Fees - Specific Student ===");

		try {
			System.out.print("Enter Student ID: ");
			int studentId = Integer.parseInt(scanner.nextLine().trim());

			List<Map<String, Object>> fees = feesService.getRemainingFeesForStudent(studentId);

			if (fees.isEmpty()) {
				System.out.println("No fee records found for this student.");
				return;
			}

			System.out.println("================================================================================");
			System.out.printf("%-10s %-15s %-10s %-15s %-12s %-12s %-12s %-10s%n", "Student ID", "Student Name",
					"Course ID", "Course Name", "Original Fee", "Paid Amount", "Remaining", "Fully Paid");
			System.out.println("================================================================================");

			for (Map<String, Object> fee : fees) {
				System.out.printf("%-10s %-15s %-10s %-15s %-12s %-12s %-12s %-10s%n", fee.get("student_id"),
						fee.get("student_name"), fee.get("course_id"), fee.get("course_name"), fee.get("original_fee"),
						fee.get("paid_amount"), fee.get("remaining_amount"), fee.get("is_fully_paid"));
			}
			System.out.println("================================================================================");

		} catch (NumberFormatException e) {
			System.out.println("✗ Invalid Student ID format.");
		} catch (RuntimeException e) {
			System.out.println("✗ Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("✗ Unexpected error: " + e.getMessage());
		}
	}
}
