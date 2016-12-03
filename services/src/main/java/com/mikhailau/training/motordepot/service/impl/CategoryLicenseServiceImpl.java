package com.mikhailau.training.motordepot.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mikhailau.training.motordepot.dataaccess.CategoryLicenseDao;
import com.mikhailau.training.motordepot.dataaccess.filters.CategoryLicenseFilter;
import com.mikhailau.training.motordepot.datamodel.CategoryLicense;
import com.mikhailau.training.motordepot.service.CategoryLicenseService;

@Service
public class CategoryLicenseServiceImpl implements CategoryLicenseService {
	private static Logger LOGGER = LoggerFactory.getLogger(CategoryLicenseServiceImpl.class);

	@Inject
	private CategoryLicenseDao categoryLicenseDao;

	@Override
	public void saveOrUpdate(CategoryLicense categoryLicense) {
		if (categoryLicense.getId() == null) {
			categoryLicenseDao.insert(categoryLicense);
			LOGGER.info("CategoryLicense insert: {}", categoryLicense);
		} else {
			categoryLicenseDao.update(categoryLicense);
			LOGGER.info("CategoryLicense update: {}", categoryLicense);
		}
	}

	@Override
	public CategoryLicense getCategoryLicense(Long id) {
		LOGGER.info("CategoryLicense get: {}", categoryLicenseDao.get(id));
		return categoryLicenseDao.get(id);
	}

	@Override
	public void delete(Long id) {
		LOGGER.info("CategoryLicense delete: {}", categoryLicenseDao.get(id));
		categoryLicenseDao.delete(id);
	}

	@Override
	public List<CategoryLicense> find(CategoryLicenseFilter filter) {
		LOGGER.info("CategoryLicense find by filter: {}", filter);
		return categoryLicenseDao.find(filter);
	}

	@Override
	public List<CategoryLicense> getAll() {
		LOGGER.info("CategoryLicense getAll: {}", " ");
		return categoryLicenseDao.getAll();
	}

	@Override
	public Long count(CategoryLicenseFilter filter) {
		return categoryLicenseDao.count(filter);
	}
}
