package com.DP2Spring.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import com.DP2Spring.model.Clerk;
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
    	Clerk prinicpal = this.clerkService.findByPrincipal();
   
        result = this.transportRepository.transportsPending();
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
    
    

}