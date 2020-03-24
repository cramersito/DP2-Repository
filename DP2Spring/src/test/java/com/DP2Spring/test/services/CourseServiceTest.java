package com.DP2Spring.test.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.DP2Spring.model.Certificate;
import com.DP2Spring.model.Clerk;
import com.DP2Spring.model.Course;
import com.DP2Spring.service.CertificateService;
import com.DP2Spring.service.ClerkService;
import com.DP2Spring.service.CourseService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CourseServiceTest {

	@Autowired
	protected CourseService courseService;
	
	
	@Autowired
	protected ClerkService clerkService;
	
	//Probably not necessary this collaborator
	@Autowired
	protected CertificateService certificateService;
	
	@Test
	@Transactional
	public void shouldPersistCourse() {
		Clerk clerk1 = this.clerkService.findOne(200);
		Course course = new Course();
		Certificate certificate = new Certificate();
		certificate.setDescription("Test cert");
		certificate.setEntity("Testt");
		int courses = this.courseService.findAll().size();
		
		course.setClerk(clerk1);
		
		course.setDescription("Test positivo");
		course.setPrice(12.0);
		course.setStartDate(new Date(System.currentTimeMillis()-1));
		course.setEndDate(new Date(System.currentTimeMillis()+1));
		
		assertThat(course.getOwnersRegistered()).isNullOrEmpty();
		
		try {
			this.certificateService.save(certificate);
			
			course.setCertificate(certificate);
			
			this.courseService.save(course);
			
		}catch(Throwable oops) {
			System.out.println("=================================================");
			System.out.println(oops.getMessage());
			System.out.println("=================================================");
		}
		
		//assertThat(this.courseService.getCoursesByClerk(200)).isEqualTo(courses);
		
		assertThat(course.getId()).isNotNull();
		
	}
    
}