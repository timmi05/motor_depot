package com.mikhailau.training.motordepot.dataaccess.filters;

public class CategoryLicenseFilter extends AbstractFilter {
	private String сategory;

	public String getСategory() {
		return сategory;
	}

	public void setСategory(String сategory) {
		this.сategory = сategory;
	}

	@Override
	public String toString() {
		return "CategoryLicenseFilter [сategory=" + сategory + "]";
	}	
}
