package com.mikhailau.training.motordepot.webapp.page.customer.panel;

import javax.inject.Inject;

import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import com.mikhailau.training.motordepot.dataaccess.filters.CustomerFilter;
import com.mikhailau.training.motordepot.datamodel.Application;
import com.mikhailau.training.motordepot.service.CustomerService;
import com.mikhailau.training.motordepot.webapp.app.AuthorizedSession;
import com.mikhailau.training.motordepot.webapp.page.allapplications.AllApplicationPage;
import com.mikhailau.training.motordepot.webapp.page.allapplications.newApp.NewApplicationPage;
import com.mikhailau.training.motordepot.webapp.page.customer.CustomerPage;
import com.mikhailau.training.motordepot.webapp.page.customer.registration.RegistrationCustomerPage;

@AuthorizeAction(roles = { "CUSTOMER" }, action = Action.RENDER)
public class MenuPanelCustomer extends Panel {
	@Inject
	private CustomerService customerService;

	private CustomerFilter customerFilter;

	public MenuPanelCustomer(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new Link("linkHome") {
			@Override
			public void onClick() {
				setResponsePage(new CustomerPage());
			}
		});

		add(new Link("linkAllApplications") {
			@Override
			public void onClick() {
				setResponsePage(new AllApplicationPage());
			}
		});

		add(new Link("linkNewApplication") {
			@Override
			public void onClick() {
				setResponsePage(new NewApplicationPage(new Application()));
			}
		});

		add(new Link("link-editCustomer") {
			@Override
			public void onClick() {
				customerFilter = new CustomerFilter();
				customerFilter.setLogin(AuthorizedSession.get().getLoggedUser().getLogin());
				setResponsePage(new RegistrationCustomerPage(customerService.find(customerFilter).get(0)));
			}
		});
	}
}
