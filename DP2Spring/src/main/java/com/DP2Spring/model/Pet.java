package com.DP2Spring.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "pet")
public class Pet extends DomainEntity{
	
private static final long serialVersionUID = 1L;
	
	// Attributes -----------------------------------
	
	@NotBlank
	private String nombre;
	
	@NotBlank
	@Pattern(regexp = "^DOMESTIC|EXOTIC|WILD$")
	private String tipo;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date birthday;
	
	// Constructors ------------------------------------
	
	public Pet() {
		super();
	}

	// Getters and setters -----------------------------
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	

	public Date getBirthday() {
		return birthday;
	}

	public Insurance getLaw() {
		return law;
	}

	public void setLaw(Insurance law) {
		this.law = law;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	// Associations -------------------------------------


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Owner owner;
	
	
	@Valid
	@OneToOne(optional = true)
	private Insurance law;
	

}
