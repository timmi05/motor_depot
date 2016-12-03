package com.mikhailau.training.motordepot.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mikhailau.training.motordepot.dataaccess.VehicleTypeDao;
import com.mikhailau.training.motordepot.dataaccess.filters.VehicleTypeFilter;
import com.mikhailau.training.motordepot.datamodel.VehicleType;
import com.mikhailau.training.motordepot.service.VehicleTypeService;

@Service
public class VehicleTypeServiceImpl implements VehicleTypeService {
	private static Logger LOGGER = LoggerFactory.getLogger(VehicleTypeServiceImpl.class);

	@Inject
	private VehicleTypeDao vehicleTypeDao;

	@Override
	public void saveOrUpdate(VehicleType vehicleType) {
		if (vehicleType.getId() == null) {
			vehicleTypeDao.insert(vehicleType);
			LOGGER.info("VehicleType insert: {}", vehicleType);
		} else {
			vehicleTypeDao.update(vehicleType);
			LOGGER.info("VehicleType updateVehicleType: {}", vehicleType);
		}
	}

	@Override
	public VehicleType getVehicleType(Long id) {
		LOGGER.info("VehicleType get: {}", vehicleTypeDao.get(id));
		return vehicleTypeDao.get(id);
	}

	@Override
	public void delete(Long id) {
		LOGGER.info("VehicleType delete: {}", vehicleTypeDao.get(id));
		vehicleTypeDao.delete(id);
	}

	@Override
	public List<VehicleType> find(VehicleTypeFilter filter) {
		LOGGER.info("VehicleType find by filter: {}", filter);
		return vehicleTypeDao.find(filter);
	}

	@Override
	public List<VehicleType> getAll() {
		LOGGER.info("VehicleType getAll: {}", " ");
		return vehicleTypeDao.getAll();
	}

	@Override
	public Long count(VehicleTypeFilter filter) {
		return vehicleTypeDao.count(filter);
	}
}
