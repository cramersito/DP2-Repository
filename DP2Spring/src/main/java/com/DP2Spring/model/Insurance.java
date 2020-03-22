package com.DP2Spring.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "insurance")
public class Insurance extends DomainEntity{
	
	

	private static final long serialVersionUID = 1L;
	
	
	// Attributes -----------------------------------
	
	@NotBlank
	private String nombre;
	
	// Constructors ------------------------------------
	
	public Insurance() {
		super();
	}

	// Getters and setters -----------------------------
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
