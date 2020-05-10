package com.DP2Spring.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.DP2Spring.configuration.MyUserDetailsService;
import com.DP2Spring.model.Clerk;
import com.DP2Spring.repository.ClerkRepository;
import com.DP2Spring.repository.OwnerRepository;

@Service
@Transactional
public class ClerkService {

    // Repository

    @Autowired
    private ClerkRepository clerkRepository;
    
    @Autowired
    private MyUserDetailsService myUserDetailsService;


    // Supporting services

    // Constructor

    public ClerkService(){
        super();
    }

    // CRUD Methods
    public Clerk findOne(int clerkId) {
    	Assert.isTrue(clerkId > 0, "Invalid ClerkId");
    	
    	Optional<Clerk> clerk;
    	Clerk result;
    	
    	clerk = this.clerkRepository.findById(clerkId);
    	
    	result = clerk.orElse(null);
    	
    	return result;
    }

    // Other business methods

    public Clerk findByPrincipal(){
        Clerk result;
        UserDetails userAccount;
        Authentication authentication;
        UsernamePasswordAuthenticationToken usernameToken;
        
//        Optional<Clerk> clerk = this.clerkRepository.findById(200);
//        Assert.notNull(clerk,"El propietario no existe");
//        
//        userAccount = this.myUserDetailsService.loadUserByUsername(clerk.get().getUserAccount().getUsername().trim());
//		
//		usernameToken = new UsernamePasswordAuthenticationToken(userAccount,
//																null,
//																userAccount.getAuthorities());
//		
//		SecurityContextHolder.getContext().setAuthentication(usernameToken);
        
        authentication = SecurityContextHolder.getContext().getAuthentication();
        userAccount = (UserDetails) authentication.getPrincipal();

        result = this.clerkRepository.findClerkByUsername(userAccount.getUsername());
        Assert.notNull(result,"El propietario no existe");
        
        return result;
    }
}