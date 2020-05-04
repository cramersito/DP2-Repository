package com.DP2Spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.DP2Spring.service.ActorService;

@Controller
@RequestMapping("/")
public class HomeController{

	@Autowired
	private ActorService actorService;

	@GetMapping("home")
	public ModelAndView auth() {
	
		
		
		ModelAndView mav = new ModelAndView("miscellaneous/home");
	
		
		return mav;

	}
	
	
	@GetMapping("login")
	public ModelAndView login() {
		
		
		
		ModelAndView mav = new ModelAndView("login");
	
		
		
		
		return mav;
		
		
	}
	
	
	@GetMapping("logout")
	public ModelAndView logout() {
		
	
		
		ModelAndView mav = new ModelAndView("sign");
		
		
	
		
		
		return mav;
	}
	
	
	

}

