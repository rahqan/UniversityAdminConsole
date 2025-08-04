package com.aurionpro.UniversityAdmin;

import com.aurionpro.controller.CourseController;
import com.aurionpro.controller.StudentController;
import com.aurionpro.controller.TeacherController;
import com.aurionpro.service.CourseService;

import java.util.Scanner;

public class UniversityAdmin {

<<<<<<< HEAD
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentController studentController = new StudentController();
        CourseController courseController = new CourseController(new CourseService());
        TeacherController teacherController = new TeacherController(); // Assuming it exists
=======
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("HEllo world!");
		
		System.out.println("Hehehehehehe");
		
		System.out.println("kuch to push ho yarr");
		
		System.out.println("final print");
		System.out.println("hello again");
		
		System.out.println("final check for github");
		
		System.out.println("HEllo world!");
		
		System.out.println("Hehehehehehe");
		
		System.out.println("kuch to push ho yarr");
		
		System.out.println("final print");
		System.out.println("hello again");
		
		System.out.println("final check for github");
		
	}
>>>>>>> Arjun

        while (true) {
            System.out.println("\n=== University Admin Menu ===");
            System.out.println("1. Student Management");
            System.out.println("2. Course Management");
            System.out.println("3. Teacher Management");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> studentController.studentMenu();
                case "2" -> courseController.courseMenu();
                case "3" -> teacherController.teacherMenu();
                case "0" -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
