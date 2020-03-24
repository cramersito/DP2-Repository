package com.DP2Spring.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.DP2Spring.model.Certificate;
import com.DP2Spring.model.Clerk;
import com.DP2Spring.model.Course;
import com.DP2Spring.repository.CourseRepository;

@Service
@Transactional
public class CourseService {

	//Attributes

	@Autowired
	private ClerkService clerkService;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private CertificateService certificateService;
	
	@Autowired
	private OwnerService ownerService;
	
	//Testing approach
	
	@Autowired
	public CourseService(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

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
			
		Assert.isTrue(course.getStartDate().before(course.getEndDate()), "La fecha de inicio debe ser anterior a la fecha de fin.");

		
		

		return this.courseRepository.saveAndFlush(course);


	}
	




	//Other methods

	public Collection<Course> getEnrollCourses(){
		
		Collection<Course> courses =  this.courseRepository.getEnrollCourses();
		Collection<Course> finalCourses = new ArrayList<Course>();
		
		for(Course c: courses) {
			if(!c.getOwnersRegistered().contains(this.ownerService.findByPrincipal())) {
				finalCourses.add(c);
			}
		}
		
		return finalCourses;
	}

	public Course findById(int id) {
		return this.courseRepository.findById(id).get();
	}
	
	public Collection<Integer> getCoursesByOwner(int ownerId) {
		Collection<Integer> result = this.courseRepository.getCoursesByOwner(ownerId);
		
		return result;
	}
	
	public Collection<Course> getCoursesByClerk(int clerkId) {
		Collection<Course> result = this.courseRepository.getClerkCourses(clerkId);
		
		return result;
	}
}
