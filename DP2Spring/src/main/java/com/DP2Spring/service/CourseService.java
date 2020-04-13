package com.DP2Spring.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.DP2Spring.model.Certificate;
import com.DP2Spring.model.Clerk;
import com.DP2Spring.model.Course;
import com.DP2Spring.model.Insurance;
import com.DP2Spring.model.Owner;
import com.DP2Spring.model.Pet;
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
	public CourseService(CourseRepository courseRepository,ClerkService clerkService,
			CertificateService certificateService,OwnerService ownerService) {
		this.courseRepository = courseRepository;
		this.clerkService = clerkService;
		this.certificateService = certificateService;
		this.ownerService = ownerService;
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

	public Insurance getLaw(Collection<Pet>pets) {
		Insurance law = null;

		for(Pet p : pets) {
			if(p.getTipo().equals("EXOTIC")) {
				law = p.getLaw();
				break;
			}
		}
		
		return law;

	}
	
	public boolean CoursePassed() {
		boolean result = false;
		Owner principal = this.ownerService.findByPrincipal();
		
		Collection<Integer> coursesId = this.getCoursesByOwner(principal.getId());
		int count = 0;
		
		for(Integer c : coursesId) {
			Course currentCourse = this.findById(c);
			
			if(currentCourse.getEndDate().before(new Date(System.currentTimeMillis()-1))) {
				count++;
				
				if(count == 2) {
					result = true;
				}
				
				
			}
			
		}
		
		
		return result;
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

	public Collection<Course> findAll(){

		return this.courseRepository.findAll();
	}
}
