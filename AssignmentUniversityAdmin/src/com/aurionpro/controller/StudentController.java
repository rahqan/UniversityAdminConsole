package com.aurionpro.controller;

public class StudentController {
	private final CourseService courseService;

	public StudentController() {
	    this.studentService = new StudentService();
	    this.courseService = new CourseService();  
	    this.scanner = new Scanner(System.in);
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

    public void deleteStudent() {
        System.out.println("Enter student ID to delete:");
        int id = Integer.parseInt(scanner.nextLine());

        studentService.deleteStudent(id);
        System.out.println("Student deleted successfully.");
    }

	
	public void assignCourseToStudent() {
	    // Step 1: Show available courses
	    List<Course> courses = courseService.getAllCourses();
	    if (courses.isEmpty()) {
	        System.out.println("No courses available to assign.");
	        return;
	    }

	    System.out.println("Available Courses:");
	    for (Course course : courses) {
	        System.out.println("Course ID: " + course.getCourseId() + ", Name: " + course.getName());
	    }

	    // Step 2: Get user input
	    System.out.println("Enter student ID:");
	    int studentId = Integer.parseInt(scanner.nextLine());

	    System.out.println("Enter course ID to assign:");
	    int courseId = Integer.parseInt(scanner.nextLine());

	    // Step 3: Assign the course
	    studentService.assignCourseToStudent(studentId, courseId);
	    System.out.println("Course assigned successfully.");
	}
	   public void viewPersonalRecord() {
	        System.out.println("Enter student ID:");
	        int studentId = Integer.parseInt(scanner.nextLine());

	        String record = studentService.viewPersonalRecord(studentId);
	        System.out.println(record);
	    }
}
