package com.DP2Spring.controller.clerk;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.DP2Spring.model.Course;
import com.DP2Spring.model.Transport;
import com.DP2Spring.service.ClerkService;
import com.DP2Spring.service.TransportService;

@Controller
@RequestMapping("/transport")
public class ClerkTransportController {

	
	
	@Autowired
	private ClerkService clerkService;
	
	@Autowired
	private TransportService transportService;
	
	//Controllers
	
	@GetMapping("/list")
	public ModelAndView create() {
		ModelAndView result = new ModelAndView("transport/list");
		
		Collection<Transport> transportesPendientes = this.transportService.transportsPending();  
		
		result.addObject("transportesPendientes", transportesPendientes);
		
		return result;
		
	}
	
	@GetMapping("/edit")
	public ModelAndView realizarTransporte(@RequestParam final int transportId) {
		ModelAndView result = new ModelAndView("transport/edit");
		
		Transport transport = this.transportService.findOne(transportId);  
		
		result.addObject("transport", transport);
		
		return result;
		
	}
}
