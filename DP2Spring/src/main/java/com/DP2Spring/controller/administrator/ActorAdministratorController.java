package com.DP2Spring.controller.administrator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.DP2Spring.model.Actor;
import com.DP2Spring.service.ActorService;

@Controller
@RequestMapping("/actor/administrator")
public class ActorAdministratorController {

	private static final Log log = LogFactory.getLog(ActorAdministratorController.class);
	
	@Autowired
	private ActorService actorService;
	
	
	public ActorAdministratorController() {
		super();
	}
	
	
	
}
