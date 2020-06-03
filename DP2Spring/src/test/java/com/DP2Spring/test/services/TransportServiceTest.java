package com.DP2Spring.test.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Collection;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import org.aspectj.lang.annotation.Before;
import org.aspectj.util.IStructureModel;
import org.hibernate.hql.internal.ast.tree.ExpectedTypeAwareNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.DP2Spring.model.Transport;
import com.DP2Spring.service.ClerkService;

import com.DP2Spring.service.OwnerService;
import com.DP2Spring.service.TransportService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TransportServiceTest  extends ValidatorTests{

	
	
	@Autowired
	protected TransportService transportService;
	
	
	@Autowired
	protected ClerkService clerkService;
	
	@Autowired
	protected OwnerService ownerService;
	
	@Autowired
	private EntityManager entityManager;

	

	//VALIDACION (AVANZADO)
	
	@ParameterizedTest
	@CsvSource({
	"Utrera, Lebrija,PENDING,'80,81'",
	"Plaza duque, Utrera,ESTADONUEVO,'80,81'",
	"Segovia, Utrera,PENDING,'81'",
	"Sevilla, ,PENDING,'80,81'",
	", Utrera,PENDING,'80,81'",
	", ,PENDING,'80,81'",
	})
	@Transactional
	@WithMockUser("owner1")
	void solicitarTransporteValidacion(String origin,String destination,String status, String pets) {

		
		Transport t = new Transport();
		t.setOrigin(origin);
		t.setDestination(destination);
		t.setStatus(status);

		Validator validator = createValidator() ;
		Set<ConstraintViolation<Transport>> constraintViolations = validator.validate(t);
		
		if(constraintViolations.size()== 0) {
			
			 this.transportService.solicitarTransporte(t,pets);
			entityManager.flush();
			assertTrue(t.getId() > 0);
		}else {
			for(ConstraintViolation<Transport> c:  constraintViolations) {
			
			if(c.getPropertyPath().toString().contentEquals(("origin"))) {
				
				assertThat(c.getMessage()).isEqualTo("no puede estar vacío");
				
			}else if(c.getPropertyPath().toString().contentEquals(("destination"))) {
				
				assertThat(c.getMessage()).isEqualTo("no puede estar vacío");
				
			}else if(c.getPropertyPath().toString().contentEquals(("status"))) {
				
				assertTrue(c.getMessage().contentEquals("no puede estar vacío")|| c.getMessage().contentEquals("tiene que corresponder a la expresión regular \"^PENDING|TRANSPORTED$\""));
			}
			
			
			}
		}

	}
	
	//VALIDACIONES
	
	@Test
	void nuevoObjetoTransporte() {

		
		Transport t = new Transport();


		Validator validator = createValidator() ;
		Set<ConstraintViolation<Transport>> constraintViolations = validator.validate(t);

		assertThat(constraintViolations.size()).isEqualTo(3);

	}
	
	
	
	
	//TEST: findOne()

	@Transactional
	@ParameterizedTest
	@ValueSource(ints = { 500 ,501 ,502, 0 })
	public void findOne(int transportId) {
		
		if(transportId > 0) {
			Transport t = this.transportService.findOne(transportId);
			assertNotNull(t,"El transporte existe.");
		}else {
			assertThrows(IllegalArgumentException.class, ()-> this.transportService.findOne(transportId),"No ha saltado la excepción esperada");
			entityManager.flush();
		}

		
	}
	
	
	
	
	//TEST: findAll()
	@Test
	@Transactional
	public void findAll() {
		entityManager.flush();
		Collection<Transport> AllTransports = this.transportService.findAll();
		assertTrue(!AllTransports.isEmpty(), "No existen 6 transportes");
		Transport t = this.transportService.findOne(500);
		entityManager.flush();
		assertTrue(AllTransports.contains(t) , "Contiene el transporte indicado");
	}
	
	//TEST: transportsPending()
	
	@Test
	@WithMockUser("clerk1")
	@Transactional
	public void transportsPending() {
		
		Collection<Transport> transportesPendientes=this.transportService.transportsPending();
		assertTrue(!transportesPendientes.isEmpty());
		
		for(Transport t : transportesPendientes) {
			
			assertTrue(t.getCompany()==null && t.getClerk()==null && 
					t.getDestination()!=null && t.getOrigin()!=null && 
					t.getPets().size()>0,"Transportes pendientes bien construidos.");
		}
		
	}
	
	
	
	//TEST: transportsTransported()
	@Test
	@WithMockUser("owner1")
	@Transactional
	public void transportsTransported() {
		
		Collection<Transport> transportesRealizados=	this.transportService.transportsTransported();
		assertTrue(!transportesRealizados.isEmpty());
		
		for(Transport t : transportesRealizados) {
			
			assertTrue(t.getCompany()!=null && t.getClerk()!=null && 
					t.getDestination()!=null && t.getOrigin()!=null && 
					t.getPets().size()>0,"Transportes realizados correctamente.");
		}
	}
	

	//TEST: create()
	@Test
	@WithMockUser("owner1")
	@Transactional
	public void create() {
		Transport t = this.transportService.create();
		Assert.isTrue(t.getStatus()=="PENDING","Transporte no creado correctamente");
		entityManager.flush();
		
	}
	
	
	//TEST: solicitarTransporte --- NEGATIVO
	
	@Test
	@WithMockUser("owner1")
	@Transactional
	public void solicitarTransporteNeg() {
		Transport t = this.transportService.create();
		t.setOrigin("Utrera");
		t.setDestination("Lebrija");
		entityManager.flush();
		Assertions.assertThrows( NullPointerException.class, ()-> this.transportService.solicitarTransporte(t,null));
		
	}
	//TEST: solicitarTransporte --- NEGATIVO
	
	@Test
	@WithMockUser("owner1")
	@Transactional
	public void solicitarTransporteNeg2() {
		Transport t = this.transportService.create();
		t.setOrigin(null);
		t.setDestination(null);
		entityManager.flush();
		Assertions.assertThrows( NullPointerException.class, ()-> this.transportService.solicitarTransporte(t,null));
		
	}
	
	
	//TEST: solicitarTransporte --- POSITIVO
	@Test
	@WithMockUser("owner1")
	@Transactional
	public void solicitarTransportePos() {
		entityManager.flush();
		Transport t = this.transportService.create();
		t.setOrigin("Alcalá");
		t.setDestination("Lebrija");
		Transport tSolicitado = this.transportService.solicitarTransporte(t,"80");
		entityManager.flush();
		Assert.isTrue(tSolicitado.getPets().size()>0,"Transporte no creado correctamente");
		
	}
	
	//TEST: transportar --- POSITIVO
	
	@Test
	@WithMockUser("clerk1")
	@Transactional
	public void transportarPos() {
		
		Transport t = this.transportService.findOne(500);
		t.setCompany("SEUR");
		t.setStatus("TRANSPORTED");
		Transport tTransport= this.transportService.transportar(t);
		entityManager.flush();
		Assert.isTrue(tTransport != null,"Transporte creado correctamente");

		
	}
	
	//TEST: transportar --- NEGATIVO
	
	@Test
	@WithMockUser("clerk1")
	@Transactional
	public void transportarNeg() {
		
		Transport t = this.transportService.findOne(505);
		Assertions.assertThrows( IllegalArgumentException.class, ()->this.transportService.transportar(t));

		
	}



	
}
