package com.DP2Spring.test.controllers;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;

import com.DP2Spring.configuration.SecurityConfig;
import com.DP2Spring.controller.CourseController;
import com.DP2Spring.model.Certificate;
import com.DP2Spring.model.Clerk;
import com.DP2Spring.service.ClerkService;
import com.DP2Spring.service.CourseService;

@WebMvcTest(controllers = CourseController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfig.class)
class CourseControllerTest {
	
	@MockBean
	private CourseService courseService;
	
	@MockBean
	private ClerkService clerkService;
	
	@Autowired
	private MockMvc mockMvc;
	
	
	@BeforeEach
	void setUp() {
		Certificate certificate = new Certificate();
		certificate.setId(4);
		certificate.setDescription("init");
		certificate.setEntity("set");
		
		given(this.clerkService.findOne(200)).willReturn(new Clerk());
		
		
	}

}
