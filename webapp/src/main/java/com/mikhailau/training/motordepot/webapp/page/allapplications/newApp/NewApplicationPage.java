package com.mikhailau.training.motordepot.webapp.page.allapplications.newApp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.validation.validator.RangeValidator;

import com.mikhailau.training.motordepot.dataaccess.filters.CustomerFilter;
import com.mikhailau.training.motordepot.dataaccess.filters.VehicleTypeFilter;
import com.mikhailau.training.motordepot.datamodel.Application;
import com.mikhailau.training.motordepot.datamodel.ApplicationState;
import com.mikhailau.training.motordepot.datamodel.Customer;
import com.mikhailau.training.motordepot.datamodel.Role;
import com.mikhailau.training.motordepot.datamodel.VehicleType;
import com.mikhailau.training.motordepot.service.ApplicationService;
import com.mikhailau.training.motordepot.service.CustomerService;
import com.mikhailau.training.motordepot.service.VehicleTypeService;
import com.mikhailau.training.motordepot.webapp.app.AuthorizedSession;
import com.mikhailau.training.motordepot.webapp.common.render.ApplicationStateChoiceRenderer;
import com.mikhailau.training.motordepot.webapp.common.render.CustomerChoiceRenderer;
import com.mikhailau.training.motordepot.webapp.common.render.VehicleTypeChoiceRenderer;
import com.mikhailau.training.motordepot.webapp.page.AbstractPage;
import com.mikhailau.training.motordepot.webapp.page.customer.CustomerPage;
import com.mikhailau.training.motordepot.webapp.page.dispatcher.DispatcherPage;

public class NewApplicationPage extends AbstractPage {
	@Inject
	private ApplicationService applicationService;

	@Inject
	private VehicleTypeService vehicleTypeService;

	@Inject
	private CustomerService customerService;

	private CustomerFilter customerFilter;
	private Application application;
	private List<VehicleType> vehicleTypes;
	private List<Customer> customers;
	private List<ApplicationState> applicationStateTemp;

	public NewApplicationPage(Application application) {
		super();

		this.application = application;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		Form<Application> form = new ApplicationForm<Application>("form",
				new CompoundPropertyModel<Application>(application));
		add(form);

		VehicleTypeFilter vehicleTypeFilter = new VehicleTypeFilter();
		vehicleTypeFilter.setExistsTrue(true);
		vehicleTypes = vehicleTypeService.find(vehicleTypeFilter);
		DropDownChoice<VehicleType> typeField = new DropDownChoice<>("type",
				new PropertyModel(application, "vehicleType"), vehicleTypes, VehicleTypeChoiceRenderer.INSTANCE);

		applicationStateTemp = new ArrayList<>(
				Arrays.asList(ApplicationState.HANDLING, ApplicationState.TURN, ApplicationState.REFUSAL));
		DropDownChoice<ApplicationState> stateField = new DropDownChoice<>("applicationState", applicationStateTemp,
				ApplicationStateChoiceRenderer.INSTANCE);

		customers = customerService.getAll();
		DropDownChoice<Customer> customerField = new DropDownChoice<>("customer",
				new PropertyModel(application, "customer"), customers, CustomerChoiceRenderer.INSTANCE);

		if (AuthenticatedWebSession.get().isSignedIn()
				&& (AuthorizedSession.get().getRoles().contains(Role.DRIVER.name())
						|| AuthorizedSession.get().getRoles().contains(Role.DISPATCHER.name()))) {
			stateField.setVisible(true);
		} else {
			stateField.setVisible(false);
			application.setApplicationState(ApplicationState.HANDLING);
		}

		if (AuthenticatedWebSession.get().isSignedIn()
				&& AuthorizedSession.get().getRoles().contains(Role.DISPATCHER.name())) {
			customerField.setVisible(true);
		} else {
			customerField.setVisible(false);
			customerFilter = new CustomerFilter();
			customerFilter.setLogin(AuthorizedSession.get().getLoggedUser().getLogin());
			application.setCustomer(customerService.find(customerFilter).get(0));
		}

		TextField<String> loadingAddressField = new TextField<>("loadingAddress");
		loadingAddressField.setRequired(true);
		loadingAddressField.setLabel(new ResourceModel("application.loading.address"));
		form.add(loadingAddressField);

		typeField.setRequired(true);
		typeField.setLabel(new ResourceModel("application.type"));
		form.add(typeField);

		customerField.setRequired(true);
		customerField.setLabel(new ResourceModel("application.сustomer"));
		form.add(customerField);

		stateField.setRequired(true);
		stateField.setLabel(new ResourceModel("application.state"));
		form.add(stateField);

		TextField<Double> weightField = new TextField<>("weight");
		weightField.add(RangeValidator.<Double> range(0d, 50d));
		form.add(weightField);

		TextField<Integer> numberOfPalletsField = new TextField<>("numberOfPallets");
		numberOfPalletsField.add(RangeValidator.<Integer> range(0, 30));
		form.add(numberOfPalletsField);

		TextField<String> unloadingAddressField = new TextField<>("unloadingAddress");
		form.add(unloadingAddressField);

		form.add(new SubmitLink("save") {
			@Override
			public void onSubmit() {
				super.onSubmit();

				if (application.getId() == null) {
					application.setReceiptTime(new Date());
					applicationService.insert(application);
				} else {
					applicationService.update(application);
				}
				
				setResponsePage((AuthorizedSession.get().getRoles().contains(Role.DISPATCHER.name())) ? new DispatcherPage() : new CustomerPage());
			}
		});
		add(new FeedbackPanel("feedback"));
	}

	private class ApplicationForm<T> extends Form<T> {
		public ApplicationForm(String id, IModel<T> model) {
			super(id, model);
		}
	}
}
