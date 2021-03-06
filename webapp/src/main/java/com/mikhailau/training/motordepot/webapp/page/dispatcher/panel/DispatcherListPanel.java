package com.mikhailau.training.motordepot.webapp.page.dispatcher.panel;

import java.io.Serializable;
import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

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
import com.mikhailau.training.motordepot.datamodel.Application;
import com.mikhailau.training.motordepot.datamodel.ApplicationState;
import com.mikhailau.training.motordepot.datamodel.Application_;
import com.mikhailau.training.motordepot.datamodel.Customer_;
import com.mikhailau.training.motordepot.datamodel.VehicleType_;
import com.mikhailau.training.motordepot.service.ApplicationService;
import com.mikhailau.training.motordepot.webapp.page.allapplications.newApp.ApplicationRunPage;
import com.mikhailau.training.motordepot.webapp.page.allapplications.newApp.NewApplicationPage;

public class DispatcherListPanel extends Panel {
	@Inject
	private ApplicationService applicationService;

	public DispatcherListPanel(String id) {
		super(id);

		ApplicationsDataProvider applicationsDataProvider = new ApplicationsDataProvider();
		DataView<Application> dataView = new DataView<Application>("rows", applicationsDataProvider, 5) {
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

				item.add(new Link<Void>("edit-link") {
					@Override
					public void onClick() {
						setResponsePage((application.getApplicationState() == ApplicationState.HANDLING)
								? new NewApplicationPage(application) : new ApplicationRunPage(application));
					}
				});
			}
		};
		add(dataView);
		add(new PagingNavigator("paging", dataView));
		add(new OrderByBorder("sort-state", Application_.applicationState, applicationsDataProvider));
		add(new OrderByBorder("sort-customerlastName", Customer_.lastName, applicationsDataProvider));
		add(new OrderByBorder("sort-vehicleType", VehicleType_.type, applicationsDataProvider));
		add(new OrderByBorder("sort-receiptTime", Application_.receiptTime, applicationsDataProvider));
	}

	private class ApplicationsDataProvider extends SortableDataProvider<Application, Serializable> {

		private ApplicationFilter applicationFilter;

		public ApplicationsDataProvider() {
			super();

			applicationFilter = new ApplicationFilter();
			applicationFilter.setFetchCustomer(true);
			applicationFilter.setFetchVehicleType(true);
			applicationFilter.setFetchDriver(true);
			applicationFilter.addState(ApplicationState.HANDLING);
			applicationFilter.addState(ApplicationState.TURN);
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
