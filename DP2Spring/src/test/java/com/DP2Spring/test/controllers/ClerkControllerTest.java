package com.DP2Spring.test.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.DP2Spring.configuration.SecurityConfig;
import com.DP2Spring.controller.clerk.ClerkController;
import com.DP2Spring.model.Certificate;
import com.DP2Spring.model.Clerk;
import com.DP2Spring.service.CertificateService;
import com.DP2Spring.service.ClerkService;
import com.DP2Spring.service.CourseService;


@WebMvcTest(controllers = ClerkController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfig.class)
@OverrideAutoConfiguration(enabled=true)
class ClerkControllerTest {
	
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
		
		
		given(this.clerkService.findByPrincipal()).willReturn(new Clerk());
		given(this.certificateService.findOne(50)).willReturn(new Certificate());
		
	}
	
	@WithMockUser(username = "clerk1", authorities = {"CLERK"})
	@Test
	void testListCourses() throws Exception{
		
		mockMvc.perform(get("/clerk/listCourses")).andExpect(status().isOk()).andExpect(view().name("/clerk/listCourses")).
		andExpect(model().attributeExists("myCourses"));
	}
	
	
	
	
	
}
