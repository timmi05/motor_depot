package com.mikhailau.training.motordepot.dataaccess;

import java.util.List;

import com.mikhailau.training.motordepot.dataaccess.filters.CustomerFilter;
import com.mikhailau.training.motordepot.datamodel.Customer;

public interface CustomerDao extends AbstractDao<Customer, Long> {

	Long count(CustomerFilter filter);

	List<Customer> find(CustomerFilter filter);

	Customer getWithAttributes(Long id);
}
