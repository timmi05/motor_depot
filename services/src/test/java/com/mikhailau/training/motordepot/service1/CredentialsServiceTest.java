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

import com.mikhailau.training.motordepot.dataaccess.filters.CredentialsFilter;
import com.mikhailau.training.motordepot.datamodel.Credentials;
import com.mikhailau.training.motordepot.datamodel.Customer;
import com.mikhailau.training.motordepot.datamodel.Role;
import com.mikhailau.training.motordepot.service.CredentialsService;
import com.mikhailau.training.motordepot.service.CustomerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:service-context-test.xml" })
public class CredentialsServiceTest {
	@Inject
	private CredentialsService сredentialsService;

	@Inject
	private CustomerService customerService;

	private static Credentials сredentials;
	private static Customer customer;
	private static Credentials result;

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
	public void testUpdateEntry() {
		CredentialsFilter filter = new CredentialsFilter();
		filter.setCredentials(true);
		filter.setRegistration(false);
		String login = "login3";
		String password = "pswd3";
		result = сredentialsService.find(login, password, filter);
		String updatedPassword = "updatedPassword";
		result.setPassword(updatedPassword);
		сredentialsService.update(result);
		Assert.assertEquals(updatedPassword, сredentialsService.getСredentials(result.getId()).getPassword());
	}

	@Test
	public void testLoginSearch() {
		CredentialsFilter filter = new CredentialsFilter();
		filter.setCredentials(true);
		filter.setRegistration(false);
		String login = "login2";
		String password = "pswd2";
		result = сredentialsService.find(login, password, filter);
		Assert.assertNotNull(result);
	}

	@Test
	public void testRegistrationSearch() {
		CredentialsFilter filter = new CredentialsFilter();
		filter.setCredentials(false);
		filter.setRegistration(true);
		String l = "login2";
		result = сredentialsService.find(l, filter);
		Assert.assertNotNull(result);
	}
}
