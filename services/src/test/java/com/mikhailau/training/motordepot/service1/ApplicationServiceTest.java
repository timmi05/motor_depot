package com.mikhailau.training.motordepot.service1;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mikhailau.training.motordepot.dataaccess.ApplicationDao;
import com.mikhailau.training.motordepot.dataaccess.filters.ApplicationFilter;
import com.mikhailau.training.motordepot.dataaccess.filters.CategoryLicenseFilter;
import com.mikhailau.training.motordepot.dataaccess.filters.CustomerFilter;
import com.mikhailau.training.motordepot.dataaccess.filters.DriverFilter;
import com.mikhailau.training.motordepot.dataaccess.filters.VehicleTypeFilter;
import com.mikhailau.training.motordepot.dataaccess.impl.AbstractDaoImpl;
import com.mikhailau.training.motordepot.datamodel.Application;
import com.mikhailau.training.motordepot.datamodel.ApplicationState;
import com.mikhailau.training.motordepot.datamodel.Application_;
import com.mikhailau.training.motordepot.datamodel.CategoryLicense;
import com.mikhailau.training.motordepot.datamodel.Credentials;
import com.mikhailau.training.motordepot.datamodel.Customer;
import com.mikhailau.training.motordepot.datamodel.Driver;
import com.mikhailau.training.motordepot.datamodel.Role;
import com.mikhailau.training.motordepot.datamodel.Vehicle;
import com.mikhailau.training.motordepot.datamodel.VehicleType;
import com.mikhailau.training.motordepot.service.ApplicationService;
import com.mikhailau.training.motordepot.service.CategoryLicenseService;
import com.mikhailau.training.motordepot.service.CustomerService;
import com.mikhailau.training.motordepot.service.DriverService;
import com.mikhailau.training.motordepot.service.VehicleTypeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:service-context-test.xml" })
public class ApplicationServiceTest {
	@Inject
	private ApplicationService applicationService;

	@Inject
	private ApplicationDao applicationDao;

	@Inject
	private VehicleTypeService vehicleTypeService;

	@Inject
	private CustomerService customerService;

	@Inject
	private CategoryLicenseService categoryLicenseService;

	@Inject
	private DriverService driverService;

	private static VehicleType vehicleType;
	private static Vehicle vehicle;
	private static Customer customer;
	private static Credentials credentialsCustomer;
	private static Credentials credentialsDriver;
	private static CategoryLicense categoryLicense;
	private static Driver driver;
	private static Driver driverTest;
	private static Application application;

	private static List<Customer> resultCustomer;
	private static List<Driver> resultDriver;
	private static List<CategoryLicense> resultCategoryLicense;
	private static List<CategoryLicense> category;
	private static List<Application> resultApplication;
	private static List<VehicleType> resultVehicleType;

