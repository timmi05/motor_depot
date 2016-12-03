package com.mikhailau.training.motordepot.dataaccess;

import java.util.List;

import com.mikhailau.training.motordepot.dataaccess.filters.CategoryLicenseFilter;
import com.mikhailau.training.motordepot.datamodel.CategoryLicense;

public interface CategoryLicenseDao extends AbstractDao<CategoryLicense, Long> {

	Long count(CategoryLicenseFilter filter);

	List<CategoryLicense> find(CategoryLicenseFilter filter);
}
