package com.mikhailau.training.motordepot.webapp.common.render;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import com.mikhailau.training.motordepot.datamodel.Customer;

public class CustomerChoiceRenderer extends ChoiceRenderer<Customer> {

	public static final CustomerChoiceRenderer INSTANCE = new CustomerChoiceRenderer();

	private CustomerChoiceRenderer() {
		super();
	}

	@Override
	public Object getDisplayValue(Customer object) {
		return object.getLastName();
	}

	@Override
	public String getIdValue(Customer object, int index) {
		return String.valueOf(object.getId());
	}
}