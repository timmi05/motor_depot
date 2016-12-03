package com.mikhailau.training.motordepot.webapp.page.driver.panel;

import java.io.Serializable;
import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.mikhailau.training.motordepot.dataaccess.filters.VehicleFilter;
import com.mikhailau.training.motordepot.datamodel.Driver_;
import com.mikhailau.training.motordepot.datamodel.Vehicle;
import com.mikhailau.training.motordepot.datamodel.VehicleType_;
import com.mikhailau.training.motordepot.datamodel.Vehicle_;
import com.mikhailau.training.motordepot.service.DriverService;
import com.mikhailau.training.motordepot.service.VehicleService;
import com.mikhailau.training.motordepot.webapp.page.driver.AllDriversPage;
import com.mikhailau.training.motordepot.webapp.page.driver.registration.RegistrationDriverPage;

public class DriverListPanel extends Panel {
	@Inject
	private VehicleService vehicleService;
	@Inject
	private DriverService driverService;

	public DriverListPanel(String id) {
		super(id);

		VehiclesDataProvider vehiclesDataProvider = new VehiclesDataProvider();
		DataView<Vehicle> dataView = new DataView<Vehicle>("rows", vehiclesDataProvider, 5) {
			@Override
			protected void populateItem(Item<Vehicle> item) {
				Vehicle vehicle = item.getModelObject();

				item.add(new Label("lastName", vehicle.getDriver().getLastName()));
				item.add(new Label("firstName", vehicle.getDriver().getFirstName()));
				item.add(DateLabel.forDatePattern("leadTime", Model.of(vehicle.getDriver().getLeadTime()),
						"dd-MM-yyyy"));

				CheckBox checkboxDriver = new CheckBox("stateFree", Model.of(vehicle.getDriver().getStateFree()));
				checkboxDriver.setEnabled(false);
				item.add(checkboxDriver);
				CheckBox checkboxVehicle = new CheckBox("stateAfterFreight", Model.of(vehicle.getStateAfterFreight()));
				checkboxVehicle.setEnabled(false);
				item.add(checkboxVehicle);
				item.add(new Label("model", vehicle.getModel()));
				item.add(new Label("licensePlate", vehicle.getLicensePlate()));
				item.add(new Label("type", vehicle.getVehicleType().getType()));
				item.add(new Label("weight", vehicle.getMaxWeight()));
				item.add(new Label("pallets", vehicle.getNumberOfPallets()));

				item.add(new Link<Void>("edit-link") {
					@Override
					public void onClick() {

						setResponsePage(new RegistrationDriverPage(vehicle));
					}
				});

				item.add(new Link<Void>("delete-link") {
					@Override
					public void onClick() {
						try {
							driverService.delete(vehicle.getId());
						} catch (PersistenceException e) {
							System.out.println("caughth persistance exception");
						}
						setResponsePage(new AllDriversPage());
					}
				});
			}
		};
		add(dataView);
		add(new PagingNavigator("paging", dataView));
		add(new OrderByBorder("sort-lastName", Driver_.lastName, vehiclesDataProvider));
		add(new OrderByBorder("sort-firstName", Driver_.firstName, vehiclesDataProvider));
		add(new OrderByBorder("sort-leadTime", Driver_.leadTime, vehiclesDataProvider));
		add(new OrderByBorder("sort-stateFree", Driver_.stateFree, vehiclesDataProvider));
		add(new OrderByBorder("sort-stateAfterFreight", Vehicle_.stateAfterFreight, vehiclesDataProvider));
		add(new OrderByBorder("sort-model", Vehicle_.model, vehiclesDataProvider));
		add(new OrderByBorder("sort-licensePlate", Vehicle_.id, vehiclesDataProvider));
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
}
