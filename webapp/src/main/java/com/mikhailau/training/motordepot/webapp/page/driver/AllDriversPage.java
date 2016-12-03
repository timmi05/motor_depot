package com.mikhailau.training.motordepot.webapp.page.driver;

import com.mikhailau.training.motordepot.webapp.page.AbstractPage;
import com.mikhailau.training.motordepot.webapp.page.driver.panel.DriverListPanel;

public class AllDriversPage extends AbstractPage {

	public AllDriversPage() {
		super();

		add(new DriverListPanel("list-panel"));
	}
}
