package com.mikhailau.training.motordepot.webapp.page.customer.registration;

import javax.inject.Inject;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import com.mikhailau.training.motordepot.dataaccess.filters.CredentialsFilter;
import com.mikhailau.training.motordepot.dataaccess.filters.CustomerFilter;
import com.mikhailau.training.motordepot.datamodel.Credentials;
import com.mikhailau.training.motordepot.datamodel.Customer;
import com.mikhailau.training.motordepot.datamodel.Role;
import com.mikhailau.training.motordepot.service.CredentialsService;
import com.mikhailau.training.motordepot.service.CustomerService;
import com.mikhailau.training.motordepot.webapp.app.AuthorizedSession;
import com.mikhailau.training.motordepot.webapp.page.AbstractPage;
import com.mikhailau.training.motordepot.webapp.page.customer.AllCustomersPage;
import com.mikhailau.training.motordepot.webapp.page.customer.CustomerPage;

public class RegistrationCustomerPage extends AbstractPage {
	@Inject
	private CustomerService customerService;

	@Inject
	private CredentialsService credentialsService;

	public static final String ID_FORM = "form";

	private String login;
	private String password;
	private String lastName;
	private Credentials credentials;
	private Customer customer;

	public RegistrationCustomerPage() {
		super();

		credentials = new Credentials();
		customer = new Customer();
	}

	public RegistrationCustomerPage(Customer customer) {
		super();

		Long id = customer.getId();
		this.customer = customerService.get(id);
		this.credentials = this.customer.getCredentials();
		login = credentials.getLogin();
		password = credentials.getPassword();
		lastName = customer.getLastName();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		final Form<Void> form = new Form<Void>(ID_FORM);
		form.setDefaultModel(new CompoundPropertyModel<RegistrationCustomerPage>(this));

		RequiredTextField<String> loginField = new RequiredTextField<String>("login");
		loginField.setRequired(true);
		loginField.setLabel(new ResourceModel("login.login"));

		PasswordTextField passwordField = new PasswordTextField("password");
		passwordField.setRequired(true);
		passwordField.setLabel(new ResourceModel("login.password"));

		if (customer.getId() == null) {
			loginField.setEnabled(true);
			passwordField.setVisible(true);
		} else {
			loginField.setEnabled(false);
			passwordField.setVisible(false);
		}
		form.add(loginField);
		form.add(passwordField);

		TextField<String> lastNameField = new TextField<String>("lastName");
		lastNameField.setRequired(true);
		lastNameField.setLabel(new ResourceModel("customer.lname"));
		form.add(lastNameField);

		form.add(new TextField<String>("firstName", new PropertyModel(customer, "firstName")));

		form.add(new SubmitLink("save") {
			@Override
			public void onSubmit() {
				super.onSubmit();

				CredentialsFilter entryfilter = new CredentialsFilter();
				entryfilter.setCredentials(false);
				entryfilter.setRegistration(true);

				CustomerFilter customerFilter = new CustomerFilter();
				customerFilter.setCustomerLName(lastName);

				if (customer.getId() == null) {
					if (credentialsService.find(login, entryfilter) != null) {
						error(getString("loging.err.add"));
					} else if (!(customerService.find(customerFilter).isEmpty())) {
						error(getString("lname.err.add"));
					} else {
						credentials.setRole(Role.CUSTOMER);
						credentials.setLogin(login);
						credentials.setPassword(password);
						customer.setLastName(lastName);
						customerService.registerCustomer(credentials, customer);
						final boolean authResult = AuthenticatedWebSession.get().signIn(login, password);
						setResponsePage(
								(authResult && AuthorizedSession.get().getRoles().contains(Role.DISPATCHER.name()))
										? new AllCustomersPage() : new CustomerPage());
					}
				} else {
					if (credentialsService.find(login, entryfilter) != null
							&& (!(credentials.getId().equals(credentialsService.find(login, entryfilter).getId())))) {
						error(getString("loging.err.add"));
					} else if (!(customerService.find(customerFilter).isEmpty())
							&& (!(credentials.getId().equals(customerService.find(customerFilter).get(0).getId())))) {
						error(getString("lname.err.add"));
					} else {
						customer.setLastName(lastName);
						credentialsService.update(credentials);
						customerService.update(customer);
						setResponsePage((AuthorizedSession.get().getRoles().contains(Role.DISPATCHER.name()))
								? new AllCustomersPage() : new CustomerPage());
					}
				}
			}
		});
		add(form);
		add(new FeedbackPanel("feedback"));
	}
}
