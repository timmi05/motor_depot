package com.mikhailau.training.motordepot.webapp.common.render;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import com.mikhailau.training.motordepot.datamodel.VehicleType;

public class VehicleTypeChoiceRenderer extends ChoiceRenderer<VehicleType> {

	public static final VehicleTypeChoiceRenderer INSTANCE = new VehicleTypeChoiceRenderer();

	private VehicleTypeChoiceRenderer() {
		super();
	}

	@Override
	public Object getDisplayValue(VehicleType object) {
		return object.getType();
	}

	@Override
	public String getIdValue(VehicleType object, int index) {
		return String.valueOf(object.getId());
	}
}