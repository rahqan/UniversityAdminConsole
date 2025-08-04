package com.aurionpro.controller;

public class StudentController {

	public StudentController() {
		this.studentService = new StudentService();

		this.scanner = new Scanner(System.in);
	}
	
	public void addNewStudent() {
		

		try {
			System.out.print("Enter student name: ");
			String name = scanner.nextLine().trim();
			if (name.isEmpty())
				name = "Unknown"; 

			System.out.print("Enter roll number: ");
			String rollInput = scanner.nextLine().trim();
			int rollNumber = rollInput.isEmpty() ? 0 : Integer.parseInt(rollInput); // default 0

			// Create student
			Student student = new Student(name, rollNumber);

			StudentProfile profile = new StudentProfile();

			System.out.print("Enter address (leave blank for none): ");
			String address = scanner.nextLine().trim();
			profile.setAddress(address.isEmpty() ? "" : address);

			System.out.print("Enter phone number (leave blank for none): ");
			String phone = scanner.nextLine().trim();
			profile.setPhone(phone.isEmpty() ? "" : phone);

			System.out.print("Enter DOB (yyyy-mm-dd) (leave blank for default): ");
			String dobInput = scanner.nextLine().trim();
			LocalDate dob = dobInput.isEmpty() ? LocalDate.of(1970, 1, 1) : LocalDate.parse(dobInput);
			profile.setDob(Date.valueOf(dob));


			// Save both to DB
			studentService.createStudent(student, profile);
			
			
			boolean continueAssignining = true;
		
			
			

			while (continueAssignining) {

				studentService.displayCourses();

				System.out.println("Enter course ID to assign (or type 'exit' to stop):");
				String input = scanner.nextLine();

				if (input.equalsIgnoreCase("exit")) {
					continueAssignining = false;
					break;
				}

				try {
					int courseId = Integer.parseInt(input);
					studentService.assignCourseToStudent(student.getStudentId(), courseId);
				} catch (NumberFormatException e) {
					System.out.println("Invalid input. Please enter a valid course ID or 'exit'.");
				}
			}

			System.out.println("✅ Student added with ID: " + student.getStudentId());
			// System.out.println("✅ Profile created with ID: " + profile.getProfileId());

		} catch (SQLException e) {
			System.err.println("❌ Database error: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("❌ Invalid input: " + e.getMessage());
		}
	}
	
	
	

	public void showAllStudents() {
		List<Student> students = studentService.showAllStudents();
		if (students.isEmpty()) {
			System.out.println("No students found.");
			return;
		}

		for (Student s : students) {
			System.out.println(s);
		}
	}

	public void searchStudent() {
		System.out.println("Enter student ID or roll number:");
		String keyword = scanner.nextLine();
		Student student = studentService.searchStudent(keyword);

		if (student != null) {
			System.out.println("Student found:");
			System.out.println(student);
		} else {
			System.out.println("Student not found.");
		}
	}
	
	public void editProfile() {
	    try {
	        System.out.println("Enter student ID to edit profile:");
	        int studentId = Integer.parseInt(scanner.nextLine());

	        // Get current profile to show user (optional)
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
	        Date dob = dobInput.isEmpty() ? currentProfile.getDob() : java.sql.Date.valueOf(dobInput);

	        StudentProfile updated = new StudentProfile(studentId, address, phone, dob);
	        studentService.updateStudentProfile(updated);
	        System.out.println("✅ Profile updated successfully.");

	    } catch (Exception e) {
	        System.err.println("❌ Error editing profile: " + e.getMessage());
	    }
	}

	
	

	public void deleteStudent() {
		System.out.println("Enter student ID to delete:");
		int id = Integer.parseInt(scanner.nextLine());

		studentService.deleteStudent(id);
		System.out.println("Student deleted successfully.");
	}

	public void assignCourseToStudent() {

		System.out.println("Enter student ID:");
		int studentId = Integer.parseInt(scanner.nextLine());
		studentService.displayCourses();
		
		System.out.println("Enter course ID:");
		int courseId=Integer.parseInt(scanner.nextLine());
		studentService.assignCourseToStudent(studentId,courseId);

	}

	public void viewPersonalRecord() {
		System.out.println("Enter student ID:");
		int studentId = Integer.parseInt(scanner.nextLine());

		String record = studentService.viewPersonalRecord(studentId);
		System.out.println(record);
	}
}
