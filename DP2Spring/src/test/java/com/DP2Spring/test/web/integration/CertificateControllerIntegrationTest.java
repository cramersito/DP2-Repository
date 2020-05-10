package com.DP2Spring.test.web.integration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.servlet.ModelAndView;

import com.DP2Spring.controller.CertificateController;
import com.DP2Spring.controller.CourseController;
import com.DP2Spring.controller.TransportController;
import com.DP2Spring.model.Certificate;
import com.DP2Spring.model.Course;
import com.DP2Spring.model.Owner;
import com.DP2Spring.model.Transport;
import com.DP2Spring.service.ActorService;
import com.DP2Spring.service.CertificateService;
import com.DP2Spring.service.ClerkService;
import com.DP2Spring.service.CourseService;
import com.DP2Spring.service.OwnerService;
import com.DP2Spring.service.PetService;
import com.DP2Spring.service.TransportService;
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CertificateControllerIntegrationTest {
	
	
	private static final int CERTIFICATE_ID = 50;
	
	@Autowired
	private CertificateController certificateController;
	
	
	@Autowired
	private ClerkService clerkService;
	
	@Autowired
	private CertificateService certificateService;
	
	@Autowired
	private CourseService courseService;
	

	

	@WithMockUser(username = "clerk1", authorities = {"CLERK"})
    @Test
	void createCertificate() throws Exception {
    	
		
		ModelAndView result=certificateController.create();
		String view = result.getViewName();

		
		assertEquals(view,"/certificate/create");
		
		
	}
    
    @Test
	void display() throws Exception {
		

		
		ModelAndView result=certificateController.display(CERTIFICATE_ID);
		String view = result.getViewName();

		
		assertEquals(view,"/certificate/display");			
	}
	
	@WithMockUser(username = "clerk1", authorities = {"CLERK"})
    @Test
	void saveCertficatePos() throws Exception {
		
		Certificate c= this.certificateService.create();
		
		c.setEntity("Gloden org");
		c.setDescription("El mejor certificado");
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		
		ModelAndView result=certificateController.save(c, bindingResult);
		
		String view = result.getViewName();

		
		assertEquals(view.split("=")[0],"redirect:/course/create?certificateId");			
	}
	
	@WithMockUser(username = "clerk1", authorities = {"CLERK"})
    @Test
	void saveCertificateNeg() throws Exception {
		
		Certificate c= this.certificateService.create();
		
		c.setEntity("Gloden org");
		
		
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		bindingResult.reject("description", "Requied!");
		ModelAndView result=this.certificateController.save(c, bindingResult);
		
		String view = result.getViewName();

		
		assertEquals(view,"/certificate/create");			
	}
    
	
	

}
