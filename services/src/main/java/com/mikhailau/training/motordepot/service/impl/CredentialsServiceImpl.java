package com.mikhailau.training.motordepot.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mikhailau.training.motordepot.dataaccess.CredentialsDao;
import com.mikhailau.training.motordepot.dataaccess.filters.CredentialsFilter;
import com.mikhailau.training.motordepot.datamodel.Credentials;
import com.mikhailau.training.motordepot.service.CredentialsService;

@Service
public class CredentialsServiceImpl implements CredentialsService {
	private static Logger LOGGER = LoggerFactory.getLogger(CredentialsServiceImpl.class);

	@Inject
	private CredentialsDao credentialsDao;

	@Override
	public Credentials getСredentials(Long id) {
		LOGGER.info("Сredentials get: {}", credentialsDao.get(id));
		return credentialsDao.get(id);
	}

	@Override
	public void update(Credentials credentials) {
		credentialsDao.update(credentials);
		LOGGER.info("Customer updateСredentials: {}", credentials);
	}

	@Override
	public Credentials find(String login, String password, CredentialsFilter filter) {
		LOGGER.info("Сredentials find by filter: {}", filter);
		return credentialsDao.find(login, password, filter);
	}

	@Override
	public Credentials find(String login, CredentialsFilter filter) {
		LOGGER.info("Сredentials find by filter: {}", filter);
		return credentialsDao.find(login, filter);
	}

	@Override
	public List<Credentials> getAll() {
		LOGGER.info("Сredentials getAll: {}", " ");
		return credentialsDao.getAll();
	}

	@Override
	public Collection<String> resolveRoles(Long id) {
		Credentials credentials = credentialsDao.get(id);
		return Collections.singletonList(credentials.getRole().name());
	}
}