	@Before
	public void testIn() {
		int testObjectsCount = 5;
		for (int i = 0; i < testObjectsCount; i++) {
			customer = new Customer();
			credentialsCustomer = new Credentials();
			customer.setFirstName("testFName" + i);
			customer.setLastName("testLName" + i);
			credentialsCustomer.setLogin("login" + i);
			credentialsCustomer.setPassword("pswd" + i);
			credentialsCustomer.setRole(Role.CUSTOMER);
			customerService.registerCustomer(credentialsCustomer, customer);
		}

		for (int i = 0; i < testObjectsCount; i++) {
			VehicleType vehicleType = new VehicleType();
			vehicleType.setType("type" + i);
			vehicleType.setExists(true);
			vehicleTypeService.saveOrUpdate(vehicleType);
		}

		for (int i = 0; i < testObjectsCount; i++) {
			categoryLicense = new CategoryLicense();
			categoryLicense.setCategory("B" + i);
			categoryLicenseService.saveOrUpdate(categoryLicense);
		}

		for (int i = 0; i < testObjectsCount; i++) {
			vehicleType = new VehicleType();
			driver = new Driver();
			credentialsDriver = new Credentials();
			vehicle = new Vehicle();
			category = new ArrayList<>();
			resultCategoryLicense = new ArrayList<>();
			CategoryLicenseFilter categoryLicensefilter = new CategoryLicenseFilter();
			for (int j = 0; j < 3; j++) {
				categoryLicensefilter.setСategory("B" + j);
				resultCategoryLicense = categoryLicenseService.find(categoryLicensefilter);
				if (!resultCategoryLicense.isEmpty()) {
					category.add(resultCategoryLicense.get(0));
				}
			}

			driver.setCategoryLicense(category);
			VehicleTypeFilter vehicleTypefilter = new VehicleTypeFilter();
			vehicleTypefilter.setTypeForFilter("type" + i);
			resultVehicleType = vehicleTypeService.find(vehicleTypefilter);
			vehicleType = resultVehicleType.get(0);
			vehicle.setVehicleType(vehicleType);
			vehicle.setLicensePlate("1234" + i);
			vehicle.setMaxWeight((double) 5);
			vehicle.setModel("Daf2");
			vehicle.setNumberOfPallets(7);
			vehicle.setStateAfterFreight(true);
			driver.setLastName("testLName" + i);
			driver.setFirstName("testFName" + i);
			driver.setStateFree(true);
			credentialsDriver.setLogin("logindriver" + i);
			credentialsDriver.setPassword("pswddriver" + i);
			credentialsDriver.setRole(Role.DRIVER);
			driverService.registerDriver(vehicle, driver, credentialsDriver);
		}

		for (int i = 0; i < testObjectsCount; i++) {
			application = new Application();
			customer = new Customer();
			credentialsCustomer = new Credentials();
			credentialsDriver = new Credentials();
			driver = new Driver();
			CustomerFilter customerfilter = new CustomerFilter();
			customerfilter.setCustomerLName("testLName" + i);
			resultCustomer = customerService.find(customerfilter);
			customer = resultCustomer.get(0);
			application.setCustomer(customer);
			VehicleTypeFilter vehicleTypefilter = new VehicleTypeFilter();
			vehicleTypefilter.setTypeForFilter("type" + i);
			resultVehicleType = vehicleTypeService.find(vehicleTypefilter);
			vehicleType = resultVehicleType.get(0);
			application.setVehicleType(vehicleType);
			DriverFilter driverfilter = new DriverFilter();
			driverfilter.setDriverLName("testLName" + i);
			driverfilter.setDriverFName("testFName" + i);
			resultDriver = driverService.find(driverfilter);
			driver = resultDriver.get(0);
			application.setDriver(driver);
			application.setReceiptTime(new Date());
			application.setLeadTime(new Date());
			application.setWeight((double) 3.2);
			application.setNumberOfPallets((int) 5);
			if (i == 3) {
				application.setApplicationState(ApplicationState.DONE);
			} else {
				application.setApplicationState(ApplicationState.RUN);
			}
			application.setLoadingAddress("loadingAddress" + i);
			application.setUnloadingAddress("unloadingAddress" + i);
			applicationService.insert(application);
		}
	}

	@After
	public void testOut() {

		List<Application> allApplication = applicationService.getAll();
		for (Application application : allApplication) {
			applicationService.delete(application.getId());
		}
		List<Customer> allCustomer = customerService.getAll();
		for (Customer customer : allCustomer) {
			customerService.delete(customer.getId());
		}
		List<Driver> allDriver = driverService.getAll();
		for (Driver driver : allDriver) {
			driverService.delete(driver.getId());
		}
		List<CategoryLicense> allCategoryLicense = categoryLicenseService.getAll();
		for (CategoryLicense categoryLicense : allCategoryLicense) {
			categoryLicenseService.delete(categoryLicense.getId());
		}
		List<VehicleType> allVehicleTypes = vehicleTypeService.getAll();
		for (VehicleType vehicleType : allVehicleTypes) {
			vehicleTypeService.delete(vehicleType.getId());
		}
	}

	@Test
	public void test() {
		Assert.assertNotNull(customerService);
	}

