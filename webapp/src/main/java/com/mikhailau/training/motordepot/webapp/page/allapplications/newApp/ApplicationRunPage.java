package com.mikhailau.training.motordepot.webapp.page.allapplications.newApp;

import java.io.Serializable;
import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.mikhailau.training.motordepot.dataaccess.filters.VehicleFilter;
import com.mikhailau.training.motordepot.datamodel.Application;
import com.mikhailau.training.motordepot.datamodel.ApplicationState;
import com.mikhailau.training.motordepot.datamodel.Driver;
import com.mikhailau.training.motordepot.datamodel.Driver_;
import com.mikhailau.training.motordepot.datamodel.Vehicle;
import com.mikhailau.training.motordepot.datamodel.VehicleType_;
import com.mikhailau.training.motordepot.datamodel.Vehicle_;
import com.mikhailau.training.motordepot.service.ApplicationService;
import com.mikhailau.training.motordepot.service.VehicleService;
import com.mikhailau.training.motordepot.webapp.page.AbstractPage;
import com.mikhailau.training.motordepot.webapp.page.dispatcher.DispatcherPage;

public class ApplicationRunPage extends AbstractPage {
	@Inject
	private ApplicationService applicationService;

	@Inject
	private VehicleService vehicleService;

	private Application application;
	private Driver driver;

	public ApplicationRunPage(Application application) {
		super();

		this.application = application;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		Form<Application> form = new ApplicationForm<Application>("form",
				new CompoundPropertyModel<Application>(application));
		add(form);
		form.add(new Label("state", application.getApplicationState()));
		form.add(new Label("customerLastName", application.getCustomer().getLastName()));
		form.add(new Label("type", application.getVehicleType().getType()));
		form.add(new Label("loadingAddress", application.getLoadingAddress()));
		form.add(new Label("unloadingAddress", application.getUnloadingAddress()));
		form.add(new Label("weight", application.getWeight()));
		form.add(new Label("numberOfPallets", application.getNumberOfPallets()));
		form.add(DateLabel.forDatePattern("receiptTime", Model.of(application.getReceiptTime()), "dd-MM-yyyy"));

		VehiclesDataProvider vehiclesDataProvider = new VehiclesDataProvider();
		DataView<Vehicle> dataView = new DataView<Vehicle>("rows", vehiclesDataProvider, 8) {
			@Override
			protected void populateItem(Item<Vehicle> item) {
				Vehicle vehicle = item.getModelObject();
				item.add(new Label("lastName", vehicle.getDriver().getLastName()));
				item.add(new Label("firstName", vehicle.getDriver().getFirstName()));
				item.add(DateLabel.forDatePattern("leadTime", Model.of(vehicle.getDriver().getLeadTime()),
						"dd-MM-yyyy   HH:mm"));
				item.add(new Label("model", vehicle.getModel()));
				item.add(new Label("type", vehicle.getVehicleType().getType()));
				item.add(new Label("weight", vehicle.getMaxWeight()));
				item.add(new Label("pallets", vehicle.getNumberOfPallets()));
				item.add(new Link<Void>("edit-link") {
					@Override
					public void onClick() {
						application.setApplicationState(ApplicationState.RUN);
						driver = vehicle.getDriver();
						application.setDriver(driver);
						vehicle.setStateAfterFreight(false);
						applicationService.update(application);
						vehicleService.update(vehicle);
						setResponsePage(new DispatcherPage());
					}
				});
			}
		};
		add(dataView);
		add(new PagingNavigator("paging", dataView));
		add(new OrderByBorder("sort-lastName", Driver_.lastName, vehiclesDataProvider));
		add(new OrderByBorder("sort-firstName", Driver_.firstName, vehiclesDataProvider));
		add(new OrderByBorder("sort-leadTime", Driver_.leadTime, vehiclesDataProvider));
		add(new OrderByBorder("sort-model", Vehicle_.model, vehiclesDataProvider));
		add(new OrderByBorder("sort-type", VehicleType_.type, vehiclesDataProvider));
		add(new OrderByBorder("sort-weight", Vehicle_.maxWeight, vehiclesDataProvider));
		add(new OrderByBorder("sort-pallets", Vehicle_.numberOfPallets, vehiclesDataProvider));
	}

	private class VehiclesDataProvider extends SortableDataProvider<Vehicle, Serializable> {

		private VehicleFilter vehicleFilter;

		public VehiclesDataProvider() {
			super();

			vehicleFilter = new VehicleFilter();
			vehicleFilter.setFetchDriver(true);
			vehicleFilter.setFetchVehicleType(true);
			vehicleFilter.setStateTrue(true);
			setSort((Serializable) Vehicle_.model, SortOrder.ASCENDING);
		}

		@Override
		public Iterator<Vehicle> iterator(long first, long count) {
			Serializable property = getSort().getProperty();
			SortOrder propertySortOrder = getSortState().getPropertySortOrder(property);
			vehicleFilter.setSortProperty((SingularAttribute) property);
			vehicleFilter.setSortOrder(propertySortOrder.equals(SortOrder.ASCENDING) ? true : false);
			vehicleFilter.setLimit((int) count);
			vehicleFilter.setOffset((int) first);

			return vehicleService.find(vehicleFilter).iterator();
		}

		@Override
		public long size() {
			return vehicleService.count(vehicleFilter);
		}

		@Override
		public IModel<Vehicle> model(Vehicle object) {
			return new Model(object);
		}

	}

	private class ApplicationForm<T> extends Form<T> {
		public ApplicationForm(String id, IModel<T> model) {
			super(id, model);
		}
	}
}
