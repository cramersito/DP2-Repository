package com.DP2Spring.controller.owner;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import com.DP2Spring.model.Owner;
import com.DP2Spring.model.Pet;


import com.DP2Spring.service.OwnerService;
import com.DP2Spring.service.PetService;

@Controller
@RequestMapping("/pet")
public class ClerkPetController {
	
	@Autowired
	private OwnerService ownerService;
	
	@Autowired
	private PetService petService;
	
	//Controllers
	
		@GetMapping("/my-pets")
		public ModelAndView create() {
			ModelAndView result = new ModelAndView("pet/my-pets");
			
			Owner principal=this.ownerService.findByPrincipal();
			
			Collection<Pet> pets = this.petService.findPetsByOwnerId(principal.getId());  
			
			result.addObject("pets", pets);
			
			return result;
			
		}
	
	
}
