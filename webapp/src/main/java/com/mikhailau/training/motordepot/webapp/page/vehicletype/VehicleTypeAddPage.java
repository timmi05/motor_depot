package com.mikhailau.training.motordepot.webapp.page.vehicletype;

import javax.inject.Inject;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;

import com.mikhailau.training.motordepot.datamodel.Credentials;
import com.mikhailau.training.motordepot.datamodel.Driver;
import com.mikhailau.training.motordepot.datamodel.Vehicle;
import com.mikhailau.training.motordepot.datamodel.VehicleType;
import com.mikhailau.training.motordepot.service.VehicleTypeService;
import com.mikhailau.training.motordepot.webapp.page.AbstractPage;
import com.mikhailau.training.motordepot.webapp.page.driver.registration.RegistrationDriverPage;

public class VehicleTypeAddPage extends AbstractPage {
	@Inject
	private VehicleTypeService vehicleTypeService;

	private VehicleType vehicleType;
	private Credentials сredentials;
	private Driver driver;
	private Vehicle vehicle;
	private boolean b = false;

	public VehicleTypeAddPage(VehicleType vehicleType, boolean b, Credentials сredentials, Driver driver,
			Vehicle vehicle) {
		super();

		this.vehicleType = vehicleType;
		this.b = b;
		this.vehicle = vehicle;
		this.driver = driver;
		this.сredentials = сredentials;
	}

	public VehicleTypeAddPage(VehicleType vehicleType) {
		super();
		this.vehicleType = vehicleType;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		Form form = new Form("form", new CompoundPropertyModel<VehicleType>(vehicleType));
		add(form);
		Label editLable = new Label("title-edit");
		Label addLable = new Label("title-add");

		if (b) {
			editLable.setVisible(false);
			addLable.setVisible(true);
		} else {
			editLable.setVisible(true);
			addLable.setVisible(false);
		}
		add(editLable);
		add(addLable);

		TextField<String> typeField = new TextField<>("type");
		typeField.setRequired(true);
		typeField.setLabel(new ResourceModel("vehicle.type"));
		form.add(typeField);

		CheckBox activeField = new CheckBox("exists");
		form.add(activeField);

		form.add(new SubmitLink("save") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				vehicleTypeService.saveOrUpdate(vehicleType);
				
				setResponsePage(b ? new RegistrationDriverPage(сredentials, driver, vehicle) : new VehicleTypePage());				
			}
		});

		add(new FeedbackPanel("feedback"));
	}
}
