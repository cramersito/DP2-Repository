package com.DP2Spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.DP2Spring.model.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner,Integer> {

    @Query("select o from Owner o where o.userAccount.username = ?1")
    Owner findOwnerByUsername(String username);
}