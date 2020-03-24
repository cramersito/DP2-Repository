package com.DP2Spring.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DP2Spring.model.Pet;
import com.DP2Spring.repository.PetRepository;


@Service
public class PetService {
	
	
	
    // Repository

    @Autowired
    private PetRepository PetRepository;


    public PetService() {
		super();
	}
    
    
    
	
    public Collection<Pet> findPetsByOwnerId(int ownerId){
        Collection<Pet> result;

        result = this.PetRepository.findPetsByOwnerId(ownerId);

        return result;
    }
    
    public Collection<Pet> petsPendingTransport(){
        Collection<Pet> result;

        result = this.PetRepository.PetsPendingTransport();
        return result;
    }
    
    public Pet myPet(int petId) {
    	Pet res= this.PetRepository.myPet(petId);
    	return res;
    	
    }
    
    

}
