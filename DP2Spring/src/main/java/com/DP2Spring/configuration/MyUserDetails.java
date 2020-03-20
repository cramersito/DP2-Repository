package com.DP2Spring.configuration;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.DP2Spring.security.Authority;
import com.DP2Spring.security.UserAccount;

public class MyUserDetails implements UserDetails {

	private String userName;
	private String password;
	private boolean active;
	private Collection<Authority> authorities;
	
	public MyUserDetails(UserAccount user) {
		
		this.userName = user.getUsername();
		this.password = user.getPassword();
		this.active = user.isEnabled();
		this.authorities = user.getAuthorities();
		
	}

	public MyUserDetails() {

	}

	@Override
	public Collection<Authority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}
}