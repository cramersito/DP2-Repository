package com.DP2Spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.DP2Spring.security.UserAccount;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
	
	Optional<UserAccount> findByUsername(String username);
	
	@Query(value = "select authority a from dp2_spring.user_account_authorities where user_account_id = ?1", nativeQuery = true)
	String findByUserId(int id);

}
