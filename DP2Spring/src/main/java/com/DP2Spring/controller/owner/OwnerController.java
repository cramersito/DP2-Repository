package com.DP2Spring.controller.owner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.DP2Spring.model.Course;
import com.DP2Spring.model.Owner;
import com.DP2Spring.model.Pet;
import com.DP2Spring.service.CourseService;
import com.DP2Spring.service.OwnerService;
import com.DP2Spring.service.PetService;

@Controller
@RequestMapping("/owner")
public class OwnerController {

	//Attributes
	
	@Autowired
	private OwnerService ownerService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private PetService petService;
	
	
	//Methods
	
	@GetMapping("/enroll")
	public ModelAndView enroll(@RequestParam int courseId) {
		ModelAndView result;
		
		
		try {
			
			this.ownerService.enroll(courseId);
			
			
			
			result =  new ModelAndView("redirect:/owner/listMyCourses");
			
			
			
		}catch(Throwable oops) {
			Collection<Course> courses =  this.courseService.getEnrollCourses();
			result = new ModelAndView("/course/enrollList");
			
			result.addObject("messageError", oops.getMessage());
			result.addObject("courses", courses);
		}
		
		return result;
		
	}
	
	
	@GetMapping("/listMyCourses")
	public ModelAndView listMyCourses() {
		
		Owner principal = this.ownerService.findByPrincipal();
		ModelAndView result = new ModelAndView("/owner/listMyCourses");
		Collection<Pet> pets = this.petService.findPetsByOwnerId(principal.getId());
		boolean isExotic = false;
		
		for(Pet p : pets) {
			if(p.getTipo().equals("EXOTIC")) {
				isExotic = true;
				result.addObject("ley", p.getLaw());
				break;
			}
		}
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		
		Collection<Integer> coursesId = this.courseService.getCoursesByOwner(principal.getId());
		Collection<Course> myCourses = new ArrayList<Course>();
		
		for(Integer c : coursesId) {
			myCourses.add(this.courseService.findById(c));
		}
		
		result.addObject("myCourses", myCourses);
		result.addObject("now", now);
		result.addObject("isExotic", isExotic);
		
		return result;
		
	}
	
	
}
