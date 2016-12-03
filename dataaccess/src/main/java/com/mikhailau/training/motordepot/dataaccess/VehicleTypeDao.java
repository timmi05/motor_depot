package com.mikhailau.training.motordepot.dataaccess;

import java.util.List;

import com.mikhailau.training.motordepot.dataaccess.filters.VehicleTypeFilter;
import com.mikhailau.training.motordepot.datamodel.VehicleType;

public interface VehicleTypeDao extends AbstractDao<VehicleType, Long> {

	Long count(VehicleTypeFilter filter);

	List<VehicleType> find(VehicleTypeFilter filter);
}
