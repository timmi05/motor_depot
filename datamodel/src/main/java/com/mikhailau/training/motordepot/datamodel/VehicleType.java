package com.mikhailau.training.motordepot.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class VehicleType extends AbstractModel {

	@Column(unique = true)
	private String type;

	@Column
	private Boolean exists;

	public Boolean getExists() {
		return exists;
	}

	public void setExists(Boolean exists) {
		this.exists = exists;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "VehicleType [type=" + type + ", exists=" + exists + "]";
	}
}
