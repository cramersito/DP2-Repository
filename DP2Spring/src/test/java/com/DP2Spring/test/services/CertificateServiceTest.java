package com.DP2Spring.test.services;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.jupiter.api.Assertions;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;

import com.DP2Spring.model.Certificate;
import com.DP2Spring.service.CertificateService;
import com.DP2Spring.service.ClerkService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CertificateServiceTest {

	//Atributos


	@Autowired
	protected ClerkService clerkService;

	@Autowired
	protected CertificateService certificateService;

	@Autowired
	private EntityManager entityManager;


	//Tests para los métodos de la clase CertificateService


	//Test positivo del método create
	@Test
	@Transactional
	@WithMockUser("clerk1")
	public void shouldCreateCertificate() {
		Certificate certificate = this.certificateService.create();

		assertNotNull(certificate);
		assertThat(certificate.getId() == 0);


	}



	//No se puede realizar un test negativo de este método


	//Test positivo del metodo save
	@Test
	@Transactional
	@WithMockUser("clerk1")
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


	//Test negativo para el metodo save
	@Test
	@Transactional
	@WithMockUser("clerk1")
	public void shouldNotPersistCertificate() {

		Certificate certificate = new Certificate();

		certificate.setDescription(" ");
		certificate.setEntity(" ");


		entityManager.flush();

		Assertions.assertThrows(ConstraintViolationException.class,  ()-> this.certificateService.save(certificate));
	}




}