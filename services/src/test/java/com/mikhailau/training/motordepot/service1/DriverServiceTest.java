package com.mikhailau.training.motordepot.service1;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mikhailau.training.motordepot.dataaccess.filters.CategoryLicenseFilter;
import com.mikhailau.training.motordepot.dataaccess.filters.DriverFilter;
import com.mikhailau.training.motordepot.dataaccess.filters.VehicleTypeFilter;
import com.mikhailau.training.motordepot.datamodel.CategoryLicense;
import com.mikhailau.training.motordepot.datamodel.Credentials;
import com.mikhailau.training.motordepot.datamodel.Driver;
import com.mikhailau.training.motordepot.datamodel.Driver_;
import com.mikhailau.training.motordepot.datamodel.Role;
import com.mikhailau.training.motordepot.datamodel.Vehicle;
import com.mikhailau.training.motordepot.datamodel.VehicleType;
import com.mikhailau.training.motordepot.service.CategoryLicenseService;
import com.mikhailau.training.motordepot.service.CredentialsService;
import com.mikhailau.training.motordepot.service.DriverService;
import com.mikhailau.training.motordepot.service.VehicleService;
import com.mikhailau.training.motordepot.service.VehicleTypeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:service-context-test.xml" })
public class DriverServiceTest {
	@Inject
	private DriverService driverService;

	@Inject
	private CredentialsService сredentialsService;

	@Inject
	private VehicleService vehicleService;

	@Inject
	private CategoryLicenseService categoryLicenseService;

	@Inject
	private VehicleTypeService vehicleTypeService;

	private static List<CategoryLicense> category;
	private static List<CategoryLicense> resultCategoryLicense;
	private static List<Driver> resultDriver;
	private static List<CategoryLicense> resulElect;
	private static List<VehicleType> resultVehicleType;

	private static CategoryLicense categoryLicense;
	private static Credentials сredentials;
	private static Driver driver;
	private static Vehicle vehicle;
	private static VehicleType vehicleType;

