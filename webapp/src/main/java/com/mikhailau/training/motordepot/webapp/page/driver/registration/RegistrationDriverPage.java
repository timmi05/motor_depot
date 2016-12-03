package com.mikhailau.training.motordepot.webapp.page.driver.registration;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.validation.validator.RangeValidator;

import com.mikhailau.training.motordepot.dataaccess.filters.CredentialsFilter;
import com.mikhailau.training.motordepot.dataaccess.filters.DriverFilter;
import com.mikhailau.training.motordepot.datamodel.CategoryLicense;
import com.mikhailau.training.motordepot.datamodel.Credentials;
import com.mikhailau.training.motordepot.datamodel.Driver;
import com.mikhailau.training.motordepot.datamodel.Role;
import com.mikhailau.training.motordepot.datamodel.Vehicle;
import com.mikhailau.training.motordepot.datamodel.VehicleType;
import com.mikhailau.training.motordepot.service.CategoryLicenseService;
import com.mikhailau.training.motordepot.service.CredentialsService;
import com.mikhailau.training.motordepot.service.DriverService;
import com.mikhailau.training.motordepot.service.VehicleService;
import com.mikhailau.training.motordepot.service.VehicleTypeService;
import com.mikhailau.training.motordepot.webapp.common.render.CategoryLicenseChoiceRenderer;
import com.mikhailau.training.motordepot.webapp.common.render.VehicleTypeChoiceRenderer;
import com.mikhailau.training.motordepot.webapp.page.AbstractPage;
import com.mikhailau.training.motordepot.webapp.page.categorylicense.CategoryLicenseAddPage;
import com.mikhailau.training.motordepot.webapp.page.driver.AllDriversPage;
import com.mikhailau.training.motordepot.webapp.page.vehicletype.VehicleTypeAddPage;

public class RegistrationDriverPage extends AbstractPage {
	@Inject
	private DriverService driverService;

	@Inject
	private CredentialsService сredentialsService;

	@Inject
	private VehicleService vehicleService;

	@Inject
	private VehicleTypeService vehicleTypeService;
	@Inject
	private CategoryLicenseService categoryLicenseService;

	private Credentials сredentials;
	private Driver driver;
	private Vehicle vehicle;
	private VehicleType vehicleType;

	private List<VehicleType> vehicleTypes;
	private List<CategoryLicense> categories;
	private boolean b = true;

	public RegistrationDriverPage() {
		super();

		сredentials = new Credentials();
		driver = new Driver();
		vehicle = new Vehicle();
		vehicleType = new VehicleType();
	}

	public RegistrationDriverPage(Vehicle vehicle) {
		super();

		Long id = vehicle.getId();
		this.vehicle = vehicleService.get(id);
		this.driver = this.vehicle.getDriver();
		this.сredentials = this.driver.getCredentials();
	}

