package com.DP2Spring.controller.clerk;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.DP2Spring.model.Clerk;
import com.DP2Spring.model.Course;
import com.DP2Spring.service.ClerkService;
import com.DP2Spring.service.CourseService;

@Controller
@RequestMapping("/clerk")
public class ClerkController {

	//Attributes
	
	@Autowired
	private ClerkService clerkService;
	
	@Autowired
	CourseService courseService;
	
	
	//Methods
	
	
	
	
	@GetMapping("/listCourses")
	public ModelAndView listCourses() {
		
		Clerk principal = this.clerkService.findByPrincipal();
		ModelAndView result = new ModelAndView("/clerk/listCourses");
		
		Collection<Course> courses = this.courseService.getCoursesByClerk(principal.getId());
		
		result.addObject("myCourses", courses);
		
		return result;
		
	}
	
	
}
