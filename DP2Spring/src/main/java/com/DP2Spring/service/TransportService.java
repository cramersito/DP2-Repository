package com.DP2Spring.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.DP2Spring.model.Clerk;
import com.DP2Spring.model.Owner;
import com.DP2Spring.model.Pet;
import com.DP2Spring.model.Transport;

import com.DP2Spring.repository.TransportRepository;

@Service
public class TransportService {
	
	
    // Repository

    @Autowired
    private TransportRepository transportRepository;

    // Supporting services



    @Autowired
    private OwnerService ownerService;
    
    @Autowired
    private ClerkService clerkService;


    @Autowired
    private PetService petService;

    
	//Testing approach
	@Autowired
    public TransportService(TransportRepository transportRepository){
		this.transportRepository = transportRepository;
    }

	//Testing approach
  

    //Transportes pendientes
    
    public Collection<Transport> transportsPending(){
        Collection<Transport> result;
    	Clerk principal = this.clerkService.findByPrincipal();
    	Assert.notNull(principal,"Debe ser un clerk");
        result = this.transportRepository.transportsPending();
        return result;
    }
    
    // CRUD Methods

    public Transport create(){
        Transport result;
        Owner principal;

        principal = this.ownerService.findByPrincipal();
        Assert.notNull(principal, "El propietario debe existir");

        result = new Transport();
        result.setStatus("PENDING");

        return result;
    }

    
    //Others
    
    public Collection<Transport> findAll() {
        Collection<Transport> result;

        result = this.transportRepository.findAll();

        return result;
    }

    public Transport findOne(int transportId) {
        Assert.isTrue(transportId > 0, "La parcela no existe");

        Transport result;

        result = this.transportRepository.findById(transportId).get();
        Assert.notNull(result, "La parcela no existe");

        return result;
    }
    
    
    public Transport solicitarTransporte(Transport transport,String pets){
    	Collection<Pet> colPets= new ArrayList<Pet> ();
    	
        Owner principal = this.ownerService.findByPrincipal();   
    	Assert.notNull(principal, "La parcela debe existir");
    	
    	for (String s : pets.split(",")) {
    		Pet pet=this.petService.myPet(Integer.parseInt(s));
    		colPets.add(pet);
    	    Assert.isTrue(this.petService.findPetsByOwnerId(principal.getId()).contains(pet), "No es tu mascota");
    	}
    		
    	transport.setPets(colPets);
        Assert.notNull(transport, "La parcela debe existir");
        Assert.isTrue(transport.getPets().size() > 0, "Debe añadir una o más mascotas"); 
        Assert.isTrue(transport.getStatus().equals("PENDING"), "Debe estar en estado pendiente");

        Transport result;

        result = this.transportRepository.save(transport);

        return result;

    }
    
    public Transport transportar(Transport transport){
    	
    	Transport t= this.findOne(transport.getId());
    	
		Clerk principal = this.clerkService.findByPrincipal();		
		transport.setClerk(principal);
		transport.setPets(t.getPets());
		Assert.notNull(transport.getClerk() != null && transport != null,"No puede ser nulo");
        Assert.notNull(transport.getId() > 0, "La parcela debe existir");
        Assert.isTrue(transport.getPets().size() > 0, "Debe añadir una o más mascotas");
        Assert.isTrue(transport.getCompany()!= null && transport.getCompany() != "","No se ha añadido compañia de transporte");
        Assert.isTrue(transport.getStatus().equals("TRANSPORTED"), "Debe estar en estado pendiente");

        Transport result;

        result = this.transportRepository.save(transport);

        return result;

    }



	public Collection<Transport> transportsTransported() {
        Collection<Transport> result;
    	Owner principal = this.ownerService.findByPrincipal();
    	Assert.notNull(principal,"Debe ser un clerk");
        result = this.transportRepository.transportsTransported();
        
		return result;
	}
    
    

    

}
