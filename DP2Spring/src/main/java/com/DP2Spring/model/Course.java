package com.DP2Spring.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "course")
public class Course extends DomainEntity{

	
private static final long serialVersionUID = 1L;
	
	// Attributes -----------------------------------
	
	
	@DecimalMin(value = "0.0")
	@Digits(integer = 9, fraction = 2)
	private double price;
	
	@NotBlank
	private String description;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date startDate;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date endDate;
	
	// Constructors ------------------------------------
	
	public Course() {
		super();
	}
	
	// Getters and setters -----------------------------
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	// Associations ------------------------------
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Clerk clerk;
	
	@NotNull
	@Valid
	@OneToOne(optional = false)
	private Certificate certificate;
	

	//@Valid
	@OneToMany
	private Collection<Owner> OwnersRegistered;

	public Clerk getClerk() {
		return clerk;
	}

	public void setClerk(Clerk clerk) {
		this.clerk = clerk;
	}

	public Certificate getCertificate() {
		return certificate;
	}

	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}

	public Collection<Owner> getOwnersRegistered() {
		return OwnersRegistered;
	}

	public void setOwnersRegistered(Collection<Owner> ownersRegistered) {
		OwnersRegistered = ownersRegistered;
	}
	
	
	
	
}
