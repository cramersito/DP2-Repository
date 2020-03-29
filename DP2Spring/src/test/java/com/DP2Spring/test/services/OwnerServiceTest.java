package com.DP2Spring.test.services;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;

import com.DP2Spring.model.Course;
import com.DP2Spring.model.Owner;
import com.DP2Spring.service.CourseService;
import com.DP2Spring.service.OwnerService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class OwnerServiceTest {

	@Autowired
	private OwnerService ownerService;
	
	@Autowired
	private CourseService courseService;

	@Autowired
	private EntityManager entityManager;
	
	
	@Test
	@Transactional
	@WithMockUser(username = "owner1", authorities = {"OWNER"})
	void shouldEnroll() {
		
		Course course = this.courseService.findById(402);
		Owner principal = this.ownerService.findByPrincipal();
		
		assertThat(!course.getOwnersRegistered().contains(principal));
		
		try {
			
			this.ownerService.enroll(402);
			
		}catch(Throwable oops) {
			System.out.println("=================================================");
			System.out.println(oops.getMessage());
			System.out.println("=================================================");
			
		}
		
		entityManager.flush();

		assertThat(course.getOwnersRegistered().contains(principal));
	}
	
	@Test
	@Transactional
	@WithMockUser(username = "owner1", authorities = {"OWNER"})
	void shouldNotEnroll() {
		
		Course course = this.courseService.findById(400);
		Owner principal = this.ownerService.findByPrincipal();
		
		assertThat(course.getOwnersRegistered().contains(principal));
		
		try {
			
			this.ownerService.enroll(400);
			
		}catch(Throwable oops) {
			System.out.println("=================================================");
			System.out.println(oops.getMessage());
			System.out.println("=================================================");
			
			assertThat(oops.getMessage().equals("Ya estás inscrito a este curso."));
		
		}
		
		entityManager.flush();

		
	}



}
