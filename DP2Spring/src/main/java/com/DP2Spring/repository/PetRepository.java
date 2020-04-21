package com.DP2Spring.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.DP2Spring.model.Pet;
import com.DP2Spring.model.Transport;

@Repository
public interface PetRepository  extends JpaRepository<Pet,Integer> {

    @Query("select p from Pet p where p.owner.id = ?1")
    Collection<Pet> findPetsByOwnerId(int ownerId);
	
    @Query("select t.pets from Transport t where t.status = 'PENDING'")
    Collection<Pet> PetsPendingTransport();
    
    @Query("select p from Pet p where p.id = ?1")
    Pet myPet(int petId);
    
}
