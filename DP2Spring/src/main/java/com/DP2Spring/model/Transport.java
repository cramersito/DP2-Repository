package com.DP2Spring.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "transport")
public class Transport  extends DomainEntity{
	
	
	private static final long serialVersionUID = 1L;
	
	// Attributes -----------------------------------
	@NotBlank
	private String company;
	@NotBlank
	private String origin;
	@NotBlank
	private String destination;
	@NotBlank
	@Pattern(regexp = "^PENDING|TRANSPORTED$")
	private String status;
	
	// Constructors ------------------------------------
	
	public Transport() {
		super();
	}
	
	// Getters and setters -----------------------------
	
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	// Associations -------------------------------------
	
	

	//@NotNull
	@Valid
	@ManyToOne(optional = true)
	private Clerk clerk;
	
	//@NotNull
	//@Valid
	@ManyToMany
	private Collection<Pet> pets;

	public Clerk getClerk() {
		return clerk;
	}

	public void setClerk(Clerk clerk) {
		this.clerk = clerk;
	}

	public Collection<Pet> getPets() {
		return pets;
	}

	public void setPets(Collection<Pet> pets) {
		this.pets = pets;
	}
	
	
	

}
