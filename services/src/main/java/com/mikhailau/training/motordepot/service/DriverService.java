package com.mikhailau.training.motordepot.service;

import java.util.List;
import javax.transaction.Transactional;

import com.mikhailau.training.motordepot.dataaccess.filters.DriverFilter;
import com.mikhailau.training.motordepot.datamodel.Credentials;
import com.mikhailau.training.motordepot.datamodel.Driver;
import com.mikhailau.training.motordepot.datamodel.Vehicle;

public interface DriverService {

	@Transactional
	void registerDriver(Vehicle vehicle, Driver driver, Credentials entry);

	Driver getDriver(Long id);

	@Transactional
	void update(Driver driver);

	@Transactional
	void delete(Long id);

	List<Driver> find(DriverFilter filter);

	List<Driver> getAll();

	Long count(DriverFilter filter);
}
