package com.mikhailau.training.motordepot.dataaccess.filters;

public class CredentialsFilter extends AbstractFilter {
	private boolean isCredentials;
	private boolean isRegistration;

	public boolean isCredentials() {
		return isCredentials;
	}

	public void setCredentials(boolean isCredentials) {
		this.isCredentials = isCredentials;
	}

	public boolean isRegistration() {
		return isRegistration;
	}

	public void setRegistration(boolean isRegistration) {
		this.isRegistration = isRegistration;
	}

	@Override
	public String toString() {
		return "CredentialsFilter [isCredentials=" + isCredentials + ", isRegistration=" + isRegistration + "]";
	}
}
