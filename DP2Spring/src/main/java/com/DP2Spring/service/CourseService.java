package com.DP2Spring.service;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		if(course.getId() == 0) {
			course.setStartDate(new Date(System.currentTimeMillis()-1));
			course.setEndDate(new Date(System.currentTimeMillis()-1));
		}

		return this.courseRepository.saveAndFlush(course);


	}
	
	public Course saveEnroll(Course course) {


		//TODO: ASSERTS
		if(course.getId() == 0) {
			course.setStartDate(new Date(System.currentTimeMillis()-1));
			course.setEndDate(new Date(System.currentTimeMillis()-1));
		}

		return this.courseRepository.save(course);


	}




	//Other methods

	public Collection<Course> getEnrollCourses(){
		return this.courseRepository.getEnrollCourses();
	}

	public Course findById(int id) {
		return this.courseRepository.findById(id).get();
	}
}
