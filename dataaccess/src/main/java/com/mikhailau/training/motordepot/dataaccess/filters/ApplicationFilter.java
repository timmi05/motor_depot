package com.mikhailau.training.motordepot.dataaccess.filters;

import java.util.ArrayList;
import java.util.List;

import com.mikhailau.training.motordepot.datamodel.ApplicationState;
import com.mikhailau.training.motordepot.datamodel.Customer;
import com.mikhailau.training.motordepot.datamodel.Driver;
import com.mikhailau.training.motordepot.datamodel.VehicleType;

public class ApplicationFilter extends AbstractFilter {
	private Driver driver;
	private Customer customer;
	private VehicleType vehicleType;
	private boolean isFetchDriver;
	private boolean isFetchCustomer;
	private boolean isFetchVehicleType;
	private List<ApplicationState> states;

	public List<ApplicationState> getStates() {
		return states;
	}

	public void addState(ApplicationState state) {
		if (states == null) {
			states = new ArrayList<>();
		}
		states.add(state);
	}

	public void setStates(List<ApplicationState> states) {
		this.states = states;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public boolean isFetchDriver() {
		return isFetchDriver;
	}

	public void setFetchDriver(boolean isFetchDriver) {
		this.isFetchDriver = isFetchDriver;
	}

	public boolean isFetchCustomer() {
		return isFetchCustomer;
	}

	public void setFetchCustomer(boolean isFetchCustomer) {
		this.isFetchCustomer = isFetchCustomer;
	}

	public boolean isFetchVehicleType() {
		return isFetchVehicleType;
	}

	public void setFetchVehicleType(boolean isFetchVehicleType) {
		this.isFetchVehicleType = isFetchVehicleType;
	}

	@Override
	public String toString() {
		return "ApplicationFilter [driver=" + driver + ", customer=" + customer + ", vehicleType=" + vehicleType
				+ ", isFetchDriver=" + isFetchDriver + ", isFetchCustomer=" + isFetchCustomer + ", isFetchVehicleType="
				+ isFetchVehicleType + ", states=" + states + "]";
	}
}
