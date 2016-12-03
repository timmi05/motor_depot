package com.mikhailau.training.motordepot.service;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import com.mikhailau.training.motordepot.dataaccess.filters.CredentialsFilter;
import com.mikhailau.training.motordepot.datamodel.Credentials;

public interface CredentialsService {

	Credentials getСredentials(Long id);

	@Transactional
	void update(Credentials entry);

    List<Credentials> getAll();

	Credentials find(String login, String password, CredentialsFilter filter);
	
	Credentials find(String login, CredentialsFilter filter);

    Collection<? extends String> resolveRoles(Long id);
}
