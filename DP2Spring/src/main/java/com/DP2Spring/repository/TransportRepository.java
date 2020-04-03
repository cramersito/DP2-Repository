package com.DP2Spring.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.DP2Spring.model.Pet;
import com.DP2Spring.model.Transport;

@Repository
public interface TransportRepository extends JpaRepository<Transport,Integer> {

    @Query("select t from Transport t where t.status = 'PENDING'")
	Collection<Transport> transportsPending();
    
   @Query("select t from Transport t where t.status = 'TRANSPORTED' and ?1 member of t.pets")
	Collection<Transport> transportsTransported(Pet p);
    
    @Query("select t from Transport t where t.status = 'TRANSPORTED'")
	Collection<Transport> transporteds();
	
	

}
