package com.mikhailau.training.motordepot.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mikhailau.training.motordepot.dataaccess.CredentialsDao;
import com.mikhailau.training.motordepot.dataaccess.CustomerDao;
import com.mikhailau.training.motordepot.dataaccess.filters.CustomerFilter;
import com.mikhailau.training.motordepot.datamodel.Credentials;
import com.mikhailau.training.motordepot.datamodel.Customer;
import com.mikhailau.training.motordepot.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	private static Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Inject
	private CustomerDao customerDao;

	@Inject
	private CredentialsDao сredentialsDao;

	@Override
	public void registerCustomer(Credentials credentials, Customer customer) {
		сredentialsDao.insert(credentials);
		customer.setCredentials(credentials);
		customerDao.insert(customer);
		LOGGER.info("Customer regirstred: {}", credentials, customer);
	}

	@Override
	public Customer getCustomer(Long id) {
		LOGGER.info("Customer get: {}", customerDao.get(id));
		return customerDao.get(id);
	}

	@Override
	public void update(Customer customer) {
		customerDao.update(customer);
		LOGGER.info("Customer update: {}", customer);
	}

	@Override
	public void delete(Long id) {
		LOGGER.info("Customer delete: {}", customerDao.get(id), сredentialsDao.get(id));
		customerDao.delete(id);
		сredentialsDao.delete(id);
	}

	@Override
	public List<Customer> find(CustomerFilter filter) {
		LOGGER.info("Customer find by filter: {}", filter);
		return customerDao.find(filter);
	}

	@Override
	public List<Customer> getAll() {
		LOGGER.info("Customer getAll: {}", " ");
		return customerDao.getAll();
	}

	@Override
	public Long count(CustomerFilter filter) {
		return customerDao.count(filter);
	}

	@Override
	public Customer get(Long id) {
		return customerDao.getWithAttributes(id);
	}
}