	@Before
	public void testIn() {
		int testObjectsCount = 5;
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
			сredentials = new Credentials();
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
			сredentials.setLogin("logindriver" + i);
			сredentials.setPassword("pswddriver" + i);
			сredentials.setRole(Role.DRIVER);
			driverService.registerDriver(vehicle, driver, сredentials);
		}
	}

	@After
	public void testOut() {
		List<Driver> all = driverService.getAll();
		for (Driver driver : all) {
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
	public void testRegistrationDriver() {
		vehicleType = new VehicleType();
		driver = new Driver();
		сredentials = new Credentials();
		vehicle = new Vehicle();
		category = new ArrayList<>();
		resultCategoryLicense = new ArrayList<>();
		CategoryLicenseFilter categoryLicensefilter = new CategoryLicenseFilter();
		categoryLicensefilter.setСategory("B1");
		resultCategoryLicense = categoryLicenseService.find(categoryLicensefilter);
		if (!resultCategoryLicense.isEmpty()) {
			category.add(resultCategoryLicense.get(0));
		}
		categoryLicensefilter.setСategory("B0");
		resultCategoryLicense = categoryLicenseService.find(categoryLicensefilter);
		if (!resultCategoryLicense.isEmpty()) {
			category.add(resultCategoryLicense.get(0));
		}
		categoryLicensefilter.setСategory("B3");
		resultCategoryLicense = categoryLicenseService.find(categoryLicensefilter);
		if (!resultCategoryLicense.isEmpty()) {
			category.add(resultCategoryLicense.get(0));
		}
		driver.setCategoryLicense(category);
		VehicleTypeFilter vehicleTypefilter = new VehicleTypeFilter();
		vehicleTypefilter.setTypeForFilter("type" + 2);
		resultVehicleType = vehicleTypeService.find(vehicleTypefilter);
		vehicleType = resultVehicleType.get(0);
		vehicle.setVehicleType(vehicleType);
		vehicle.setLicensePlate("1234" + 6);
		vehicle.setMaxWeight((double) 5);
		vehicle.setModel("Daf2");
		vehicle.setNumberOfPallets(7);
		vehicle.setStateAfterFreight(true);
		driver.setLastName("testLName" + 6);
		driver.setFirstName("testFName" + 6);
		driver.setStateFree(true);
		сredentials.setLogin("logindriver" + 6);
		сredentials.setPassword("pswddriver" + 6);
		сredentials.setRole(Role.DRIVER);
		driverService.registerDriver(vehicle, driver, сredentials);
		Driver registrddriver = driverService.getDriver(driver.getId());
		Credentials registrdEntry = сredentialsService.getСredentials(сredentials.getId());
		Vehicle registrdVehicle = vehicleService.getVehicle(vehicle.getId());
		Assert.assertNotNull(registrddriver);
		Assert.assertNotNull(registrdEntry);
		Assert.assertNotNull(registrdVehicle);
	}

	@Test
	public void searchDriverTest() {
		DriverFilter driverfilter = new DriverFilter();
		driverfilter.setDriverLName("testLName" + 1);
		driverfilter.setDriverFName("testFName" + 1);
		resultDriver = driverService.find(driverfilter);
		Assert.assertEquals(1, resultDriver.size());
	}

	@Test
	public void searchStateFreeTrueDriverTest() {
		DriverFilter driverfilter = new DriverFilter();
		driverfilter.setStateFreeTrue(true);
		resultDriver = driverService.find(driverfilter);
		Assert.assertEquals(5, resultDriver.size());
	}

	@Test
	public void searchStateFreeFalseDriverTest() {
		DriverFilter driverfilter = new DriverFilter();
		driverfilter.setStateFreeFalse(true);
		resultDriver = driverService.find(driverfilter);
		Assert.assertEquals(0, resultDriver.size());
	}

	@Test
	public void searchCategoryDriverTest() {
		resulElect = new ArrayList<CategoryLicense>();
		CategoryLicenseFilter categoryLicensefilter = new CategoryLicenseFilter();
		categoryLicensefilter.setСategory("B0");
		resultCategoryLicense = categoryLicenseService.find(categoryLicensefilter);
		if (!resultCategoryLicense.isEmpty()) {
			resulElect.add(resultCategoryLicense.get(0));
		}
		categoryLicensefilter.setСategory("B2");
		resultCategoryLicense = categoryLicenseService.find(categoryLicensefilter);
		if (!resultCategoryLicense.isEmpty()) {
			resulElect.add(resultCategoryLicense.get(0));
		}
		DriverFilter driverfilter = new DriverFilter();
		driverfilter.setCategories(resulElect);
		resultDriver = driverService.find(driverfilter);
		Assert.assertEquals(5, resultDriver.size());
	}

	@Test
	public void testSort() {
		DriverFilter filter = new DriverFilter();
		category = null;
		filter.setCategories(category);
		filter.setLimit(null);
		filter.setOffset(null);
		filter.setSortOrder(true);
		filter.setSortProperty(Driver_.lastName);
		resultDriver = driverService.find(filter);
		Assert.assertEquals(5, resultDriver.size());
	}

	@Test
	public void testPage() {
		DriverFilter filter = new DriverFilter();
		// test paging
		int limit = 3;
		category = null;
		filter.setCategories(category);
		filter.setLimit(limit);
		filter.setOffset(0);
		resultDriver = driverService.find(filter);
		Assert.assertEquals(limit, resultDriver.size());
	}

	@Test
	public void testSearchEntry() {
		DriverFilter driverFilter = new DriverFilter();
		driverFilter.setLogin("logindriver1");
		resultDriver = driverService.find(driverFilter);
		Assert.assertEquals(1, resultDriver.size());
	}

	@Test
	public void testFetacEntry() {
		DriverFilter driverFilter = new DriverFilter();
		driverFilter.setFetchСredentials(true);
		resultDriver = driverService.find(driverFilter);
		Assert.assertEquals(5, resultDriver.size());
	}

	@Test
	public void testFetchCategories() {
		DriverFilter driverFilter = new DriverFilter();
		driverFilter.setFetchCategories(true);
		resultDriver = driverService.find(driverFilter);
		Assert.assertEquals(15, resultDriver.size());
	}
}
