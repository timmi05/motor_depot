package com.mikhailau.training.motordepot.webapp.page.driver;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.mikhailau.training.motordepot.dataaccess.filters.ApplicationFilter;
import com.mikhailau.training.motordepot.dataaccess.filters.DriverFilter;
import com.mikhailau.training.motordepot.datamodel.Application;
import com.mikhailau.training.motordepot.datamodel.ApplicationState;
import com.mikhailau.training.motordepot.datamodel.Driver;
import com.mikhailau.training.motordepot.datamodel.Role;
import com.mikhailau.training.motordepot.datamodel.Vehicle;
import com.mikhailau.training.motordepot.service.ApplicationService;
import com.mikhailau.training.motordepot.service.DriverService;
import com.mikhailau.training.motordepot.service.VehicleService;
import com.mikhailau.training.motordepot.webapp.app.AuthorizedSession;
import com.mikhailau.training.motordepot.webapp.page.AbstractPage;
import com.mikhailau.training.motordepot.webapp.page.dispatcher.DispatcherPage;

public class DriverPage extends AbstractPage {
	@Inject
	private DriverService driverService;

	@Inject
	private VehicleService vehicleService;

	@Inject
	private ApplicationService applicationService;

	private Application application;
	private Driver driver;
	private Vehicle vehicle;
	private DriverFilter driverFilter;
	private List<Application> resultApplication;
	private ApplicationFilter applicationFilter;
	private boolean b = false;

	public DriverPage() {
		super();

		applicationFilter = new ApplicationFilter();
		driverFilter = new DriverFilter();
		applicationFilter.setFetchCustomer(true);
		applicationFilter.setFetchDriver(true);
		applicationFilter.setFetchVehicleType(true);
		driverFilter.setLogin(AuthorizedSession.get().getLoggedUser().getLogin());
		driver = driverService.find(driverFilter).get(0);
		vehicle = vehicleService.getVehicle(driver.getId());
		applicationFilter.setDriver(driver);
		applicationFilter.addState(ApplicationState.RUN);
		resultApplication = applicationService.find(applicationFilter);
		if (!resultApplication.isEmpty()) {
			application = resultApplication.get(0);
			b = true;
		}
	}

	public DriverPage(Application application) {
		super();

		b = true;
		this.application = application;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		Form<Application> form = new ApplicationForm<Application>("form",
				new CompoundPropertyModel<Application>(application));

		Form<?> form2 = new Form<Void>("form2");
		add(form);
		add(form2);

		if (b) {
			form2.setVisible(false);
			form.add(new Label("state", application.getApplicationState()));
			form.add(new Label("customerLastName", application.getCustomer().getLastName()));
			form.add(new Label("type", application.getVehicleType().getType()));
			form.add(new Label("loadingAddress", application.getLoadingAddress()));
			form.add(new Label("unloadingAddress", application.getUnloadingAddress()));
			form.add(new Label("weight", application.getWeight()));
			form.add(new Label("numberOfPallets", application.getNumberOfPallets()));
			form.add(DateLabel.forDatePattern("receiptTime", Model.of(application.getReceiptTime()), "dd-MM-yyyy"));

			form.add(new SubmitLink("done") {
				@Override
				public void onSubmit() {
					super.onSubmit();

					application.setLeadTime(new Date());
					application.setApplicationState(ApplicationState.DONE);
					applicationService.update(application);
					setResponsePage(AuthorizedSession.get().getRoles().contains(Role.DISPATCHER.name())
							? new DispatcherPage() : new DriverPage());
				}
			});

			form.add(new SubmitLink("refusal") {
				@Override
				public void onSubmit() {
					super.onSubmit();

					application.setLeadTime(new Date());
					application.setApplicationState(ApplicationState.REFUSAL);
					applicationService.update(application);
					setResponsePage(AuthorizedSession.get().getRoles().contains(Role.DISPATCHER.name())
							? new DispatcherPage() : new DriverPage());
				}
			});
		} else {
			form.setVisible(false);
			form2.add(new CheckBox("driverState", new PropertyModel(driver, "stateFree")));
			form2.add(new CheckBox("vehicleState", new PropertyModel(vehicle, "stateAfterFreight")));
			form2.add(new SubmitLink("change") {
				@Override
				public void onSubmit() {
					super.onSubmit();

					vehicleService.update(vehicle);
					driverService.update(driver);
					setResponsePage(new DriverPage());
				}
			});
		}
	}

	private class ApplicationForm<T> extends Form<T> {

		public ApplicationForm(String id, IModel<T> model) {
			super(id, model);
		}
	}
}
