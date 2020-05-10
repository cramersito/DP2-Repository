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
import com.DP2Spring.controller.owner.OwnerController;
import com.DP2Spring.model.Certificate;
import com.DP2Spring.model.Owner;
import com.DP2Spring.service.CertificateService;
import com.DP2Spring.service.ClerkService;
import com.DP2Spring.service.CourseService;
import com.DP2Spring.service.OwnerService;
import com.DP2Spring.service.PetService;


@WebMvcTest(controllers = OwnerController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfig.class)
@OverrideAutoConfiguration(enabled=true)
class OwnerControllerTest {
	
	@MockBean
	private CourseService courseService;
	
	@MockBean
	private ClerkService clerkService;
	
	@MockBean
	private CertificateService certificateService;
	
	@MockBean
	private OwnerService ownerService;
	
	@MockBean
	private PetService petService;
	
	@Autowired
	private MockMvc mockMvc;
	
	
	
	@BeforeEach
	void setUp() {
		
		
		given(this.ownerService.findOne(60)).willReturn(new Owner());
		given(this.certificateService.findOne(50)).willReturn(new Certificate());
		
	}
	

	/*@WithMockUser(username = "owner1", authorities = {"OWNER"})
	@Test
	void testListMyCourses() throws Exception{
		
		mockMvc.perform(get("/owner/listMyCourses")).andExpect(status().isOk()).andExpect(view().name("/owner/listMyCourses"))
		.andExpect(model().attributeExists("myCourses"));
	}*/

	
	
	@WithMockUser(username = "owner1", authorities = {"OWNER"})
	@Test
	void testEnroll() throws Exception{
		
		mockMvc.perform(get("/owner/enroll?courseId=403")).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/owner/listMyCourses"));
	}
	
	
	
	
	
}
