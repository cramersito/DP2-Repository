package com.DP2Spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.DP2Spring.model.Clerk;

@Repository
public interface ClerkRepository extends JpaRepository<Clerk,Integer> {

    @Query("select c from Clerk c where c.userAccount.username = ?1")
    Clerk findClerkByUsername(String username);
}