	@Test
	public void testEntityManagerInitialization()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field f = AbstractDaoImpl.class.getDeclaredField("entityManager");
		f.setAccessible(true);
		EntityManager em = (EntityManager) f.get(applicationDao);
		Assert.assertNotNull(em);
	}

	@Test
	public void testApplicationInserted() {
		regAppl();
		applicationService.insert(application);
		Application registrdApplication = applicationService.getApplication(application.getId());
		Assert.assertNotNull(registrdApplication);
	}

	private void regAppl() {
		application = new Application();
		customer = new Customer();
		credentialsCustomer = new Credentials();
		customer.setFirstName(System.currentTimeMillis() + "testFName");
		customer.setLastName(System.currentTimeMillis() + "testLName");
		credentialsCustomer.setLogin(System.currentTimeMillis() + "login");
		credentialsCustomer.setPassword("pswd");
		credentialsCustomer.setRole(Role.CUSTOMER);
		customerService.registerCustomer(credentialsCustomer, customer);
		application.setCustomer(customer);
		vehicleType = new VehicleType();
		vehicleType.setType(System.currentTimeMillis() + "big");
		vehicleType.setExists(true);
		vehicleTypeService.saveOrUpdate(vehicleType);
		application.setVehicleType(vehicleType);
		application.setReceiptTime(new Date());
		application.setLeadTime(new Date());
		application.setWeight((double) 3.2);
		application.setNumberOfPallets((int) 5);
		application.setApplicationState(ApplicationState.RUN);
		application.setLoadingAddress("loadingAddress");
		application.setUnloadingAddress("unloadingAddress");
	}

	@Test
	public void testApplicationUpdated() {
		ApplicationFilter applicationFilter = new ApplicationFilter();
		DriverFilter driverfilter = new DriverFilter();
		driverfilter.setDriverLName("testLName" + 1);
		driverfilter.setDriverFName("testFName" + 1);
		resultDriver = driverService.find(driverfilter);
		driverTest = resultDriver.get(0);
		applicationFilter.setDriver(driverTest);
		resultApplication = applicationService.find(applicationFilter);
		application = resultApplication.get(0);
		String unloadingAddress2 = "unloadingAddress2";
		application.setUnloadingAddress(unloadingAddress2);
		applicationService.update(application);
		Assert.assertEquals(unloadingAddress2,
				applicationService.getApplication(application.getId()).getUnloadingAddress());
	}

	@Test
	public void testSearchCustomerApplication() {
		ApplicationFilter applicationFilter = new ApplicationFilter();
		resultApplication.clear();
		CustomerFilter filter = new CustomerFilter();
		filter.setCustomerLName("testLName" + 3);
		resultCustomer = customerService.find(filter);
		for (Customer customerTest : resultCustomer) {
			applicationFilter.setCustomer(customerTest);
			List<Application> resultTemp = applicationService.find(applicationFilter);
			resultApplication.addAll(resultTemp);
			resultTemp.clear();
		}
		Assert.assertEquals(1, resultApplication.size());
	}

	@Test
	public void testSearchDriverApplication() {
		ApplicationFilter applicationFilter = new ApplicationFilter();
		DriverFilter driverfilter = new DriverFilter();
		driverfilter.setDriverLName("testLName" + 1);
		driverfilter.setDriverFName("testFName" + 1);
		resultDriver = driverService.find(driverfilter);
		driverTest = resultDriver.get(0);
		applicationFilter.setDriver(driverTest);
		resultApplication = applicationService.find(applicationFilter);
		Assert.assertEquals(1, resultApplication.size());
	}

	@Test
	public void testSearchApplicationState() {
		ApplicationFilter applicationFilter = new ApplicationFilter();
		applicationFilter.setStates(Collections.singletonList(ApplicationState.RUN));
		resultApplication = applicationService.find(applicationFilter);
		Assert.assertEquals(4, resultApplication.size());
	}

	@Test
	public void testSort() {
		ApplicationFilter applicationFilter = new ApplicationFilter();
		applicationFilter.setLimit(null);
		applicationFilter.setOffset(null);
		applicationFilter.setSortOrder(true);
		applicationFilter.setSortProperty(Application_.leadTime);
		resultApplication = applicationService.find(applicationFilter);
		Assert.assertEquals(5, resultApplication.size());
	}

	@Test
	public void testPage() {
		ApplicationFilter applicationFilter = new ApplicationFilter();
		// test paging
		int limit = 3;
		applicationFilter.setLimit(limit);
		applicationFilter.setOffset(0);
		resultApplication = applicationService.find(applicationFilter);
		Assert.assertEquals(limit, resultApplication.size());
	}

	@Test
	public void testApplicationDelete() {
		ApplicationFilter applicationFilter = new ApplicationFilter();
		DriverFilter driverfilter = new DriverFilter();
		driverfilter.setDriverLName("testLName" + 1);
		driverfilter.setDriverFName("testFName" + 1);
		resultDriver = driverService.find(driverfilter);
		driverTest = resultDriver.get(0);
		applicationFilter.setDriver(driverTest);
		resultApplication = applicationService.find(applicationFilter);
		application = resultApplication.get(0);
		applicationService.delete(application.getId());
		Assert.assertNull(applicationService.getApplication(application.getId()));
	}
}
