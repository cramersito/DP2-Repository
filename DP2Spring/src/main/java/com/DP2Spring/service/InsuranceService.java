package com.DP2Spring.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DP2Spring.model.Certificate;
import com.DP2Spring.model.Insurance;
import com.DP2Spring.repository.CourseRepository;
import com.DP2Spring.repository.InsuranceRepository;

@Service
@Transactional
public class InsuranceService {
	
	//Attributes
	
	@Autowired
	private InsuranceRepository insuranceRepository;
	
	

	
	//Crud methods
	
	public Certificate create() {
		
		return new Certificate();
	}
	

	
	
	//Other methods
	
	public Insurance findOne(int id) {
		
		return this.insuranceRepository.findById(id).get();
	}
	
	public Collection<Insurance> findAll(){
		return this.insuranceRepository.findAll();
	}
}
