package com.mikhailau.training.motordepot.webapp.common.render;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import com.mikhailau.training.motordepot.datamodel.Driver;

public class DriverChoiceRenderer extends ChoiceRenderer<Driver> {

	public static final DriverChoiceRenderer INSTANCE = new DriverChoiceRenderer();

	private DriverChoiceRenderer() {
		super();
	}

	@Override
	public Object getDisplayValue(Driver object) {
		return String.format("%s %s", object.getLastName(), object.getFirstName());
	}

	@Override
	public String getIdValue(Driver object, int index) {
		return String.valueOf(object.getId());
	}
}