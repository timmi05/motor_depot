package com.mikhailau.training.motordepot.webapp.page.login;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.string.Strings;

import com.mikhailau.training.motordepot.datamodel.Role;
import com.mikhailau.training.motordepot.webapp.app.AuthorizedSession;
import com.mikhailau.training.motordepot.webapp.page.AbstractPage;
import com.mikhailau.training.motordepot.webapp.page.customer.CustomerPage;
import com.mikhailau.training.motordepot.webapp.page.customer.registration.RegistrationCustomerPage;
import com.mikhailau.training.motordepot.webapp.page.dispatcher.DispatcherPage;
import com.mikhailau.training.motordepot.webapp.page.driver.DriverPage;

public class LoginPage extends AbstractPage {

	public static final String ID_FORM = "form";

	private String login;
	private String password;

	@Override
	protected void onInitialize() {
		super.onInitialize();

		final Form<Void> form = new Form<Void>(ID_FORM);
		form.setDefaultModel(new CompoundPropertyModel<LoginPage>(this));
		RequiredTextField<String> loginField = new RequiredTextField<String>("login");
		loginField.setLabel(new ResourceModel("login.login"));
		form.add(loginField);

		PasswordTextField passwordField = new PasswordTextField("password");
		passwordField.setLabel(new ResourceModel("login.password"));
		form.add(passwordField);

		form.add(new SubmitLink("enter") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				if (Strings.isEmpty(login) || Strings.isEmpty(password)) {
					return;
				}
				final boolean authResult = AuthenticatedWebSession.get().signIn(login, password);
				if (authResult && AuthorizedSession.get().getRoles().contains(Role.DISPATCHER.name())) {
					setResponsePage(new DispatcherPage());
				} else if (authResult && AuthorizedSession.get().getRoles().contains(Role.DRIVER.name())) {
					setResponsePage(new DriverPage());
				} else if (authResult && AuthorizedSession.get().getRoles().contains(Role.CUSTOMER.name())) {
					setResponsePage(new CustomerPage());
				} else {
					error(getString("loging.err.auth"));
				}
			}
		});

		form.add(new Link("linkRegistrationCustomer") {
			@Override
			public void onClick() {
				setResponsePage(new RegistrationCustomerPage());
			}
		});
		add(form);
		add(new FeedbackPanel("feedback"));
	}
}
