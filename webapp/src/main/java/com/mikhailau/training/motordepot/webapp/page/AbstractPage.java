package com.mikhailau.training.motordepot.webapp.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.mikhailau.training.motordepot.webapp.component.menu.MenuPanel;
import com.mikhailau.training.motordepot.webapp.page.customer.panel.MenuPanelCustomer;
import com.mikhailau.training.motordepot.webapp.page.dispatcher.panel.MenuPanelDispatcher;
import com.mikhailau.training.motordepot.webapp.page.driver.panel.MenuPanelDriver;

public abstract class AbstractPage extends WebPage {
	public AbstractPage() {
		super();
	}

	public AbstractPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new MenuPanel("menu-panel"));

		add(new MenuPanelCustomer("menu-panelCustomer"));
		add(new MenuPanelDispatcher("menu-panelDispatcher"));
		add(new MenuPanelDriver("menu-panelDriver"));
	}
}
