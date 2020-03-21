package com.DP2Spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DP2Spring.model.Certificate;
import com.DP2Spring.model.Clerk;
import com.DP2Spring.model.Course;
import com.DP2Spring.repository.CourseRepository;

@Service
public class CourseService {

	//Attributes
	
	@Autowired
	private ClerkService clerkService;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private CertificateService certificateService;
	
	//CRUD Methods
	
	public Course create() {
		Course result = new Course();
		
		Certificate certificate = this.certificateService.create();
		Clerk prinicpal = this.clerkService.findByPrincipal();
		
		result.setClerk(prinicpal);
		result.setCertificate(certificate);
		
		
		return result;
		
	}
	
	public Course save(Course course) {
		
		
		//TODO: ASSERTS
		
		return this.courseRepository.save(course);
		
		
	}
	
	
	
	
	//Other methods
	
	
}
