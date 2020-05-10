package com.DP2Spring.test.web.integration;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.DP2Spring.model.Certificate;
import com.DP2Spring.service.CertificateService;
import com.DP2Spring.service.ClerkService;
import com.DP2Spring.service.CourseService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application.properties")
public class CertificateControllerE2ETest {



	@Autowired
	private MockMvc mockMvc;




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
		.andExpect(view().name("redirect:/course/create?certificateId=1"));


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
