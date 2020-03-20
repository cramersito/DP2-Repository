package com.DP2Spring.helper;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class Utils {
	
	public Object  findByPrincipal() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return principal;
	}

}
