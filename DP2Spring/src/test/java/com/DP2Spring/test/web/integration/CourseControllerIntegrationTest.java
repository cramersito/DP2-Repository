package com.DP2Spring.test.web.integration;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.servlet.ModelAndView;

import com.DP2Spring.controller.CourseController;
import com.DP2Spring.model.Certificate;
import com.DP2Spring.model.Course;
import com.DP2Spring.service.CertificateService;
import com.DP2Spring.service.ClerkService;
import com.DP2Spring.service.CourseService;
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
		Certificate cer = this.certificateService.create();
		
		cer.setDescription("sample");
		cer.setEntity("sample");
		
		this.certificateService.save(cer);
		
		c.setStartDate(new Date(System.currentTimeMillis()+200000));
		c.setEndDate(new Date(System.currentTimeMillis()+2000000000));
		c.setCertificate(cer);
		c.setDescription("sample");
		
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
