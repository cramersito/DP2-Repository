package com.DP2Spring.test.web.integration;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.servlet.ModelAndView;

import com.DP2Spring.controller.TransportController;
import com.DP2Spring.model.Transport;
import com.DP2Spring.service.ActorService;
import com.DP2Spring.service.ClerkService;
import com.DP2Spring.service.OwnerService;
import com.DP2Spring.service.PetService;
import com.DP2Spring.service.TransportService;
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransportControllerIntegrationTest {
	
	
	private static final int TEST_OWNER_ID = 60;
	private static final int TEST_CLERK_ID = 200;
	private static final String TEST_PETS_IDS = "80,81";
	private static final int TEST_TRANSPORT_ID = 503;
	
	@Autowired
	private TransportController transportController;
	
	
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
	
	@WithMockUser(username = "clerk1", authorities = {"CLERK"})
    @Test
	void list() throws Exception {
		
		
		ModelAndView result=transportController.list();
		String view = result.getViewName();

		
		assertEquals(view,"transport/list");

	}
	@WithMockUser(username = "owner1", authorities = {"OWNER"})
    @Test
	void myTransports() throws Exception {
	
		
		
		ModelAndView result=transportController.myTransports();
		String view = result.getViewName();

		
		assertEquals(view,"transport/myTransports");

	}
	@WithMockUser(username = "clerk1", authorities = {"CLERK"})
    @Test
	void transporteds() throws Exception {
	
		
		
		ModelAndView result=transportController.transporteds();
		String view = result.getViewName();

		
		assertEquals(view,"transport/transporteds");

	}
	
	@WithMockUser(username = "owner1", authorities = {"OWNER"})
    @Test
	void createTransport() throws Exception {
    	
		
		ModelAndView result=transportController.create();
		String view = result.getViewName();

		
		assertEquals(view,"transport/edit");
		
		
	}
    
	@WithMockUser(username = "owner1", authorities = {"OWNER"})
    @Test
	void testSolicitarTransporteFormSuccess() throws Exception {
    	Transport newT=this.transportService.create();
    	newT.setOrigin("Carmona");
    	newT.setDestination("Lebrija");
    	
    	BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
    	ModelAndView result= this.transportController.solicitarTransporte(newT, bindingResult, TEST_PETS_IDS);
    	String view = result.getViewName();
    	
		assertEquals(view,"redirect:/pet/my-pets");				
	}
    
	
	@WithMockUser(username = "owner1", authorities = {"OWNER"})
    @Test
	void testSolicitarTransporteFormHasErrors() throws Exception {
    	Transport newT=this.transportService.create();
    	newT.setOrigin("Carmona");
    	
    	
    	BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
    	bindingResult.reject("destination", "Requied!");
    	ModelAndView result= this.transportController.solicitarTransporte(newT, bindingResult, TEST_PETS_IDS);
    	String view = result.getViewName();
    	
		assertEquals(view,"transport/edit");				
	}
    
	
	@WithMockUser(username = "clerk1", authorities = {"CLERK"})
    @Test
	void testTransportarFormHasErrors() throws Exception {
    	Transport t=this.transportService.findOne(TEST_TRANSPORT_ID);
    	
    	 	
    	BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
    	
    	ModelAndView result= this.transportController.transportar(t, bindingResult);
    	String view = result.getViewName();
    	
		assertEquals(view,"transport/edit");				
	}
	
	@WithMockUser(username = "clerk1", authorities = {"CLERK"})
    @Test
	void testTransportarFormSuccess() throws Exception {
    	Transport t=this.transportService.findOne(TEST_TRANSPORT_ID);
    	    
    	t.setCompany("DHL");
    	t.setStatus("TRANSPORTED");
    	
    	BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
    	
    	ModelAndView result= this.transportController.transportar(t, bindingResult);
    	String view = result.getViewName();
    	
		assertEquals(view,"redirect:/transport/list");					
	}
    
	

}
