package com.mikhailau.training.motordepot.webapp.page.categorylicense;

import javax.inject.Inject;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.mikhailau.training.motordepot.dataaccess.filters.CategoryLicenseFilter;
import com.mikhailau.training.motordepot.datamodel.CategoryLicense;
import com.mikhailau.training.motordepot.datamodel.Credentials;
import com.mikhailau.training.motordepot.datamodel.Driver;
import com.mikhailau.training.motordepot.datamodel.Vehicle;
import com.mikhailau.training.motordepot.service.CategoryLicenseService;
import com.mikhailau.training.motordepot.webapp.page.AbstractPage;
import com.mikhailau.training.motordepot.webapp.page.categorylicense.panel.CategoryLicenseRegistrationListPanel;
import com.mikhailau.training.motordepot.webapp.page.driver.registration.RegistrationDriverPage;

public class CategoryLicenseAddPage extends AbstractPage {
	@Inject
	private CategoryLicenseService categoryLicenseService;

	private CategoryLicense categoryLicense;
	private Credentials entry;
	private Driver driver;
	private Vehicle vehicle;

	public CategoryLicenseAddPage(PageParameters parameters) {
		super(parameters);
	}

	public CategoryLicenseAddPage(CategoryLicense categoryLicense, Credentials entry, Driver driver, Vehicle vehicle) {
		super();

		this.categoryLicense = categoryLicense;
		this.vehicle = vehicle;
		this.driver = driver;
		this.entry = entry;
	}

	public CategoryLicenseAddPage(CategoryLicense categoryLicense) {
		super();

		this.categoryLicense = categoryLicense;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		Form form = new Form("form", new CompoundPropertyModel<CategoryLicense>(categoryLicense));
		add(form);
		form.add(new CategoryLicenseRegistrationListPanel("category-list"));

		TextField<String> categoryField = new TextField<>("category");
		categoryField.setRequired(true);
		categoryField.setLabel(new ResourceModel("category.license"));
		form.add(categoryField);

		form.add(new SubmitLink("save") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				CategoryLicenseFilter categoryLicenseFilter = new CategoryLicenseFilter();
				categoryLicenseFilter.setСategory(categoryLicense.getCategory());
				if (categoryLicenseService.find(categoryLicenseFilter).isEmpty()) {
					categoryLicenseService.saveOrUpdate(categoryLicense);
					setResponsePage(new RegistrationDriverPage(entry, driver, vehicle));
				} else {
					error(getString("category.err.add"));
				}
			}
		});
		add(new FeedbackPanel("feedback"));
	}
}
