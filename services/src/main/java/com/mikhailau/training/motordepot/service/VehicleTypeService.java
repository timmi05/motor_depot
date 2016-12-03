package com.mikhailau.training.motordepot.service;

import java.util.List;

import javax.transaction.Transactional;

import com.mikhailau.training.motordepot.dataaccess.filters.VehicleTypeFilter;
import com.mikhailau.training.motordepot.datamodel.VehicleType;

public interface VehicleTypeService {

	@Transactional
	void saveOrUpdate(VehicleType vehicleType);

	VehicleType getVehicleType(Long id);

	@Transactional
	void delete(Long id);

	List<VehicleType> find(VehicleTypeFilter filter);

	List<VehicleType> getAll();

	Long count(VehicleTypeFilter filter);
}
