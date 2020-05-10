package com.DP2Spring.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DP2Spring.model.Certificate;
import com.DP2Spring.repository.CertificateRepository;

@Service
@Transactional
public class CertificateService {
	
	//Attributes
	
	@Autowired
	private CertificateRepository certificateRepository;
	
	

	
	
	//Crud methods
	
	public Certificate create() {
		
		return new Certificate();
	}
	
	
	
	public Certificate save(Certificate certificate) {
		return this.certificateRepository.saveAndFlush(certificate);
	}
	
	
	
	//Other methods
	
	public Certificate findOne(int id) {
		
		return this.certificateRepository.findById(id).get();
	}
	
	public Collection<Certificate> findAll(){
		return this.certificateRepository.findAll();
	}
}
