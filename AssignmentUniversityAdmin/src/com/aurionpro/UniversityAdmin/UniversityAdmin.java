package com.aurionpro.UniversityAdmin;

import com.aurionpro.controller.CourseController;
import com.aurionpro.service.CourseService;

public class UniversityAdmin {
	
    public static void main(String[] args) {
        try {
            CourseController courseController = new CourseController(new CourseService());
            courseController.courseMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
