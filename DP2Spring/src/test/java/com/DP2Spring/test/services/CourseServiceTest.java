package com.DP2Spring.test.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.DP2Spring.model.Certificate;
import com.DP2Spring.model.Clerk;
import com.DP2Spring.model.Course;
import com.DP2Spring.service.CertificateService;
import com.DP2Spring.service.ClerkService;
import com.DP2Spring.service.CourseService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CourseServiceTest {

	@Autowired
	protected CourseService courseService;


	@Autowired
	protected ClerkService clerkService;

	//Probably not necessary this collaborator
	@Autowired
	protected CertificateService certificateService;

	@Autowired
	private EntityManager entityManager;


	//First testing getters used in big methods
	
	@Test
	@WithMockUser("owner1")
	public void getEnrollCoursesPos() {
		Collection<Course> result = this.courseService.getEnrollCourses();
		assertTrue(result.size() == 2);

	}

	

	@Test
	@WithMockUser("owner1")
	void getEnrollCoursesNeg() {
		Collection<Course> result = this.courseService.getEnrollCourses();
		assertTrue(result.size()!=1);

	}
	
	@Test
	void shouldFindCourse() {
		Course course = this.courseService.findById(400);
		assertThat(course.getDescription().equals("Curso de principantes"));
		assertThat(course.getPrice() == 10.99);
	}
	
	
	@Test
	@WithMockUser("clerk1")
	public void getCoursesByClerk() {
		Collection<Course> result = this.courseService.getCoursesByClerk(201);
		assertTrue(result.size() == 3);

	}
	
	@Test
	void shouldFindAll() {
		Collection<Course> all = this.courseService.findAll();
		assertTrue(all.size() == 6);
	}

	//Once this is tested lets test certificate service because its used in save course
	


	//Positive case course saved well
	@Test
	@Transactional
	public void shouldPersistCourse() {
		Clerk clerk1 = this.clerkService.findOne(200);
		Course course = new Course();
		Certificate certificate = new Certificate();
		certificate.setDescription("Test cert");
		certificate.setEntity("Testt");
		

		course.setClerk(clerk1);

		course.setDescription("Test positivo");
		course.setPrice(12.0);
		course.setStartDate(new Date(System.currentTimeMillis()-1));
		course.setEndDate(new Date(System.currentTimeMillis()+1));

		assertThat(course.getOwnersRegistered()).isNullOrEmpty();

		try {

			//Check first certificate save

			this.certificateService.save(certificate);

			course.setCertificate(certificate);

			this.courseService.save(course);

		}catch(Throwable oops) {
			System.out.println("=================================================");
			System.out.println(oops.getMessage());
			System.out.println("=================================================");
		}



		entityManager.flush();

		assertThat(course.getId()).isNotNull();

	}
	
	//In this case shouldn't persist because of dates (Start date must be before end date)
	
	@Test
	@Transactional
	public void shouldNotPersistCourse() {
		Clerk clerk1 = this.clerkService.findOne(200);
		Course course = new Course();
		Certificate certificate = new Certificate();
		certificate.setDescription("Test cert");
		certificate.setEntity("Testt");
		

		course.setClerk(clerk1);

		course.setDescription("Test negativo");
		course.setPrice(12.0);
		course.setStartDate(new Date(System.currentTimeMillis()-1));
		course.setEndDate(new Date(System.currentTimeMillis()-1));

		assertThat(course.getOwnersRegistered()).isNullOrEmpty();

		try {

			//Check first certificate save

			this.certificateService.save(certificate);

			course.setCertificate(certificate);

			this.courseService.save(course);

		}catch(Throwable oops) {
			System.out.println("=================================================");
			System.out.println(oops.getMessage());
			System.out.println("=================================================");
			
			//Checking exception is what expected
			assertThat(oops.getMessage().equals("La fecha de inicio debe ser anterior a la fecha de fin."));
		}



		entityManager.flush();
		
		//Id == 0 means that doesn't get persisted
		assertThat(course.getId() == 0);
	

	}
	
	

}