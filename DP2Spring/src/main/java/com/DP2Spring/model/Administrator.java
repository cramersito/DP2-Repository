package com.DP2Spring.model;

import javax.persistence.Entity;
import javax.persistence.Table;
 
@Entity
@Table(name = "administrator")
public class Administrator extends Actor {

	private static final long serialVersionUID = 1L;

	// Constructors ----------------------------------
	
	public Administrator() {
		super();
	}
	
}
