package com.mikhailau.training.motordepot.service;

import java.util.List;

import javax.transaction.Transactional;

import com.mikhailau.training.motordepot.dataaccess.filters.CustomerFilter;
import com.mikhailau.training.motordepot.datamodel.Credentials;
import com.mikhailau.training.motordepot.datamodel.Customer;

public interface CustomerService {

	@Transactional
	void registerCustomer(Credentials entry, Customer customer);

	Customer getCustomer(Long id);
	
	Customer get(Long id);

	@Transactional
	void update(Customer customer);

	@Transactional
	void delete(Long id);

	List<Customer> find(CustomerFilter filter);

	List<Customer> getAll();

	Long count(CustomerFilter filter);
}
