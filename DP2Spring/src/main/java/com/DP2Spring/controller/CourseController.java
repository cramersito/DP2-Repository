package com.DP2Spring.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.DP2Spring.model.Certificate;
import com.DP2Spring.model.Course;
import com.DP2Spring.service.CertificateService;
import com.DP2Spring.service.ClerkService;
import com.DP2Spring.service.CourseService;

@Controller
@RequestMapping("/course")
public class CourseController {

	//Attributes
	
	@Autowired
	private CourseService courseService;
	

	
	@Autowired
	CertificateService certificateService;
	
	@Autowired
	ClerkService clerkService;
	
	
	//Controllers
	
	@GetMapping("/create")
	public ModelAndView create(@RequestParam int certificateId) {
		ModelAndView result = new ModelAndView("course/create");
		
		Course course = new Course();
		
		course.setClerk(this.clerkService.findByPrincipal());
		
		
		Certificate certificate = this.certificateService.findOne(certificateId);
		
		course.setCertificate(certificate);
		
		result.addObject("course", course);
		
		return result;
		
	}
	
	@GetMapping("/enroll/list")
	public ModelAndView enrollList() {
		ModelAndView result = new ModelAndView("course/enrollList");
		
		Collection<Course> courses = this.courseService.getEnrollCourses();
		
		
		result.addObject("courses", courses);
		
		return result;
		
	}
	
	@RequestMapping(value = "/create" , method = RequestMethod.POST)
	public ModelAndView save(@Valid Course course, BindingResult binding) {
		ModelAndView result;
		
		if(binding.hasErrors()) {
			
			result = new ModelAndView("/course/create");
			
		}else {
			try {
				this.courseService.save(course);
				
				result = new ModelAndView("redirect:/clerk/listCourses");
			}catch(Throwable oops) {
				result = new ModelAndView("/course/create");
				
				result.addObject("errorMessage", oops.getMessage());
			}
		}
		
		
		return result;
	}
	
}
