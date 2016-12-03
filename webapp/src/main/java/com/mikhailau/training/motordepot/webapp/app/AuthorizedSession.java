package com.mikhailau.training.motordepot.webapp.app;

import java.util.Locale;

import javax.inject.Inject;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;

import com.mikhailau.training.motordepot.dataaccess.filters.CredentialsFilter;
import com.mikhailau.training.motordepot.datamodel.Credentials;
import com.mikhailau.training.motordepot.service.CredentialsService;
import com.mikhailau.training.motordepot.webapp.component.localization.LanguageSelectionComponent;

public class AuthorizedSession extends AuthenticatedWebSession {
	@Inject
	private CredentialsService credentialsService;

	private Credentials loggedUser;
	private Roles roles;

	public AuthorizedSession(Request request) {
		super(request);
		Injector.get().inject(this);
	}

	@Override
	public Locale getLocale() {
		Locale locale = super.getLocale();

		if (locale == null || !LanguageSelectionComponent.SUPPORTED_LOCALES.contains(locale)) {
			setLocale(LanguageSelectionComponent.SUPPORTED_LOCALES.get(0));
		}
		return super.getLocale();
	}

	public static AuthorizedSession get() {
		return (AuthorizedSession) Session.get();
	}

	@Override
	public boolean authenticate(final String userName, final String password) {
		CredentialsFilter filter = new CredentialsFilter();
		filter.setCredentials(true);
		loggedUser = credentialsService.find(userName, password, filter);

		return loggedUser != null;
	}

	@Override
	public Roles getRoles() {
		if (isSignedIn() && (roles == null)) {
			roles = new Roles();
			roles.addAll(credentialsService.resolveRoles(loggedUser.getId()));
		}
		return roles;
	}

	@Override
	public void signOut() {
		super.signOut();

		loggedUser = null;

		roles = null;
	}

	public Credentials getLoggedUser() {
		return loggedUser;
	}
}
