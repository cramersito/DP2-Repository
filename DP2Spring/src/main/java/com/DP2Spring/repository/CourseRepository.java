package com.DP2Spring.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.DP2Spring.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {

	@Query("select c from Course c where c.startDate <= CURRENT_DATE")
	Collection<Course> getEnrollCourses();
}