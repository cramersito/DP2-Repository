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
import com.DP2Spring.controller.CourseController;
import com.DP2Spring.model.Certificate;
import com.DP2Spring.model.Clerk;
import com.DP2Spring.model.Course;
import com.DP2Spring.service.CertificateService;
import com.DP2Spring.service.ClerkService;
import com.DP2Spring.service.CourseService;


@WebMvcTest(controllers = CourseController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfig.class)
@OverrideAutoConfiguration(enabled=true)
class CourseControllerTest {

	@MockBean
	private CourseService courseService;

	@MockBean
	private ClerkService clerkService;

	@MockBean
	private CertificateService certificateService;

	@Autowired
	private MockMvc mockMvc;


	private Course c;

	@BeforeEach
	void setUp() {



	}

	@WithMockUser(username = "clerk1", authorities = {"CLERK"})
	@Test
	void testCreateCourse() throws Exception{
		mockMvc.perform(get("/course/create?certificateId=50")).andExpect(status().isOk())
		.andExpect(view().name("course/create")).andExpect(model().attributeExists("course"));


	}

	@WithMockUser(username = "clerk1", authorities = {"CLERK"})
	@Test
	void testCreateCourseSuccess() throws Exception{

		mockMvc.perform(post("/course/createCourse")
				.with(csrf())
				.param("price", "10.99")
				.param("description", "Description")
				.param("startDate", "22/09/2015")
				.param("endDate", "22/09/2016")
				.param("certificate", "50")
				.param("clerk", "200"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/clerk/listCourses"));


	}


	//Certificado nulo
	@WithMockUser(username = "clerk1", authorities = {"CLERK"})
	@Test
	void testCreateCourseNoSuccess() throws Exception{

		mockMvc.perform(post("/course/createCourse")
				.with(csrf())
				.param("price", "10.99")
				.param("description", "Description")
				.param("startDate", "22/09/2015")
				.param("endDate", "22/09/2016")
				.param("clerk", "200"))
		.andExpect(model().attributeHasErrors("course"))
		.andExpect(model().attributeHasFieldErrors("course", "certificate"))
		.andExpect(status().isOk())
		.andExpect(view().name("/course/create"));




	}


	//Mal formato en endDate
	@WithMockUser(username = "clerk1", authorities = {"CLERK"})
	@Test
	void testCreateCourseNoSuccess2() throws Exception{

		mockMvc.perform(post("/course/createCourse")
				.with(csrf())
				.param("price", "10.99")
				.param("description", "Description")
				.param("startDate", "22/09/2015")
				.param("endDate", "22-09-2016")
				.param("clerk", "200")
				.param("certificate", "50"))
		.andExpect(model().attributeHasErrors("course"))
		.andExpect(model().attributeHasFieldErrors("course", "endDate"))
		.andExpect(status().isOk())
		.andExpect(view().name("/course/create"));




	}

	//Clerk nulo
	@WithMockUser(username = "clerk1", authorities = {"CLERK"})
	@Test
	void testCreateCourseNoSuccess3() throws Exception{

		mockMvc.perform(post("/course/createCourse")
				.with(csrf())
				.param("price", "10.99")
				.param("description", "Description")
				.param("startDate", "22/09/2018")
				.param("endDate", "22/09/2016")
				.param("certificate", "50"))
		.andExpect(model().attributeHasErrors("course"))
		.andExpect(model().attributeHasFieldErrors("course", "clerk"))
		.andExpect(status().isOk())
		.andExpect(view().name("/course/create"));




	}

	@WithMockUser(username = "owner1", authorities = {"OWNER"})
	@Test
	void testEnroll() throws Exception{
		mockMvc.perform(get("/course/enroll/list")).andExpect(status().isOk())
		.andExpect(view().name("course/enrollList")).andExpect(model().attributeExists("courses"));


	}

}
