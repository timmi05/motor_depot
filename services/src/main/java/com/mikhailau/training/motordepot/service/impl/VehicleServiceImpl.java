package com.mikhailau.training.motordepot.service.impl;

import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mikhailau.training.motordepot.dataaccess.VehicleDao;
import com.mikhailau.training.motordepot.dataaccess.filters.VehicleFilter;
import com.mikhailau.training.motordepot.datamodel.Vehicle;
import com.mikhailau.training.motordepot.service.VehicleService;

@Service
public class VehicleServiceImpl implements VehicleService {
	private static Logger LOGGER = LoggerFactory.getLogger(VehicleServiceImpl.class);

	@Inject
	private VehicleDao vehicleDao;

	@Override
	public Vehicle getVehicle(Long id) {
		LOGGER.info("Vehicle get: {}", vehicleDao.get(id));
		return vehicleDao.get(id);
	}
	
	@Override
	public Vehicle get(Long id) {
		return vehicleDao.getWithAttributes(id);
	}
		
	@Override
	public void update(Vehicle vehicle) {
		LOGGER.info("Vehicle update: {}", vehicle);
		vehicleDao.update(vehicle);
	}

	@Override
	public List<Vehicle> find(VehicleFilter filter) {
		LOGGER.info("Vehicle find by filter: {}", filter);
		return vehicleDao.find(filter);
	}

	@Override
	public List<Vehicle> getAll() {
		LOGGER.info("Driver getAll: {}", " ");
		return vehicleDao.getAll();
	}

	@Override
	public Long count(VehicleFilter filter) {
		return vehicleDao.count(filter);
	}
}
