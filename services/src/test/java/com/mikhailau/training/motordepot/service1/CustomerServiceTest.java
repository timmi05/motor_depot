package com.mikhailau.training.motordepot.service1;

import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mikhailau.training.motordepot.dataaccess.filters.CustomerFilter;
import com.mikhailau.training.motordepot.datamodel.Credentials;
import com.mikhailau.training.motordepot.datamodel.Customer;
import com.mikhailau.training.motordepot.datamodel.Customer_;
import com.mikhailau.training.motordepot.datamodel.Role;
import com.mikhailau.training.motordepot.service.CredentialsService;
import com.mikhailau.training.motordepot.service.CustomerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:service-context-test.xml" })
public class CustomerServiceTest {
	@Inject
	private CustomerService customerService;

	@Inject
	private CredentialsService entryService;

	private static Customer customer;
	private static Customer customer2;
	private static Credentials сredentials;
	private static List<Customer> result;

	@Before
	public void testIn() {
		int testObjectsCount = 5;
		for (int i = 0; i < testObjectsCount; i++) {
			customer = new Customer();
			сredentials = new Credentials();
			customer.setFirstName("testFName" + i);
			customer.setLastName("testLName" + i);
			сredentials.setLogin("login" + i);
			сredentials.setPassword("pswd" + i);
			сredentials.setRole(Role.CUSTOMER);
			customerService.registerCustomer(сredentials, customer);
		}
	}

	@After
	public void testOut() {
		List<Customer> all = customerService.getAll();
		for (Customer customer : all) {
			customerService.delete(customer.getId());
		}
	}

	@Test
	public void testCustomerRegistr() {
		customer = new Customer();
		сredentials = new Credentials();
		customer.setFirstName("testFName");
		customer.setLastName("testLName");
		сredentials.setLogin(System.currentTimeMillis() + "login");
		сredentials.setPassword("pswd");
		сredentials.setRole(Role.CUSTOMER);
		customerService.registerCustomer(сredentials, customer);
		Customer registrdCustomer = customerService.getCustomer(customer.getId());
		Credentials registrdEntry = entryService.getСredentials(сredentials.getId());
		Assert.assertNotNull(registrdCustomer);
		Assert.assertNotNull(registrdEntry);
	}

	@Test
	public void testCustomerUpdat() {
		CustomerFilter filter = new CustomerFilter();
		filter.setCustomerLName("testLName" + 1);
		result = customerService.find(filter);
		customer2 = result.get(0);
		String updatedFName = "updatedName";
		customer2.setFirstName(updatedFName);
		customerService.update(customer2);
		Assert.assertEquals(updatedFName, customerService.getCustomer(customer2.getId()).getFirstName());
	}

	@Test
	public void testCustomerDelete() {
		CustomerFilter filter = new CustomerFilter();
		filter.setCustomerLName("testLName" + 3);
		result = customerService.find(filter);
		customer2 = result.get(0);
		customerService.delete(customer2.getId());
		Assert.assertNull(customerService.getCustomer(customer2.getId()));
		Assert.assertNull(entryService.getСredentials(customer2.getId()));
	}

	@Test
	public void testSearch() {
		CustomerFilter filter = new CustomerFilter();
		result = customerService.find(filter);
		Assert.assertEquals(5, result.size());
	}

	@Test
	public void testSort() {
		CustomerFilter filter = new CustomerFilter();
		filter.setLimit(null);
		filter.setOffset(null);
		filter.setSortOrder(true);
		filter.setSortProperty(Customer_.lastName);
		result = customerService.find(filter);
		Assert.assertEquals(5, result.size());
	}

	@Test
	public void testSearchName() {
		CustomerFilter filter = new CustomerFilter();
		filter.setCustomerLName("testLName" + 2);
		result = customerService.find(filter);
		customer2 = result.get(0);
		Assert.assertEquals(1, result.size());
	}

	@Test
	public void testSearchLogin() {
		CustomerFilter filter = new CustomerFilter();
		filter.setLogin("login3");
		result = customerService.find(filter);
		customer2 = result.get(0);
		Assert.assertEquals(1, result.size());
	}

	@Test
	public void testPage() {
		CustomerFilter filter = new CustomerFilter();
		// test paging
		int limit = 3;
		filter.setLimit(limit);
		filter.setOffset(0);
		result = customerService.find(filter);
		Assert.assertEquals(limit, result.size());
	}
}
