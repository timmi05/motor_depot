package com.mikhailau.training.motordepot.datamodel;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class Vehicle extends AbstractModel {

	@MapsId
	@OneToOne(fetch = FetchType.LAZY, optional = false, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(nullable = false, updatable = false, name = "id")
	private Driver driver;

	@Column
	private String model;

	@Column(unique=true)
	private String licensePlate;

	@Column
	private Boolean stateAfterFreight;

	@Column
	private Double maxWeight;

	@Column
	private Integer numberOfPallets;

	@ManyToOne(targetEntity = VehicleType.class, fetch = FetchType.LAZY)
	private VehicleType vehicleType;

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public Boolean getStateAfterFreight() {
		return stateAfterFreight;
	}

	public void setStateAfterFreight(Boolean stateAfterFreight) {
		this.stateAfterFreight = stateAfterFreight;
	}

	public Double getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(Double maxWeight) {
		this.maxWeight = maxWeight;
	}

	public Integer getNumberOfPallets() {
		return numberOfPallets;
	}

	public void setNumberOfPallets(Integer numberOfPallets) {
		this.numberOfPallets = numberOfPallets;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	@Override
	public String toString() {
		return "Vehicle [driver=" + driver + ", model=" + model + ", licensePlate=" + licensePlate
				+ ", stateAfterFreight=" + stateAfterFreight + ", maxWeight=" + maxWeight + ", numberOfPallets="
				+ numberOfPallets + ", vehicleType=" + vehicleType + "]";
	}
}
