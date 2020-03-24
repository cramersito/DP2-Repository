package com.DP2Spring.test.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.test.web.servlet.MockMvc;


import com.DP2Spring.configuration.SecurityConfig;
import com.DP2Spring.controller.CourseController;
import com.DP2Spring.model.Certificate;
import com.DP2Spring.model.Clerk;
import com.DP2Spring.model.Course;
import com.DP2Spring.service.CertificateService;
import com.DP2Spring.service.ClerkService;
import com.DP2Spring.service.CourseService;


import static org.mockito.BDDMockito.given;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CourseController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfig.class)
class CourseControllerTest {
	
	@MockBean
	private CourseService courseService;
	
	@MockBean
	private ClerkService clerkService;
	
	@MockBean
	private CertificateService certificateService;
	
	@Autowired
	private MockMvc mockMvc;
	
	
	@BeforeEach
	void setUp() {
		
		given(this.clerkService.findOne(200)).willReturn(new Clerk());
		given(this.courseService.findById(400)).willReturn(new Course());
		given(this.certificateService.findOne(50)).willReturn(new Certificate());
		
		
	}
	
	@WithMockUser(username = "clerk1", authorities = {"CLERK"})
	@Test
	void testCreateCourse() throws Exception{
		mockMvc.perform(get("/course/create")).andExpect(status().isOk())
		.andExpect(view().name("/course/create")).andExpect(model().attributeExists("course"));
		
		
	}
	
	

}
