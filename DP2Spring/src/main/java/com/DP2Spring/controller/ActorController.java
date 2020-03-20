package com.DP2Spring.controller;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.DP2Spring.service.ActorService;
import com.DP2Spring.service.OwnerService;



@Controller
@RequestMapping("/actor")
public class ActorController {

	private static final Log log = LogFactory.getLog(ActorController.class);
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private OwnerService ownerService;
	

	

	
	public ActorController() {
		super();
	}
	

	
}
