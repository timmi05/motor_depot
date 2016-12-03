package com.mikhailau.training.motordepot.webapp.page.allapplications;

import com.mikhailau.training.motordepot.webapp.page.AbstractPage;
import com.mikhailau.training.motordepot.webapp.page.allapplications.panel.ApplicationListPanel;

public class AllApplicationPage extends AbstractPage {

	public AllApplicationPage() {
		super();

		add(new ApplicationListPanel("list-panel"));
	}
}
