package com.DP2Spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.DP2Spring.model.Insurance;
import com.DP2Spring.service.InsuranceService;

@Controller
@RequestMapping("/insurance")
public class InsuranceController {

	@Autowired
	private InsuranceService insuranceService;
	
	
	@GetMapping("/display")
	public ModelAndView display(@RequestParam int insuranceId) {
		ModelAndView result = new ModelAndView("/insurance/display");
		
		Insurance ley = this.insuranceService.findOne(insuranceId);
		
		result.addObject("ley", ley);
		
		return result;
		
	}
	
}
