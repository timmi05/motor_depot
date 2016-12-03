package com.mikhailau.training.motordepot.service;

import java.util.List;

import javax.transaction.Transactional;

import com.mikhailau.training.motordepot.dataaccess.filters.VehicleFilter;
import com.mikhailau.training.motordepot.datamodel.Vehicle;

public interface VehicleService {

	Vehicle getVehicle(Long id);

	@Transactional
	void update(Vehicle vehicle);

	List<Vehicle> find(VehicleFilter filter);

	List<Vehicle> getAll();

	Long count(VehicleFilter filter);
	
	Vehicle get(Long id);
}
