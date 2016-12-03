package com.mikhailau.training.motordepot.service;

import java.util.List;

import javax.transaction.Transactional;

import com.mikhailau.training.motordepot.dataaccess.filters.CategoryLicenseFilter;
import com.mikhailau.training.motordepot.datamodel.CategoryLicense;

public interface CategoryLicenseService {

	@Transactional
	void saveOrUpdate(CategoryLicense categoryLicense);

	CategoryLicense getCategoryLicense(Long id);

	@Transactional
	void delete(Long id);

	List<CategoryLicense> find(CategoryLicenseFilter filter);

	List<CategoryLicense> getAll();
	
	Long count(CategoryLicenseFilter filter);
}
