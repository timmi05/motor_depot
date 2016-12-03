package com.mikhailau.training.motordepot.webapp.common.render;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import com.mikhailau.training.motordepot.datamodel.CategoryLicense;

public class CategoryLicenseChoiceRenderer extends ChoiceRenderer<CategoryLicense> {

	public static final CategoryLicenseChoiceRenderer INSTANCE = new CategoryLicenseChoiceRenderer();

	private CategoryLicenseChoiceRenderer() {
		super();
	}

	@Override
	public Object getDisplayValue(CategoryLicense object) {
		return object.getCategory();
	}

	@Override
	public String getIdValue(CategoryLicense object, int index) {
		return String.valueOf(object.getCategory());
	}
}
