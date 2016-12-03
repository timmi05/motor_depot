package com.mikhailau.training.motordepot.webapp.page.allapplications.panel;

import java.io.Serializable;
import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.mikhailau.training.motordepot.dataaccess.filters.ApplicationFilter;
import com.mikhailau.training.motordepot.dataaccess.filters.CustomerFilter;
import com.mikhailau.training.motordepot.dataaccess.filters.DriverFilter;
import com.mikhailau.training.motordepot.datamodel.Application;
import com.mikhailau.training.motordepot.datamodel.ApplicationState;
import com.mikhailau.training.motordepot.datamodel.Application_;
import com.mikhailau.training.motordepot.datamodel.Customer_;
import com.mikhailau.training.motordepot.datamodel.Driver_;
import com.mikhailau.training.motordepot.datamodel.Role;
import com.mikhailau.training.motordepot.datamodel.VehicleType_;
import com.mikhailau.training.motordepot.service.ApplicationService;
import com.mikhailau.training.motordepot.service.CustomerService;
import com.mikhailau.training.motordepot.service.DriverService;
import com.mikhailau.training.motordepot.webapp.app.AuthorizedSession;
import com.mikhailau.training.motordepot.webapp.page.allapplications.AllApplicationPage;
import com.mikhailau.training.motordepot.webapp.page.allapplications.newApp.ApplicationRunPage;
import com.mikhailau.training.motordepot.webapp.page.allapplications.newApp.NewApplicationPage;
import com.mikhailau.training.motordepot.webapp.page.driver.DriverPage;

public class ApplicationListPanel extends Panel {
	@Inject
	private ApplicationService applicationService;

	@Inject
	private CustomerService customerService;

	@Inject
	private DriverService driverService;

	private CustomerFilter customerFilter;
	private DriverFilter driverFilter;

	public ApplicationListPanel(String id) {
		super(id);

		ApplicationsDataProvider applicationsDataProvider = new ApplicationsDataProvider();
		DataView<Application> dataView = new DataView<Application>("rows", applicationsDataProvider, 25) {
			@Override
			protected void populateItem(Item<Application> item) {
				Application application = item.getModelObject();

				item.add(new Label("state", application.getApplicationState()));
				item.add(new Label("customerLastName", application.getCustomer().getLastName()));
				item.add(new Label("type", application.getVehicleType().getType()));
				item.add(new Label("loadingAddress", application.getLoadingAddress()));
				item.add(new Label("unloadingAddress", application.getUnloadingAddress()));
				item.add(new Label("weight", application.getWeight()));
				item.add(new Label("numberOfPallets", application.getNumberOfPallets()));
				item.add(DateLabel.forDatePattern("receiptTime", Model.of(application.getReceiptTime()), "dd-MM-yyyy"));
				item.add(DateLabel.forDatePattern("leadTime", Model.of(application.getLeadTime()), "dd-MM-yyyy"));

				try {
					item.add(new Label("driverLastName", application.getDriver().getLastName()));
				} catch (NullPointerException ex) {
					item.add(new Label("driverLastName", ""));
				}

				Link<Void> editLink = new Link<Void>("edit-link") {
					@Override
					public void onClick() {
						if (application.getApplicationState() != ApplicationState.DONE) {
							if (application.getApplicationState() == ApplicationState.RUN) {
								setResponsePage(new DriverPage(application));
							} else if (application.getApplicationState() == ApplicationState.TURN) {
								setResponsePage(new ApplicationRunPage(application));
							} else {
								setResponsePage(new NewApplicationPage(application));
							}

						}
					}
				};

				Link<Void> deleteLink = new Link<Void>("delete-link") {
					@Override
					public void onClick() {
						try {
							applicationService.delete(application.getId());
						} catch (PersistenceException e) {
							System.out.println("caughth persistance exception");
						}

						setResponsePage(new AllApplicationPage());
					}
				};

				if (AuthorizedSession.get().getRoles().contains(Role.DISPATCHER.name())) {
					editLink.setVisible(true);
					deleteLink.setVisible(true);
				} else {
					editLink.setVisible(false);
					deleteLink.setVisible(false);
				}

				item.add(editLink);
				item.add(deleteLink);
			}
		};
		add(dataView);
		add(new PagingNavigator("paging", dataView));
		add(new OrderByBorder("sort-state", Application_.applicationState, applicationsDataProvider));
		add(new OrderByBorder("sort-customerlastName", Customer_.lastName, applicationsDataProvider));
		add(new OrderByBorder("sort-driverlastName", Driver_.lastName, applicationsDataProvider));
		add(new OrderByBorder("sort-vehicleType", VehicleType_.type, applicationsDataProvider));
		add(new OrderByBorder("sort-receiptTime", Application_.receiptTime, applicationsDataProvider));

		Label actionsLabel = new Label("actions");
		actionsLabel.setVisible(AuthorizedSession.get().getRoles().contains(Role.DISPATCHER.name()));
		add(actionsLabel);
	}

	private class ApplicationsDataProvider extends SortableDataProvider<Application, Serializable> {
		private ApplicationFilter applicationFilter;

		public ApplicationsDataProvider() {
			super();

			applicationFilter = new ApplicationFilter();
			applicationFilter.setFetchCustomer(true);
			applicationFilter.setFetchVehicleType(true);
			applicationFilter.setFetchDriver(true);

			if (AuthenticatedWebSession.get().isSignedIn()
					&& AuthorizedSession.get().getRoles().contains(Role.CUSTOMER.name())) {
				customerFilter = new CustomerFilter();
				customerFilter.setLogin(AuthorizedSession.get().getLoggedUser().getLogin());
				applicationFilter.setCustomer(customerService.find(customerFilter).get(0));
			} else if (AuthenticatedWebSession.get().isSignedIn()
					&& AuthorizedSession.get().getRoles().contains(Role.DRIVER.name())) {
				driverFilter = new DriverFilter();
				driverFilter.setLogin(AuthorizedSession.get().getLoggedUser().getLogin());
				applicationFilter.setDriver(driverService.find(driverFilter).get(0));
			}
			setSort((Serializable) Application_.receiptTime, SortOrder.ASCENDING);
		}

		@Override
		public Iterator<Application> iterator(long first, long count) {
			Serializable property = getSort().getProperty();
			SortOrder propertySortOrder = getSortState().getPropertySortOrder(property);

			applicationFilter.setSortProperty((SingularAttribute) property);
			applicationFilter.setSortOrder(propertySortOrder.equals(SortOrder.ASCENDING) ? true : false);
			applicationFilter.setLimit((int) count);
			applicationFilter.setOffset((int) first);

			return applicationService.find(applicationFilter).iterator();
		}

		@Override
		public long size() {
			return applicationService.count(applicationFilter);
		}

		@Override
		public IModel<Application> model(Application object) {
			return new Model(object);
		}
	}
}
