package com.DP2Spring.security;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Embeddable;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.security.core.GrantedAuthority;

@Embeddable
@Table(name = "authority")
public class Authority implements GrantedAuthority {
 
	// Constructors -----------------------------------------------------------
	private static final long serialVersionUID = 1L;

	public Authority() {
		super();
	}

	// Values -----------------------------------------------------------------

	public static final String ADMIN = "ADMIN";
	public static final String OWNER = "OWNER";
	public static final String CLERK = "CLERK";

	// Attributes -------------------------------------------------------------

	private String authority;
	
	@NotBlank
	@Pattern(regexp = "^" + Authority.ADMIN + "|" + Authority.OWNER + "|" + Authority.CLERK + "$")
	@Override
	public String getAuthority() {
		return this.authority;
	}

	public void setAuthority(final String authority) {
		this.authority = authority;
	}
	
	public static Collection<Authority> listAuthorities() {
		Collection<Authority> result;
		Authority authority;

		result = new ArrayList<Authority>();

		authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		result.add(authority);

		authority = new Authority();
		authority.setAuthority(Authority.OWNER);
		result.add(authority);

		authority = new Authority();
		authority.setAuthority(Authority.CLERK);
		result.add(authority);

		return result;
	}
	
	@Override
	public int hashCode() {
		return this.getAuthority().hashCode();
	}

	@Override
	public boolean equals(final Object other) {
		boolean result;

		if (this == other)
			result = true;
		else if (other == null)
			result = false;
		else if (!this.getClass().isInstance(other))
			result = false;
		else
			result = (this.getAuthority().equals(((Authority) other).getAuthority()));

		return result;
	}

	@Override
	public String toString() {
		return this.authority;
	}
	
	
}
