package com.mikhailau.training.motordepot.datamodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class Driver extends AbstractModel {

	@MapsId
	@OneToOne(fetch = FetchType.LAZY, optional = false, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(nullable = false, updatable = false, name = "id")
	private Credentials credentials;

	@JoinTable(name = "driver_2_category_license", joinColumns = {
			@JoinColumn(name = "driver_id") }, inverseJoinColumns = { @JoinColumn(name = "category_license_id") })
	@ManyToMany(targetEntity = CategoryLicense.class, fetch = FetchType.LAZY)
	private List<CategoryLicense> categoryLicense = new ArrayList<CategoryLicense>();;

	@Column
	private String lastName;

	@Column
	private String firstName;

	@Column
	private Boolean stateFree;

	@Column
	private Date leadTime;

	public List<CategoryLicense> getCategoryLicense() {
		return categoryLicense;
	}

	public void setCategoryLicense(List<CategoryLicense> categoryLicense) {
		this.categoryLicense = categoryLicense;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Boolean getStateFree() {
		return stateFree;
	}

	public void setStateFree(Boolean stateFree) {
		this.stateFree = stateFree;
	}

	public Date getLeadTime() {
		return leadTime;
	}

	public void setLeadTime(Date leadTime) {
		this.leadTime = leadTime;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	@Override
	public String toString() {
		return "Driver [credentials=" + credentials + ", categoryLicense=" + categoryLicense + ", lastName=" + lastName
				+ ", firstName=" + firstName + ", stateFree=" + stateFree + ", leadTime=" + leadTime + "]";
	}
}
