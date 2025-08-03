package com.aurionpro.UniversityAdmin;

import com.aurionpro.controller.CourseController;

public class UniversityAdmin {
	
    public static void main(String[] args) {
    	
        try {
            CourseController courseController = new CourseController();
            courseController.courseMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
