package com.DP2Spring.controller;

import java.util.Collection;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.DP2Spring.model.Clerk;

import com.DP2Spring.model.Owner;
import com.DP2Spring.model.Pet;
import com.DP2Spring.model.Transport;
import com.DP2Spring.service.ActorService;
import com.DP2Spring.service.ClerkService;
import com.DP2Spring.service.OwnerService;
import com.DP2Spring.service.PetService;
import com.DP2Spring.service.TransportService;

@Controller
@RequestMapping("/transport")
public class TransportController {

	
	
	@Autowired
	private ClerkService clerkService;
	
	@Autowired
	private TransportService transportService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private PetService petService;
	
	@Autowired
	private OwnerService ownerService;
	
	//Controllers
	
	@GetMapping("/list")
	public ModelAndView list() {
		ModelAndView result = new ModelAndView("transport/list");
		
		Collection<Transport> transportesPendientes = this.transportService.transportsPending();  
		
		result.addObject("transportesPendientes", transportesPendientes);
		
		return result;
		
	}
	@GetMapping("/myTransports")
	public ModelAndView myTransports() {
		ModelAndView result = new ModelAndView("transport/myTransports");
		
		Collection<Transport> transportes = this.transportService.transportsTransported();  
		
		result.addObject("transportes", transportes);
		
		return result;
		
	}
	
	// Create

	@GetMapping("/create")
	public ModelAndView create() {
		ModelAndView result;
		Transport transport;

		transport = this.transportService.create();

		result = this.createEditModelAndView(transport);

		return result;
	}
	
	
	
	@GetMapping("/edit")
	public ModelAndView realizarTransporte(@RequestParam final int transportId) {
		ModelAndView result = new ModelAndView("transport/edit");
		
		Transport transport = this.transportService.findOne(transportId);  
		
		if(this.actorService.isASpecificRole(this.actorService.findByPrincipal(), "CLERK")) {
			Clerk principal = this.clerkService.findByPrincipal();
			result.addObject("principal", principal);
			
		}
		
		result.addObject("transport", transport);
		
		return result;
		
	}
	
	

	@PostMapping(value = "/edit", params = "solicitarTransporte")
	public ModelAndView solicitarTransporte(@Valid Transport transport, final BindingResult binding, @PathParam("mascotas") String mascotas) {
		ModelAndView result;


		if (binding.hasErrors()) {
			result = this.createEditModelAndView(transport);
		} else {
			try {
				this.transportService.solicitarTransporte(transport,mascotas);
				result = new ModelAndView("redirect:/pet/my-pets");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(transport, "Ha ocurrido un error");
			}
		}

		return result;
	}
	
	@PostMapping(value = "/edit", params = "transportar")
	public ModelAndView transportar(@ModelAttribute("transport") @Valid Transport t, final BindingResult binding) {
		ModelAndView result;


		if (binding.hasErrors()) {
			result = this.createEditModelAndView(t);
		} else {
			try {
				this.transportService.transportar(t);
				result = new ModelAndView("redirect:/transport/list");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(t, "Ha ocurrido un error");
			}
		}

		return result;
	}
	
	
	// Ancillary methods

	protected ModelAndView createEditModelAndView(final Transport transport) {
		ModelAndView result;

		result = this.createEditModelAndView(transport, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Transport transport, final String messageCode) {
		ModelAndView result;
		
		
		
		result = new ModelAndView("transport/edit");
		result.addObject("transport", transport);
		result.addObject("messageCode", messageCode);
		

		
		if(this.actorService.isASpecificRole(this.actorService.findByPrincipal(), "OWNER")) {
			Owner owner = this.ownerService.findByPrincipal();
			Collection<Pet> mascotas= this.petService.findPetsByOwnerId(owner.getId());
			result.addObject("mascotas", mascotas);
			
		}
		
		
		return result;
	}
	
	
	
	
	
}
