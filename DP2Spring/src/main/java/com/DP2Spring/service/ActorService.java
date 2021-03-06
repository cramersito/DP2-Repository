package com.DP2Spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.DP2Spring.model.Actor;
import com.DP2Spring.model.Administrator;
import com.DP2Spring.repository.ActorRepository;
import com.DP2Spring.security.Authority;
import com.DP2Spring.security.UserAccount;

@Service
@Transactional
public class ActorService {

	private static final Log log = LogFactory.getLog(ActorService.class);
	
    // Repository ------------------------------------

    @Autowired
    private ActorRepository actorRepository;
    
    // Supporting services ----------------------------

    // Constructor ------------------------------------

    public ActorService(){
        super();
    }

    // CRUD Methods ------------------------------------
    public Actor findOne(int actorId) {
    	Actor result = this.actorRepository.findById(actorId).get();
    	
    	
    	
    	return result;
    }
    
   
    
    public Actor findByPrincipal(){
		Actor result;
    	UserDetails userAccount;
        Authentication authentication;

        authentication = SecurityContextHolder.getContext().getAuthentication();
        userAccount = (UserDetails) authentication.getPrincipal();
        
        result = this.findByUsername(userAccount.getUsername());
		Assert.notNull(result,"El actor no existe");
		
		return result;
    }
    
    public boolean isASpecificRole(Actor actor, String role) {
    	boolean result;
    	Authority authority;
    	
    	authority = new Authority();
    	authority.setAuthority(role);
    	  	
    	result = actor.getUserAccount().getAuthorities().contains(authority);
    	
    	return result;
    }
    
    public String getEncodedIban(String iban) {
    	String result;
    	
    	result = (iban != null && iban != "" && !iban.isEmpty())
    			? iban.substring(0, 4) + " **** **** **** **** " + iban.subSequence(19, 23)
    			: "";
    	
    	return result;
    }
    
    public String getRole(Actor actor) {
    	String result;
    	List<Authority> authorities;
    	
    	authorities = new ArrayList<Authority>(actor.getUserAccount().getAuthorities());
    	result = "";
    	for (Authority a: authorities) {
    		result += a.getAuthority() + " ";
    	}
    	 	
    	return result;
    }
    
    private Actor findByUsername(String username) {
    	Actor result;
    	
    	result = this.actorRepository.findByUsername(username);
    	
    	return result;
    }
    
}