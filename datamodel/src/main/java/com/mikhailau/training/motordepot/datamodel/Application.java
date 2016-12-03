package com.mikhailau.training.motordepot.datamodel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class Application extends AbstractModel {

	@Column
	@Enumerated(value = EnumType.ORDINAL)
	private ApplicationState applicationState;

	@ManyToOne(targetEntity = Customer.class, fetch = FetchType.LAZY)
	private Customer customer;

	@Column
	private Date receiptTime;

	@Column
	private Date leadTime;

	@ManyToOne(targetEntity = VehicleType.class, fetch = FetchType.LAZY)
	private VehicleType vehicleType;

	@Column
	private Double weight;

	@Column
	private Integer numberOfPallets;

	@ManyToOne(targetEntity = Driver.class, fetch = FetchType.LAZY)
	private Driver driver;

	@Column
	private String loadingAddress;

	@Column
	private String unloadingAddress;

	public String getLoadingAddress() {
		return loadingAddress;
	}

	public void setLoadingAddress(String loadingAddress) {
		this.loadingAddress = loadingAddress;
	}

	public String getUnloadingAddress() {
		return unloadingAddress;
	}

	public void setUnloadingAddress(String unloadingAddress) {
		this.unloadingAddress = unloadingAddress;
	}

	public ApplicationState getApplicationState() {
		return applicationState;
	}

	public void setApplicationState(ApplicationState applicationState) {
		this.applicationState = applicationState;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getReceiptTime() {
		return receiptTime;
	}

	public void setReceiptTime(Date receiptTime) {
		this.receiptTime = receiptTime;
	}

	public Date getLeadTime() {
		return leadTime;
	}

	public void setLeadTime(Date leadTime) {
		this.leadTime = leadTime;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Integer getNumberOfPallets() {
		return numberOfPallets;
	}

	public void setNumberOfPallets(Integer numberOfPallets) {
		this.numberOfPallets = numberOfPallets;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	@Override
	public String toString() {
		return "Application [applicationState=" + applicationState + ", customer=" + customer + ", receiptTime="
				+ receiptTime + ", leadTime=" + leadTime + ", vehicleType=" + vehicleType + ", weight=" + weight
				+ ", numberOfPallets=" + numberOfPallets + ", driver=" + driver + ", loadingAddress=" + loadingAddress
				+ ", unloadingAddress=" + unloadingAddress + "]";
	}
}
