package com.mikhailau.training.motordepot.dataaccess.filters;

public class VehicleTypeFilter extends AbstractFilter {
	private String typeForFilter;
	private boolean isExistsTrue;

	public boolean isExistsTrue() {
		return isExistsTrue;
	}

	public void setExistsTrue(boolean isExistsTrue) {
		this.isExistsTrue = isExistsTrue;
	}

	public String getTypeForFilter() {
		return typeForFilter;
	}

	public void setTypeForFilter(String typeForFilter) {
		this.typeForFilter = typeForFilter;
	}

	@Override
	public String toString() {
		return "VehicleTypeFilter [typeForFilter=" + typeForFilter + ", existsTrue=" + isExistsTrue + "]";
	}
}
