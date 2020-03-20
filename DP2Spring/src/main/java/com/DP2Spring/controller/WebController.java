package com.DP2Spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

	@GetMapping(value="/owner")
	public String owner(){
		return "<h1>Owner permission</h1>";
	}

	@RequestMapping(value="/admin")
	public String admin(){
		return "admin";
	}
	
	@RequestMapping(value="/clerk")
	public String clerk(){
		return "clerk";
	}
	
	
	
	

	@RequestMapping(value="/403")
	public String Error403(){
		return "403";
	}
}