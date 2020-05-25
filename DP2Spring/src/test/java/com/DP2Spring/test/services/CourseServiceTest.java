package com.DP2Spring.test.services;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
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
import com.DP2Spring.model.Course;
import com.DP2Spring.model.Insurance;
import com.DP2Spring.model.Pet;
import com.DP2Spring.service.CertificateService;
import com.DP2Spring.service.ClerkService;
import com.DP2Spring.service.CourseService;
import com.DP2Spring.service.PetService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CourseServiceTest {

	//Atributos

	@Autowired
	protected CourseService courseService;


	@Autowired
	protected ClerkService clerkService;

	@Autowired
	protected PetService petService;


	@Autowired
	protected CertificateService certificateService;

	@Autowired
	private EntityManager entityManager;





	//Tests para los métodos de la clase CourseService



	//Test positivo para el método coursePassed
	@Test
	@Transactional
	@WithMockUser("owner1")
	void shouldCoursePassed() {
		assertThat(this.courseService.CoursePassed());


	}

	//Test negativo para el método coursePassed
	@Test
	@Transactional
	@WithMockUser("clerk1")
	void shouldNotCoursePassed() {

		Assertions.assertThrows(IllegalArgumentException.class,  ()-> this.courseService.getEnrollCourses());

	}

	//Test para el método getEnrollCourses
	@Test
	@Transactional
	@WithMockUser("owner1")
	public void getEnrollCoursesPos() {
		Collection<Course> result = this.courseService.getEnrollCourses();
		assertTrue(!result.isEmpty());

	}


	//Test negativo para el método getEnrollCourses 
	@Test
	@Transactional
	@WithMockUser("clerk1")
	public void getEnrollCoursesNeg() {

		Assertions.assertThrows(IllegalArgumentException.class,  ()-> this.courseService.getEnrollCourses());

	}



	//Test positivo del método shouldLaw
	@Test
	@Transactional
	@WithMockUser("owner1")
	void shouldLaw() {
		Collection<Pet> pets = this.petService.findAll();

		Insurance law = this.courseService.getLaw(pets);

		assertThat(law != null);
	}

	//Test negativo del método shouldLaw
	@Test
	@Transactional
	@WithMockUser("clerk1")
	void shouldNotLaw() {


		Assertions.assertThrows(NullPointerException.class,  ()-> this.courseService.getLaw(null));

	}


	//Test positivo del método getCoursesByClerk
	@Test
	@Transactional
	@WithMockUser("clerk1")
	public void getCoursesByClerk() {
		Collection<Course> result = this.courseService.getCoursesByClerk(201);
		assertTrue(result.size() == 3);

	}

	//Test negativo del método getCoursesByClerk
	@Test
	@Transactional
	@WithMockUser("clerk1")
	public void getCoursesByClerkNeg() {
		Collection<Course> result = this.courseService.getCoursesByClerk(599);

		assertThat(result.isEmpty());
	}


	//Test positivo del metodo save
	@Test
	@Transactional
	@WithMockUser("clerk1")

	public void shouldPersistCourse() {

		Course course = new Course();
		Certificate certificate = new Certificate();
		certificate.setDescription("Test cert");
		certificate.setEntity("Testt");




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


	//Test negativo del metodo save. Fecha inicio posterior a fecha fin.
	@Test
	@Transactional
	@WithMockUser("clerk1")
	public void shouldNotPersistCourse() {
		Course course = new Course();
		Certificate certificate = new Certificate();
		certificate.setDescription("Test cert");
		certificate.setEntity("Testt");




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

	//Test positivo del metodo create
	@Test
	@Transactional
	@WithMockUser("clerk1")
	public void shouldCreateCourse() {
		Course course = this.courseService.create();

		assertNotNull(course);

	}


	//Test negativo del metodo create
	@Test
	@Transactional
	@WithMockUser("owner1")
	public void shouldNotCreateCourse() {
		Assertions.assertThrows(IllegalArgumentException.class,  ()-> this.courseService.create());

	}
	




}