package com.mikhailau.training.motordepot.dataaccess.filters;

import com.mikhailau.training.motordepot.datamodel.VehicleType;

public class VehicleFilter extends AbstractFilter {
	private String vehicleLicensePlate;
	private String vehicleModel;
	private boolean isFetchDriver;
	private boolean isFetchVehicleType;
	private String driver;
	private VehicleType vehicleType;
	private boolean isStateTrue;
	private boolean isStateFalse;
	private boolean isStateFreeTrue;
	private boolean isStateFreeFalse;
	private boolean isStateAfterFreightTrue;
	private boolean isStateAfterFreightFalse;

	public String getVehicleLicensePlate() {
		return vehicleLicensePlate;
	}

	public void setVehicleLicensePlate(String vehicleLicensePlate) {
		this.vehicleLicensePlate = vehicleLicensePlate;
	}

	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

	public boolean isFetchDriver() {
		return isFetchDriver;
	}

	public void setFetchDriver(boolean isFetchDriver) {
		this.isFetchDriver = isFetchDriver;
	}

	public boolean isFetchVehicleType() {
		return isFetchVehicleType;
	}

	public void setFetchVehicleType(boolean isFetchVehicleType) {
		this.isFetchVehicleType = isFetchVehicleType;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public boolean isStateTrue() {
		return isStateTrue;
	}

	public void setStateTrue(boolean isStateTrue) {
		this.isStateTrue = isStateTrue;
	}

	public boolean isStateFalse() {
		return isStateFalse;
	}

	public void setStateFalse(boolean isStateFalse) {
		this.isStateFalse = isStateFalse;
	}

	public boolean isStateFreeTrue() {
		return isStateFreeTrue;
	}

	public void setStateFreeTrue(boolean isStateFreeTrue) {
		this.isStateFreeTrue = isStateFreeTrue;
	}

	public boolean isStateFreeFalse() {
		return isStateFreeFalse;
	}

	public void setStateFreeFalse(boolean isStateFreeFalse) {
		this.isStateFreeFalse = isStateFreeFalse;
	}

	public boolean isStateAfterFreightTrue() {
		return isStateAfterFreightTrue;
	}

	public void setStateAfterFreightTrue(boolean isStateAfterFreightTrue) {
		this.isStateAfterFreightTrue = isStateAfterFreightTrue;
	}

	public boolean isStateAfterFreightFalse() {
		return isStateAfterFreightFalse;
	}

	public void setStateAfterFreightFalse(boolean isStateAfterFreightFalse) {
		this.isStateAfterFreightFalse = isStateAfterFreightFalse;
	}

	@Override
	public String toString() {
		return "VehicleFilter [vehicleLicensePlate=" + vehicleLicensePlate + ", vehicleModel=" + vehicleModel
				+ ", isFetchDriver=" + isFetchDriver + ", isFetchVehicleType=" + isFetchVehicleType + ", driver="
				+ driver + ", vehicleType=" + vehicleType + ", isStateTrue=" + isStateTrue + ", isStateFalse="
				+ isStateFalse + ", isStateFreeTrue=" + isStateFreeTrue + ", isStateFreeFalse=" + isStateFreeFalse
				+ ", isStateAfterFreightTrue=" + isStateAfterFreightTrue + ", isStateAfterFreightFalse="
				+ isStateAfterFreightFalse + "]";
	}
}
