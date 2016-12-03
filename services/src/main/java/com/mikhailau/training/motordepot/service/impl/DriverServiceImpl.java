package com.mikhailau.training.motordepot.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mikhailau.training.motordepot.dataaccess.CredentialsDao;
import com.mikhailau.training.motordepot.dataaccess.DriverDao;
import com.mikhailau.training.motordepot.dataaccess.VehicleDao;
import com.mikhailau.training.motordepot.dataaccess.filters.DriverFilter;
import com.mikhailau.training.motordepot.datamodel.Credentials;
import com.mikhailau.training.motordepot.datamodel.Driver;
import com.mikhailau.training.motordepot.datamodel.Vehicle;
import com.mikhailau.training.motordepot.service.DriverService;

@Service
public class DriverServiceImpl implements DriverService {
	private static Logger LOGGER = LoggerFactory.getLogger(DriverServiceImpl.class);

	@Inject
	private DriverDao driverDao;

	@Inject
	private VehicleDao vehicleDao;

	@Inject
	private CredentialsDao сredentialsDao;

	@Override
	public void registerDriver(Vehicle vehicle, Driver driver, Credentials credentials) {
		сredentialsDao.insert(credentials);
		driver.setCredentials(credentials);   
		driver.setStateFree(true);
		driver.setLeadTime(new Date());
		driverDao.insert(driver);
		vehicle.setDriver(driver);
		vehicleDao.insert(vehicle);
		LOGGER.info("Driver and Vehicle regirstred: {}", credentials, driver, vehicle);
	}

	@Override
	public Driver getDriver(Long id) {
		LOGGER.info("Driver get: {}", driverDao.get(id));
		return driverDao.get(id);
	}

	@Override
	public void update(Driver driver) {
		LOGGER.info("Driver update: {}", driver);
		driverDao.update(driver);
	}

	@Override
	public void delete(Long id) {
		LOGGER.info("Customer delete: {}", driverDao.get(id), сredentialsDao.get(id), vehicleDao.get(id));
		vehicleDao.delete(id);
		driverDao.delete(id);
		сredentialsDao.delete(id);
	}

	@Override
	public List<Driver> find(DriverFilter filter) {
		LOGGER.info("Driver find by filter: {}", filter);
		return driverDao.find(filter);
	}

	@Override
	public List<Driver> getAll() {
		LOGGER.info("Driver getAll: {}", " ");
		return driverDao.getAll();
	}

	@Override
	public Long count(DriverFilter filter) {
		return driverDao.count(filter);
	}
}
