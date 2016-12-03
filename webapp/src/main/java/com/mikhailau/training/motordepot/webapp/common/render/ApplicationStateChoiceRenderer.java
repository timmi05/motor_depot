package com.mikhailau.training.motordepot.webapp.common.render;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import com.mikhailau.training.motordepot.datamodel.ApplicationState;

public class ApplicationStateChoiceRenderer extends ChoiceRenderer<ApplicationState> {

	public static final ApplicationStateChoiceRenderer INSTANCE = new ApplicationStateChoiceRenderer();

	private ApplicationStateChoiceRenderer() {
		super();
	}

	@Override
	public Object getDisplayValue(ApplicationState object) {
		return object.name();
	}

	@Override
	public String getIdValue(ApplicationState object, int index) {
		return String.valueOf(object.ordinal());
	}
}
