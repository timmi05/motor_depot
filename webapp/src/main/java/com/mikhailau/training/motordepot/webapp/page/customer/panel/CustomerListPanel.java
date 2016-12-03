package com.mikhailau.training.motordepot.webapp.page.customer.panel;

import java.io.Serializable;
import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.metamodel.SingularAttribute;

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

import com.mikhailau.training.motordepot.dataaccess.filters.CustomerFilter;
import com.mikhailau.training.motordepot.datamodel.Customer;
import com.mikhailau.training.motordepot.datamodel.Customer_;
import com.mikhailau.training.motordepot.service.CustomerService;
import com.mikhailau.training.motordepot.webapp.page.customer.AllCustomersPage;
import com.mikhailau.training.motordepot.webapp.page.customer.registration.RegistrationCustomerPage;

public class CustomerListPanel extends Panel {
	@Inject
	private CustomerService customerService;

	public CustomerListPanel(String id) {
		super(id);

		CustomersDataProvider customersDataProvider = new CustomersDataProvider();
		DataView<Customer> dataView = new DataView<Customer>("rows", customersDataProvider, 5) {
			@Override
			protected void populateItem(Item<Customer> item) {
				Customer customer = item.getModelObject();

				item.add(new Label("lastName", customer.getLastName()));
				item.add(new Label("firstName", customer.getFirstName()));
				item.add(new Link<Void>("edit-link") {
					@Override
					public void onClick() {
						setResponsePage(new RegistrationCustomerPage(customer));
					}
				});
				Link<Void> deleteLink = new Link<Void>("delete-link") {
					@Override
					public void onClick() {
						try {
							customerService.delete(customer.getId());
							setResponsePage(new AllCustomersPage());
						} catch (PersistenceException e) {
							error(getString("category.err.add"));
						}
					}
				};
				item.add(deleteLink);
			}
		};
		add(dataView);
		add(new PagingNavigator("paging", dataView));
		add(new OrderByBorder("sort-lastName", Customer_.lastName, customersDataProvider));
		add(new OrderByBorder("sort-firstName", Customer_.firstName, customersDataProvider));
	}

	private class CustomersDataProvider extends SortableDataProvider<Customer, Serializable> {

		private CustomerFilter customerFilter;

		public CustomersDataProvider() {
			super();
			customerFilter = new CustomerFilter();
			setSort((Serializable) Customer_.lastName, SortOrder.ASCENDING);
		}

		@Override
		public Iterator<Customer> iterator(long first, long count) {
			Serializable property = getSort().getProperty();
			SortOrder propertySortOrder = getSortState().getPropertySortOrder(property);

			customerFilter.setSortProperty((SingularAttribute) property);
			customerFilter.setSortOrder(propertySortOrder.equals(SortOrder.ASCENDING) ? true : false);
			customerFilter.setLimit((int) count);
			customerFilter.setOffset((int) first);
		
			return customerService.find(customerFilter).iterator();
		}

		@Override
		public long size() {
			return customerService.count(customerFilter);
		}

		@Override
		public IModel<Customer> model(Customer object) {
			return new Model<Customer>(object);
		}
	}
}
