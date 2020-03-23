package com.DP2Spring.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.DP2Spring.model.Certificate;
import com.DP2Spring.service.CertificateService;

@Controller
@RequestMapping("/certificate")
public class CertificateController {
	
	//Attributes
	
	@Autowired
	CertificateService certificateService;
	
	
	//Methods
	
	@GetMapping("/create")
	public ModelAndView create() {
		ModelAndView result = new ModelAndView("/certificate/create");
		
		Certificate certificate = this.certificateService.create();
		
		result.addObject("certificate", certificate);
		
		return result;
	}
	
	
	@RequestMapping(value = "/create", params = "save")
	public ModelAndView save(@Valid Certificate certificate, BindingResult binding) {
		
		ModelAndView result;
		
		if(binding.hasErrors()) {
			
			result = new ModelAndView("/certificate/create");
			
			
		}else {
			
			try {
				
				this.certificateService.save(certificate);
				
				result = new ModelAndView("redirect:/course/create?certificateId="+certificate.getId());
				
				result.addObject("certificate", certificate);
			}catch(Throwable oops) {
				result = new ModelAndView("/certificate/create");
				
				result.addObject("error", oops.getMessage());
			}
			
		}
		
		return result;
	}

}
