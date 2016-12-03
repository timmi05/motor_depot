package com.mikhailau.training.motordepot.webapp.page.dispatcher.panel;

import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import com.mikhailau.training.motordepot.datamodel.Application;
import com.mikhailau.training.motordepot.webapp.page.allapplications.AllApplicationPage;
import com.mikhailau.training.motordepot.webapp.page.allapplications.newApp.NewApplicationPage;
import com.mikhailau.training.motordepot.webapp.page.customer.AllCustomersPage;
import com.mikhailau.training.motordepot.webapp.page.customer.registration.RegistrationCustomerPage;
import com.mikhailau.training.motordepot.webapp.page.dispatcher.DispatcherPage;
import com.mikhailau.training.motordepot.webapp.page.driver.AllDriversPage;
import com.mikhailau.training.motordepot.webapp.page.driver.registration.RegistrationDriverPage;

@AuthorizeAction(roles = { "DISPATCHER" }, action = Action.RENDER)
public class MenuPanelDispatcher extends Panel {

	public MenuPanelDispatcher(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new Link("linkDispatcher") {
			@Override
			public void onClick() {
				setResponsePage(new DispatcherPage());
			}
		});

		add(new Link("linkAllApplications") {
			@Override
			public void onClick() {
				setResponsePage(new AllApplicationPage());
			}
		});

		add(new Link("linkAllDrivers") {
			@Override
			public void onClick() {
				setResponsePage(new AllDriversPage());
			}
		});

		add(new Link("linkAllCustomers") {
			@Override
			public void onClick() {
				setResponsePage(new AllCustomersPage());
			}
		});

		add(new Link("linkRegistrationCustomer") {
			@Override
			public void onClick() {
				setResponsePage(new RegistrationCustomerPage());
			}
		});

		add(new Link("linkRegistrationDriver") {
			@Override
			public void onClick() {
				setResponsePage(new RegistrationDriverPage());
			}
		});

		add(new Link("linkNewApplication") {
			@Override
			public void onClick() {
				setResponsePage(new NewApplicationPage(new Application()));
			}
		});
	}
}
