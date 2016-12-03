package com.mikhailau.training.motordepot.dataaccess.filters;

import java.io.Serializable;

import javax.persistence.metamodel.SingularAttribute;

public class AbstractFilter implements Serializable {
	private SingularAttribute sortProperty;
	private boolean sortOrder;
	private Integer offset;
	private Integer limit;

	public SingularAttribute getSortProperty() {
		return sortProperty;
	}

	public void setSortProperty(SingularAttribute sortProperty) {
		this.sortProperty = sortProperty;
	}

	public boolean isSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(boolean sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
}
