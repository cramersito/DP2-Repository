package com.DP2Spring.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

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
    private ActorService actorService;

    @Autowired
    private OwnerService ownerService;
    
    @Autowired
    private ClerkService clerkService;

    @Autowired
    private Validator validator;
    
    // Constructor

    public TransportService(){
        super();
    }

    

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
    
    
    public Transport solicitarTransporte(Transport transport){
        Assert.notNull(transport, "La parcela debe existir");
        Assert.isTrue(transport.getPets().size() > 0, "Debe añadir una o más mascotas");
       //controlar que son sus mascotas
        Assert.isTrue(transport.getStatus().equals("PENDING"), "Debe estar en estado pendiente");

        Transport result;

        result = this.transportRepository.save(transport);

        return result;

    }
    
    public Transport transportar(Transport transport){
        Assert.notNull(transport.getId() > 0, "La parcela debe existir");
        Assert.isTrue(transport.getPets().size() > 0, "Debe añadir una o más mascotas");
       //controlar que son sus mascotas
        Assert.isTrue(transport.getStatus().equals("TRANSPORTED"), "Debe estar en estado pendiente");

        Transport result;

        result = this.transportRepository.save(transport);

        return result;

    }
    
    
    // Other business methods

  /*  public Transport reconstruct(final Transport transport, final BindingResult binding) {
    	Transport result, stored;

		if (transport.getId() == 0)
			result = this.create();
		else {
			stored = this.findOne(transport.getId());

			result = new Transport();
			result.setId(stored.getId());
            result.setVersion(stored.getVersion());
            result.setPets(stored.getPets());
            result.setStatus(stored.getStatus().trim());
            result.
            result.setArgumented(stored.isArgumented());
        }
       
        result.setTitle(transport.getTitle().trim());


		this.validator.validate(result, binding);

		return result;
	}*/
    

}
