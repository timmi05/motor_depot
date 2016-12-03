package com.mikhailau.training.motordepot.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class CategoryLicense extends AbstractModel {

	@Column(unique=true)
	private String category;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "CategoryLicense [category=" + category + "]";
	}
}
