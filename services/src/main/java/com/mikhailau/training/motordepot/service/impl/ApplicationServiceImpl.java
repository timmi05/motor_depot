package com.mikhailau.training.motordepot.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mikhailau.training.motordepot.dataaccess.ApplicationDao;
import com.mikhailau.training.motordepot.dataaccess.filters.ApplicationFilter;
import com.mikhailau.training.motordepot.datamodel.Application;
import com.mikhailau.training.motordepot.service.ApplicationService;

@Service
public class ApplicationServiceImpl implements ApplicationService {
	private static Logger LOGGER = LoggerFactory.getLogger(ApplicationServiceImpl.class);

	@Inject
	private ApplicationDao applicationDao;

	@Override
	public void insert(Application application) {
		applicationDao.insert(application);
		LOGGER.info("Application insert: {}", application);
	}

	@Override
	public Application getApplication(Long id) {
		LOGGER.info("Application get: {}", applicationDao.get(id));
		return applicationDao.get(id);
	}

	@Override
	public void update(Application application) {
		applicationDao.update(application);
		LOGGER.info("Application update: {}", application);
	}

	@Override
	public void delete(Long id) {
		LOGGER.info("Application delete: {}", applicationDao.get(id));
		applicationDao.delete(id);
	}

	@Override
	public List<Application> find(ApplicationFilter filter) {
		LOGGER.info("Application find by filter: {}", filter);
		return applicationDao.find(filter);
	}

	@Override
	public List<Application> getAll() {
		LOGGER.info("Application getAll: {}", " ");
		return applicationDao.getAll();
	}

	@Override
	public Long count(ApplicationFilter filter) {
		return applicationDao.count(filter);
	}
}
