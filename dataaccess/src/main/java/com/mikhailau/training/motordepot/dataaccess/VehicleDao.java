package com.mikhailau.training.motordepot.dataaccess;

import java.util.List;

import com.mikhailau.training.motordepot.dataaccess.filters.VehicleFilter;
import com.mikhailau.training.motordepot.datamodel.Vehicle;

public interface VehicleDao extends AbstractDao<Vehicle, Long> {

	Long count(VehicleFilter filter);

	List<Vehicle> find(VehicleFilter filter);
	
	Vehicle getWithAttributes(Long id);
}
