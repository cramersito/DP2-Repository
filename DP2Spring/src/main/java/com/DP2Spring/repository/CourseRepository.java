package com.DP2Spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DP2Spring.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {

}