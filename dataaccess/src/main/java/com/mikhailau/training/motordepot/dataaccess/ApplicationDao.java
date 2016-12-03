package com.mikhailau.training.motordepot.dataaccess;

import java.util.List;

import com.mikhailau.training.motordepot.dataaccess.filters.ApplicationFilter;
import com.mikhailau.training.motordepot.datamodel.Application;

public interface ApplicationDao extends AbstractDao<Application, Long> {

	Long count(ApplicationFilter filter);

	List<Application> find(ApplicationFilter filter);
}
