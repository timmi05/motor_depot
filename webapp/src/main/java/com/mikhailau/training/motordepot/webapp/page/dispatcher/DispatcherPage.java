package com.mikhailau.training.motordepot.webapp.page.dispatcher;

import com.mikhailau.training.motordepot.webapp.page.AbstractPage;
import com.mikhailau.training.motordepot.webapp.page.dispatcher.panel.DispatcherListPanel;

public class DispatcherPage extends AbstractPage {

	public DispatcherPage() {
		super();

		add(new DispatcherListPanel("list-panel"));
	}
}
