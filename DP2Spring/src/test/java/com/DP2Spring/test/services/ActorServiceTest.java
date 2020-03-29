package com.DP2Spring.test.services;

import static org.assertj.core.api.Assertions.assertThat;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;

import com.DP2Spring.model.Actor;
import com.DP2Spring.model.Administrator;
import com.DP2Spring.model.Clerk;
import com.DP2Spring.model.Owner;
import com.DP2Spring.service.ActorService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ActorServiceTest {

	@Autowired
	private ActorService actorService;
	
	
	@Test
	@Transactional
	@WithMockUser(username = "admin1", authorities = {"ADMIN"})
	void findOneAdmin() {
		Actor actor = this.actorService.findOne(100);
		
		assertThat(actor instanceof Administrator);
	}
	
	
	@Test
	@Transactional
	@WithMockUser(username = "clerk1", authorities = {"CLERK"})
	void findOneClerk() {
		Actor actor = this.actorService.findOne(200);
		
		assertThat(actor instanceof Clerk);
	}
	
	
	@Test
	@Transactional
	@WithMockUser(username = "owner1", authorities = {"OWNER"})
	void findOneOwner() {
		Actor actor = this.actorService.findOne(60);
		
		assertThat(actor instanceof Owner);
	}
}
