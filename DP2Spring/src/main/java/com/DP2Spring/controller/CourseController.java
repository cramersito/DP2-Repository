package com.DP2Spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.DP2Spring.model.Course;
import com.DP2Spring.service.ClerkService;
import com.DP2Spring.service.CourseService;

@Controller
@RequestMapping("/course")
public class CourseController {

	//Attributes
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private ClerkService clerkService;
	
	
	//Controllers
	
	@GetMapping("/create")
	public ModelAndView create() {
		ModelAndView result = new ModelAndView("course/create");
		
		Course course = this.courseService.create();
		
		result.addObject("course", course);
		
		return result;
		
	}
	
	@RequestMapping(value = "/create" , method = RequestMethod.POST)
	public ModelAndView save(Course course, BindingResult binding) {
		ModelAndView result = null;
		
		try {
			this.courseService.save(course);
			
			result = new ModelAndView("/");
		}catch(Throwable oops) {
			
		}
		
		return result;
	}
	
}
