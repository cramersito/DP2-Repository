package com.DP2Spring.test.services;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.DP2Spring.model.Certificate;
import com.DP2Spring.model.Course;
import com.DP2Spring.service.CertificateService;
import com.DP2Spring.service.ClerkService;
import com.DP2Spring.service.CourseService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CertificateServiceTest {

	@Autowired
	private CourseService courseService;

	@Autowired
	protected ClerkService clerkService;

	@Autowired
	protected CertificateService certificateService;

	@Autowired
	private EntityManager entityManager;


	//Test getters and setters
	
	@Test
	void findOne() {
		Certificate certificate = this.certificateService.findOne(50);
		
		assertThat(certificate.getDescription().equals("Certificado para curso de principiantes"));
		assertThat(certificate.getEntity().equals("Entity 1"));
	}
	
	@Test
	void findAll() {
		Collection<Certificate> certificates = this.certificateService.findAll();
		Collection<Course> courses = this.courseService.findAll();
		
		//Mapped one to one so must be same samples of both
		assertTrue(courses.size() == certificates.size());
	}
	
	
	@Test
	@Transactional
	public void shouldPersistCertificate() {
		
		Certificate certificate = new Certificate();
		
		certificate.setDescription("Test certificate");
		certificate.setEntity("Entity for test certificate");
		
		try {
			
			this.certificateService.save(certificate);
			
		}catch(Throwable oops) {
			System.out.println("=================================================");
			System.out.println(oops.getMessage());
			System.out.println("=================================================");
		}
		
		entityManager.flush();

		assertThat(certificate.getId()).isNotNull();
	}
	
	
}