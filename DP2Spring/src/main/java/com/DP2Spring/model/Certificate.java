package com.DP2Spring.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "certificate")
public class Certificate extends DomainEntity{
	
	private static final long serialVersionUID = 1L;
	
	// Attributes -----------------------------------
	
	@NotBlank
	private String entity;
	
	@NotBlank
	private String description;

	public Certificate() {
		super();
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	

}
