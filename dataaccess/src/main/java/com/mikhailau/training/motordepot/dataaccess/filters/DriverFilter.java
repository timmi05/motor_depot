package com.mikhailau.training.motordepot.dataaccess.filters;

import java.util.List;

import com.mikhailau.training.motordepot.datamodel.CategoryLicense;

public class DriverFilter extends AbstractFilter {
	private String driverFName;
	private String driverLName;
	private String login;
	private boolean isFetchСredentials;
	private boolean isFetchCategories;
	private List<CategoryLicense> categories;
	private boolean isStateFreeTrue;
	private boolean isStateFreeFalse;

	public String getDriverFName() {
		return driverFName;
	}

	public void setDriverFName(String driverFName) {
		this.driverFName = driverFName;
	}

	public String getDriverLName() {
		return driverLName;
	}

	public void setDriverLName(String driverLName) {
		this.driverLName = driverLName;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public boolean isFetchСredentials() {
		return isFetchСredentials;
	}

	public void setFetchСredentials(boolean isFetchСredentials) {
		this.isFetchСredentials = isFetchСredentials;
	}

	public boolean isFetchCategories() {
		return isFetchCategories;
	}

	public void setFetchCategories(boolean isFetchCategories) {
		this.isFetchCategories = isFetchCategories;
	}

	public List<CategoryLicense> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryLicense> categories) {
		this.categories = categories;
	}

	public boolean isStateFreeTrue() {
		return isStateFreeTrue;
	}

	public void setStateFreeTrue(boolean stateFreeTrue) {
		this.isStateFreeTrue = stateFreeTrue;
	}

	public boolean isStateFreeFalse() {
		return isStateFreeFalse;
	}

	public void setStateFreeFalse(boolean stateFreeFalse) {
		this.isStateFreeFalse = stateFreeFalse;
	}

	@Override
	public String toString() {
		return "DriverFilter [driverFName=" + driverFName + ", driverLName=" + driverLName + ", login=" + login
				+ ", isFetchСredentials=" + isFetchСredentials + ", isFetchCategories=" + isFetchCategories
				+ ", categories=" + categories + ", stateFreeTrue=" + isStateFreeTrue + ", stateFreeFalse="
				+ isStateFreeFalse + "]";
	}
}
