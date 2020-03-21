package com.DP2Spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DP2Spring.model.Transport;

@Repository
public interface TransportRepository extends JpaRepository<Transport,Integer> {
	
	

}
