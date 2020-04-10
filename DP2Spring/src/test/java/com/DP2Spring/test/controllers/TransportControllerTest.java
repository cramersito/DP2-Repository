package com.DP2Spring.test.controllers;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.DP2Spring.configuration.SecurityConfig;
import com.DP2Spring.controller.CourseController;
import com.DP2Spring.controller.TransportController;
import com.DP2Spring.model.Certificate;
import com.DP2Spring.model.Clerk;
import com.DP2Spring.model.Course;
import com.DP2Spring.model.Owner;
import com.DP2Spring.model.Pet;
import com.DP2Spring.model.Transport;
import com.DP2Spring.service.ActorService;
import com.DP2Spring.service.CertificateService;
import com.DP2Spring.service.ClerkService;
import com.DP2Spring.service.CourseService;
import com.DP2Spring.service.OwnerService;
import com.DP2Spring.service.PetService;
import com.DP2Spring.service.TransportService;

@WebMvcTest(controllers = TransportController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfig.class)
@OverrideAutoConfiguration(enabled=true)
class TransportControllerTest {
	

	private static final int TEST_TRANSPORT_ID = 500;
	private static final int TEST_PET_ID = 80;
	private static final int TEST_OWNER_ID = 60;
	private static final int TEST_CLERK_ID = 200;
	@MockBean
	private TransportService transportService;

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ClerkService clerkService;
	
	@MockBean
	private ActorService actorService;
	
	@MockBean
	private PetService petService;
	
	@MockBean
	private OwnerService ownerService;
	
	private Transport tr;

	@BeforeEach
	void setup() {
		given(this.transportService.findOne(TEST_TRANSPORT_ID)).willReturn(new Transport());
		given(this.petService.myPet(TEST_PET_ID)).willReturn(new Pet());
		given(this.ownerService.findOne(TEST_OWNER_ID)).willReturn(new Owner());
		given(this.clerkService.findOne(TEST_CLERK_ID)).willReturn(new Clerk());
		
		tr = new Transport();
		tr.setId(TEST_TRANSPORT_ID);
		tr.setOrigin("Madrid");
		tr.setCompany(null);
		tr.setDestination("Rota");
		tr.setStatus("PENDING");
		given(this.transportService.findOne(TEST_TRANSPORT_ID)).willReturn(tr);
		
		
	}

	@WithMockUser(username = "clerk1", authorities = {"CLERK"})
    @Test
    void listadoTransportesPendientes() throws Exception {
	
	mockMvc.perform(get("/transport/list")).andExpect(status().isOk()).andExpect(view().name("transport/list"));
	
	
	verify(transportService).transportsPending();
	
}
	
	@WithMockUser(username = "owner1", authorities = {"OWNER"})
    @Test
    void transportesRealizados() throws Exception {
	
	mockMvc.perform(get("/transport/myTransports"))
	.andExpect(status().isOk())
	.andExpect(view().name("transport/myTransports"));
	
	verify(transportService).transportsTransported();
	
}
	
	@WithMockUser(username = "owner1", authorities = {"OWNER"})
    @Test
    void controladorErroneo() throws Exception {
	
	mockMvc.perform(get("/transport/listTEST"))
	.andExpect(status()
			.is4xxClientError());
	
}
	

	@WithMockUser(username = "clerk1", authorities = {"CLERK"})
	@Test
	void editViewTransport() throws Exception{
		mockMvc.perform(get("/transport/edit?transportId="+TEST_TRANSPORT_ID))
		.andExpect(status().isOk())
		.andExpect(view().name("transport/edit"))
		.andExpect(model().attributeExists("transport"));
		
		
	}
	
	
	@WithMockUser(username = "clerk1", authorities = {"CLERK"})
	@Test
	void transportarPos() throws Exception {
		mockMvc.perform(post("/transport/edit/transportar")
				.with(csrf())
				.param("id", Integer.toString(TEST_TRANSPORT_ID))
				.param("status", "TRANSPORTED")
				.param("origin", "Rota")
				.param("destination", "Sevilla")
				.param("company", "SEUR"))		
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/transport/list"));
		
		
	}
	
	@WithMockUser(username = "clerk1", authorities = {"CLERK"})
	@Test
	void transportarPosWithSetUp() throws Exception {
		mockMvc.perform(post("/transport/edit/transportar")
				.with(csrf())
				.param("id", Integer.toString(tr.getId()))
				.param("status", "TRANSPORTED")
				.param("origin", tr.getOrigin())
				.param("destination", tr.getDestination())
				.param("company", "SEUR"))		
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/transport/list"));
		
		
	}
	
	@WithMockUser(username = "clerk1", authorities = {"CLERK"})
	@Test
	void transportarNeg() throws Exception {
		mockMvc.perform(post("/transport/edit/transportar")
				.with(csrf())
				.param("id", Integer.toString(TEST_TRANSPORT_ID))
				.param("origin", "Rota") //Envio formulario sin la compañia y sin estado
				.param("destination", "Sevilla"))		
				.andExpect(status().isOk())
				.andExpect(view().name("transport/edit"));
		
		
		
		
	}
	
	@WithMockUser(username = "owner1", authorities = {"OWNER"})
	@Test
	void solicitarTransportePos() throws Exception {

		
		mockMvc.perform(post("/transport/edit/solicitarTransporte")
				.with(csrf())
				.param("origin", "Rota")
				.param("status", "PENDING")
				.param("destination", "Sevilla")
				.param("mascotas", Integer.toString(TEST_PET_ID)))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/pet/my-pets"));
		
		
		
		
	}
	

        


}