	public RegistrationDriverPage(Credentials сredentials, Driver driver, Vehicle vehicle) {
		super();

		this.vehicle = vehicle;
		this.driver = driver;
		this.сredentials = сredentials;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		Form<?> form = new Form<Void>("form");
		add(form);

		TextField<String> licensePlateField = new TextField<String>("licensePlate",
				new PropertyModel(vehicle, "licensePlate"));
		licensePlateField.setRequired(true);
		licensePlateField.setLabel(new ResourceModel("license.plate"));
		form.add(licensePlateField);

		TextField<Double> maxWeightField = new TextField<Double>("maxWeight", new PropertyModel(vehicle, "maxWeight"));
		maxWeightField.setRequired(true);
		maxWeightField.add(RangeValidator.<Double> range(0d, 50d));
		maxWeightField.setLabel(new ResourceModel("max.weight"));
		form.add(maxWeightField);

		TextField<Integer> numberOfPalletsField = new TextField<Integer>("numberOfPallets",
				new PropertyModel(vehicle, "numberOfPallets"));
		numberOfPalletsField.add(RangeValidator.<Integer> range(0, 30));
		numberOfPalletsField.setLabel(new ResourceModel("application.numberpallets"));
		form.add(numberOfPalletsField);

		vehicleTypes = vehicleTypeService.getAll();
		DropDownChoice<VehicleType> typeField = new DropDownChoice<>("type", new PropertyModel(vehicle, "vehicleType"),
				vehicleTypes, VehicleTypeChoiceRenderer.INSTANCE);
		typeField.setRequired(true);
		typeField.setLabel(new ResourceModel("vehicle.type"));
		form.add(typeField);

		TextField<String> loginField = new TextField<String>("login", new PropertyModel(сredentials, "login"));
		loginField.setRequired(true);
		loginField.setLabel(new ResourceModel("login.login"));

		if (vehicle.getId() == null) {
			loginField.setEnabled(true);
		} else {
			loginField.setEnabled(false);
		}
		form.add(loginField);

		TextField<String> passwordField = new TextField<String>("password", new PropertyModel(сredentials, "password"));
		passwordField.setRequired(true);
		passwordField.setLabel(new ResourceModel("login.password"));
		form.add(passwordField);

		TextField<String> lastNameField = new TextField<String>("lastName", new PropertyModel(driver, "lastName"));
		lastNameField.setRequired(true);
		lastNameField.setLabel(new ResourceModel("driver.lname"));
		form.add(lastNameField);

		TextField<String> firstNameField = new TextField<String>("firstName", new PropertyModel(driver, "firstName"));
		firstNameField.setRequired(true);
		firstNameField.setLabel(new ResourceModel("driver.fname"));
		form.add(firstNameField);

		categories = categoryLicenseService.getAll();
		CheckBoxMultipleChoice<CategoryLicense> listCategories = new CheckBoxMultipleChoice<CategoryLicense>(
				"categories", new PropertyModel(driver, "categoryLicense"), categories,
				CategoryLicenseChoiceRenderer.INSTANCE);
		listCategories.setRequired(true);
		listCategories.setLabel(new ResourceModel("category.license"));
		form.add(listCategories);

		TextField<String> modelField = new TextField<String>("model", new PropertyModel(vehicle, "model"));
		modelField.setRequired(true);
		modelField.setLabel(new ResourceModel("vehicle.model"));
		form.add(modelField);

		form.add(new CheckBox("driverState", new PropertyModel(driver, "stateFree")));
		form.add(new CheckBox("vehicleState", new PropertyModel(vehicle, "stateAfterFreight")));

		form.add(new Link("create") {
			@Override
			public void onClick() {
				setResponsePage(new VehicleTypeAddPage(new VehicleType(), b, сredentials, driver, vehicle));
			}
		});

		form.add(new Link("create-category") {
			@Override
			public void onClick() {
				setResponsePage(new CategoryLicenseAddPage(new CategoryLicense(), сredentials, driver, vehicle));
			}
		});

		form.add(new SubmitLink("save") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				CredentialsFilter entryfilter = new CredentialsFilter();
				entryfilter.setCredentials(false);
				entryfilter.setRegistration(true);

				DriverFilter driverFilter = new DriverFilter();
				driverFilter.setDriverLName(driver.getLastName());
				driverFilter.setDriverFName(driver.getFirstName());

				if (vehicle.getId() == null) {
					if (сredentialsService.find(сredentials.getLogin(), entryfilter) != null) {
						error(getString("loging.err.add"));
					} else if (!(driverService.find(driverFilter).isEmpty())) {
						error(getString("lname.err.add"));
					} else {
						сredentials.setRole(Role.DRIVER);
						driverService.registerDriver(vehicle, driver, сredentials);
						setResponsePage(new AllDriversPage());
					}
				} else {
					if (сredentialsService.find(сredentials.getLogin(), entryfilter) != null && (!(сredentials.getId()
							.equals(сredentialsService.find(сredentials.getLogin(), entryfilter).getId())))) {
						error(getString("loging.err.add"));
					} else if (!(driverService.find(driverFilter).isEmpty())
							&& (!(сredentials.getId().equals(driverService.find(driverFilter).get(0).getId())))) {
						error(getString("lname.err.add"));
					} else {
						сredentialsService.update(сredentials);
						driverService.update(driver);
						vehicleService.update(vehicle);
						setResponsePage(new AllDriversPage());
					}
				}
			}
		});
		add(new FeedbackPanel("feedback"));
	}
}
