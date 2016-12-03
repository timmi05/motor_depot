package com.mikhailau.training.motordepot.service;

import java.util.List;

import javax.transaction.Transactional;

import com.mikhailau.training.motordepot.dataaccess.filters.ApplicationFilter;
import com.mikhailau.training.motordepot.datamodel.Application;

public interface ApplicationService {

	@Transactional
	void insert(Application application);

	Application getApplication(Long id);

	@Transactional
	void update(Application application);

	@Transactional
	void delete(Long id);

	List<Application> find(ApplicationFilter filter);

	List<Application> getAll();

	Long count(ApplicationFilter filter);
}
