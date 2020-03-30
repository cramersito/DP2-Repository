package com.DP2Spring.test.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.aspectj.lang.annotation.Before;
import org.aspectj.util.IStructureModel;
import org.hibernate.hql.internal.ast.tree.ExpectedTypeAwareNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.Validator;
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
public class TransportServiceTest {

	
	
	@Autowired
	protected TransportService transportService;
	
	
	@Autowired
	protected ClerkService clerkService;
	
	@Autowired
	protected OwnerService ownerService;
	
	@Autowired
	private EntityManager entityManager;

	

	
	//TEST: findOne()

	@Transactional
	@ParameterizedTest
	@ValueSource(ints = { 500 ,501 ,502, 0 })
	public void findOne(int transportId) {
		
		if(transportId > 0) {
			Transport t = this.transportService.findOne(500);
			assertNotNull(t,"El transporte existe.");
		}else {
			assertThrows(IllegalArgumentException.class, ()-> this.transportService.findOne(transportId),"No ha saltado la excepción esperada");
		}

		
	}
	
	
	//TEST: findAll()
	@Test
	@Transactional
	public void findAll() {
		Collection<Transport> AllTransports = this.transportService.findAll();
		assertTrue(AllTransports.size()==6 , "Existen 6 transportes");
		Transport t = this.transportService.findOne(500);
		assertTrue(AllTransports.contains(t) , "Contiene el transporte indicado");
	}
	
	//TEST: transportsPending()
	
	@Test
	@WithMockUser("clerk1")
	@Transactional
	public void transportsPending() {
		
		Collection<Transport> transportesPendientes=this.transportService.transportsPending();
		assertTrue(transportesPendientes.size()==4);
		
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
		assertTrue(transportesRealizados.size()==2);
		
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
		
	}
	
	
	//TEST: solicitarTransporte --- NEGATIVO
	
	@Test
	@WithMockUser("owner1")
	@Transactional
	public void solicitarTransporteNeg() {
		Transport t = this.transportService.create();
		t.setOrigin("Utrera");
		t.setDestination("Lebrija");

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

		Assertions.assertThrows( NullPointerException.class, ()-> this.transportService.solicitarTransporte(t,null));
		
	}
	
	
	//TEST: solicitarTransporte --- POSITIVO
	@Test
	@WithMockUser("owner1")
	@Transactional
	public void solicitarTransportePos() {
		Transport t = this.transportService.create();
		t.setOrigin("Utrera");
		t.setDestination("Lebrija");
		Transport tSolicitado = this.transportService.solicitarTransporte(t,"80,81");
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
		Assert.isTrue(tTransport != null,"Transporte no creado correctamente");
		
	}
	//TEST: transportar --- NEGATIVO
	
	@Test
	@WithMockUser("clerk1")
	@Transactional
	public void transportarNegNull() {
		
		Transport t = this.transportService.findOne(500);
		Assertions.assertThrows(IllegalArgumentException.class,()->this.transportService.transportar(t),"Se ha transportado");
		
	}


	
}
