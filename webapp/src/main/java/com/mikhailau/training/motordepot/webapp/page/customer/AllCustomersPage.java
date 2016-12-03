package com.mikhailau.training.motordepot.webapp.page.customer;

import com.mikhailau.training.motordepot.webapp.page.AbstractPage;
import com.mikhailau.training.motordepot.webapp.page.customer.panel.CustomerListPanel;

public class AllCustomersPage extends AbstractPage {

	public AllCustomersPage() {
		super();

		add(new CustomerListPanel("list-panel"));
	}
}
