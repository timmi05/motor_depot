package com.mikhailau.training.motordepot.webapp.page.driver.panel;

import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import com.mikhailau.training.motordepot.webapp.page.allapplications.AllApplicationPage;
import com.mikhailau.training.motordepot.webapp.page.driver.DriverPage;

@AuthorizeAction(roles = { "DRIVER" }, action = Action.RENDER)
public class MenuPanelDriver extends Panel {

	public MenuPanelDriver(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new Link("linkHome") {
			@Override
			public void onClick() {
				setResponsePage(new DriverPage());
			}
		});

		add(new Link("linkAllApplications") {
			@Override
			public void onClick() {
				setResponsePage(new AllApplicationPage());
			}
		});
	}
}
