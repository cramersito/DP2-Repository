package com.DP2Spring.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.DP2Spring.model.Transport;

@Repository
public interface TransportRepository extends JpaRepository<Transport,Integer> {

    @Query("select t from Transport t where t.status = 'PENDING'")
	Collection<Transport> transportsPending();
	
	

}
