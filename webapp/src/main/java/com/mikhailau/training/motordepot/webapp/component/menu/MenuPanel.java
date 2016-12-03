package com.mikhailau.training.motordepot.webapp.component.menu;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import com.mikhailau.training.motordepot.webapp.component.localization.LanguageSelectionComponent;
import com.mikhailau.training.motordepot.webapp.page.home.HomePage;
import com.mikhailau.training.motordepot.webapp.page.login.LoginPage;
import com.mikhailau.training.motordepot.webapp.page.vehicletype.VehicleTypePage;

public class MenuPanel extends Panel {

	public MenuPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new Link("link-home") {
			@Override
			public void onClick() {
				setResponsePage(new HomePage());
			}
		});

		add(new Link("link-vehicleType") {
			@Override
			public void onClick() {
				setResponsePage(new VehicleTypePage());
			}
		});

		Link exitLink = new Link("link-exit") {
			@Override
			public void onClick() {
				getSession().invalidate();
				setResponsePage(new HomePage());
			}
		};
		add(exitLink);

		Link loginLink = new Link("link-login") {
			@Override
			public void onClick() {
				setResponsePage(new LoginPage());
			}
		};
		add(loginLink);

		if (AuthenticatedWebSession.get().isSignedIn()) {
			exitLink.setVisible(true);
			loginLink.setVisible(false);
		} else {
			exitLink.setVisible(false);
			loginLink.setVisible(true);
		}

		add(new LanguageSelectionComponent("language-select"));
	}
}
