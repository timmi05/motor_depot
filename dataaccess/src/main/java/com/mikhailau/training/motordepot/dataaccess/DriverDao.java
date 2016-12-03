package com.mikhailau.training.motordepot.dataaccess;

import java.util.List;

import com.mikhailau.training.motordepot.dataaccess.filters.DriverFilter;
import com.mikhailau.training.motordepot.datamodel.Driver;

public interface DriverDao extends AbstractDao<Driver, Long> {

	Long count(DriverFilter filter);

	List<Driver> find(DriverFilter filter);
}
