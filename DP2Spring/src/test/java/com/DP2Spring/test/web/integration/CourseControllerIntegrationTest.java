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

import com.DP2Spring.controller.CourseController;
import com.DP2Spring.controller.TransportController;
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
public class CourseControllerIntegrationTest {
	
	
	private static final int CERTIFICATE_ID = 50;
	
	@Autowired
	private CourseController courseController;
	
	
	@Autowired
	private ClerkService clerkService;
	
	@Autowired
	private CertificateService certificateService;
	
	@Autowired
	private CourseService courseService;
	

	

	@WithMockUser(username = "clerk1", authorities = {"CLERK"})
    @Test
	void createCourse() throws Exception {
    	
		
		ModelAndView result=courseController.create(CERTIFICATE_ID);
		String view = result.getViewName();

		
		assertEquals(view,"course/create");
		
		
	}
    
	@WithMockUser(username = "owner1", authorities = {"OWNER"})
    @Test
	void enrollList() throws Exception {
		

		
		ModelAndView result=courseController.enrollList();
		String view = result.getViewName();

		
		assertEquals(view,"course/enrollList");			
	}
	
	@WithMockUser(username = "clerk1", authorities = {"CLERK"})
    @Test
	void saveCoursePos() throws Exception {
		
		Course c= this.courseService.create();
		
		c.setStartDate(new Date(System.currentTimeMillis()-1));
		c.setEndDate(new Date(System.currentTimeMillis()+1));
		
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		
		ModelAndView result=courseController.save(c, bindingResult);
		
		String view = result.getViewName();

		
		assertEquals(view,"redirect:/clerk/listCourses");			
	}
	
	@WithMockUser(username = "clerk1", authorities = {"CLERK"})
    @Test
	void saveCourseNeg() throws Exception {
		
		Course c= this.courseService.create();
		
		c.setStartDate(new Date(System.currentTimeMillis()-1));
		
		
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		bindingResult.reject("endDate", "Requied!");
		ModelAndView result=courseController.save(c, bindingResult);
		
		String view = result.getViewName();

		
		assertEquals(view,"/course/create");			
	}
    
	
	

}
