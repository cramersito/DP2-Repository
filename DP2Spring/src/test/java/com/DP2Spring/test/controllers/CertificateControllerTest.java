package com.DP2Spring.test.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.DP2Spring.controller.CertificateController;
import com.DP2Spring.model.Certificate;
import com.DP2Spring.service.CertificateService;
import com.DP2Spring.service.ClerkService;
import com.DP2Spring.service.CourseService;


@WebMvcTest(controllers = CertificateController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfig.class)
@OverrideAutoConfiguration(enabled=true)
class CertificateControllerTest {
	
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
		
		
	
		given(this.certificateService.findOne(50)).willReturn(new Certificate());
		
	}
	
	@WithMockUser(username = "clerk1", authorities = {"CLERK"})
	@Test
	void testCreateCertificateForm() throws Exception{
		
		mockMvc.perform(get("/certificate/create")).andExpect(status().isOk()).andExpect(view().name("/certificate/create")).
		andExpect(model().attributeExists("certificate"));
	}
	
	@WithMockUser(username = "clerk1", authorities = {"CLERK"})
	@Test
	void testCreateCertificateSuccess() throws Exception{
		
		mockMvc.perform(post("/certificate/create")
				.with(csrf())
				.param("entity", "sample")
				.param("description", "Description"))
	
	.andExpect(status().is3xxRedirection())
	
	//Es cero el id ya que es el que se acaba de crear
	.andExpect(view().name("redirect:/course/create?certificateId=0"));
		
	
	}
	
	
	@WithMockUser(username = "clerk1", authorities = {"CLERK"})
	@Test
	void testCreateCertificateNoSuccess() throws Exception{
		
		mockMvc.perform(post("/certificate/create")
				.with(csrf())
				.param("description", "Description"))
		.andExpect(model().attributeHasErrors("certificate"))
	.andExpect(status().isOk())
	.andExpect(view().name("/certificate/create"));

		
	
	}
	
	
	
	
}
