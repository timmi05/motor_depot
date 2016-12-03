package com.mikhailau.training.motordepot.dataaccess.filters;

public class CustomerFilter extends AbstractFilter {
	private String customerLName;
	private String customerFName;
	private String login;
	private boolean isFetchСredentials;

	public String getCustomerFName() {
		return customerFName;
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

	public void setCustomerFName(String customerFName) {
		this.customerFName = customerFName;
	}

	public String getCustomerLName() {
		return customerLName;
	}

	public void setCustomerLName(String driverFName) {
		this.customerLName = driverFName;
	}

	@Override
	public String toString() {
		return "CustomerFilter [customerLName=" + customerLName + ", customerFName=" + customerFName + ", login="
				+ login + ", fetchСredentials=" + isFetchСredentials + "]";
	}
}
