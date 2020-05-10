package com.DP2Spring.test.web.integration;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application.properties")
public class CourseControllerE2ETest {



	@Autowired
	private MockMvc mockMvc;





	@WithMockUser(username = "clerk1", authorities = {"CLERK"})
	@Test
	void testCreateCourse() throws Exception{
		mockMvc.perform(get("/course/create?certificateId=56")).andExpect(status().isOk())
		.andExpect(view().name("course/create")).andExpect(model().attributeExists("course"));


	}

	@WithMockUser(username = "clerk1", authorities = {"CLERK"})
	@Test
	void testCreateCourseSuccess() throws Exception{

		mockMvc.perform(post("/course/createCourse")
				.with(csrf())
				.param("price", "10.99")
				.param("description", "Description")
				.param("startDate", "12/09/2020")
				.param("endDate", "12/09/2021")
				.param("clerk", "200")
				.param("certificate", "56"))
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
