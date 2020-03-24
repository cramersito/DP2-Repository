package com.DP2Spring.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.DP2Spring.model.Course;
import com.DP2Spring.model.Owner;
import com.DP2Spring.repository.OwnerRepository;

@Service
@Transactional
public class OwnerService {

	// Repository

	@Autowired
	private OwnerRepository ownerRepository;

	@Autowired
	CourseService courseService;

	// Supporting services

	// Constructor

	public OwnerService(){
		super();
	}




	public void enroll(int courseId) {

		Owner principal = this.findByPrincipal();
		Course course = this.courseService.findById(courseId);
		
		Assert.isTrue(!course.getOwnersRegistered().contains(principal), "Ya estás inscrito a este curso.");

		Collection<Owner> enrolled = course.getOwnersRegistered();
		enrolled.add(principal);
		
		
		this.courseService.save(course);

		


	}


	// CRUD Methods
	public Owner findOne(int ownerId) {
		Assert.isTrue(ownerId > 0, "Invalid ownerId");

		Optional<Owner> owner;
		Owner result;

		owner = this.ownerRepository.findById(ownerId);

		result = owner.orElse(null);

		return result;
	}

	// Other business methods

	public Owner findByPrincipal(){
		Owner result;
		UserDetails userAccount;
		Authentication authentication;

		authentication = SecurityContextHolder.getContext().getAuthentication();
		userAccount = (UserDetails) authentication.getPrincipal();

		result = this.ownerRepository.findOwnerByUsername(userAccount.getUsername());
		Assert.notNull(result,"El propietario no existe");

		return result;
	}
}