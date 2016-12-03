package com.mikhailau.training.motordepot.webapp.page.vehicletype;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;

import com.mikhailau.training.motordepot.datamodel.Role;
import com.mikhailau.training.motordepot.webapp.app.AuthorizedSession;
import com.mikhailau.training.motordepot.webapp.page.AbstractPage;
import com.mikhailau.training.motordepot.webapp.page.vehicletype.panel.VehicleTypeListDispatcherPanel;
import com.mikhailau.training.motordepot.webapp.page.vehicletype.panel.VehicleTypeListPanel;

public class VehicleTypePage extends AbstractPage {

	public VehicleTypePage() {
		super();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add((AuthenticatedWebSession.get().isSignedIn()
				&& AuthorizedSession.get().getRoles().contains(Role.DISPATCHER.name()))
						? new VehicleTypeListDispatcherPanel("list-panel") : new VehicleTypeListPanel("list-panel"));
	}
}
