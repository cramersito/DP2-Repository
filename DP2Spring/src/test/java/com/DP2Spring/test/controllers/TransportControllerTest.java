package com.DP2Spring.test.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.DP2Spring.configuration.SecurityConfig;
import com.DP2Spring.controller.TransportController;
import com.DP2Spring.model.Transport;
import com.DP2Spring.service.ClerkService;
import com.DP2Spring.service.TransportService;
@WebMvcTest(controllers = TransportController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfig.class)
class TransportControllerTest {
	

	private static final int TEST_TRANSPORT_ID = 2;

	@MockBean
	private TransportService transportService;

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ClerkService clerkService;

	@BeforeEach
	void setup() {
		given(this.transportService.findOne(TEST_TRANSPORT_ID)).willReturn(new Transport());
	}

	@WithMockUser(value = "spring")
    @Test
void testProcessFindFormSuccess() throws Exception {
	

	mockMvc.perform(get("/transport/myTransport")).andExpect(status().isOk()).andExpect(view().name("transport/myTransport"));
}
        


}
