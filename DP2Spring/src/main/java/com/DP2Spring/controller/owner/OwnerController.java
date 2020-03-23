package com.DP2Spring.controller.owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.DP2Spring.service.OwnerService;

@Controller
@RequestMapping("/owner")
public class OwnerController {

	//Attributes
	
	@Autowired
	private OwnerService ownerService;
	
	
	//Methods
	
	@GetMapping("/enroll")
	public ModelAndView enroll(@RequestParam int courseId) {
		ModelAndView result;
		
		try {
			
			this.ownerService.enroll(courseId);
			
			result =  new ModelAndView("redirect:/owner/listMyCourses");
			
		}catch(Throwable oops) {
			result = new ModelAndView("/course/enrollList");
			
			result.addObject("messageError", oops.getMessage());
		}
		
		return result;
		
	}
}
