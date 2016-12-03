package com.mikhailau.training.motordepot.webapp.page.customer;

import com.mikhailau.training.motordepot.webapp.page.AbstractPage;
import com.mikhailau.training.motordepot.webapp.page.customer.panel.CustomerApplicationListPanel;

public class CustomerPage extends AbstractPage {

	public CustomerPage() {
		super();

		add(new CustomerApplicationListPanel("list-panel"));
	}
}
