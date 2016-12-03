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
import com.mikhailau.training.motordepot.dataaccess.filters.VehicleFilter;
import com.mikhailau.training.motordepot.dataaccess.filters.VehicleTypeFilter;
import com.mikhailau.training.motordepot.datamodel.CategoryLicense;
import com.mikhailau.training.motordepot.datamodel.Credentials;
import com.mikhailau.training.motordepot.datamodel.Driver;
import com.mikhailau.training.motordepot.datamodel.Role;
import com.mikhailau.training.motordepot.datamodel.Vehicle;
import com.mikhailau.training.motordepot.datamodel.VehicleType;
import com.mikhailau.training.motordepot.service.CategoryLicenseService;
import com.mikhailau.training.motordepot.service.DriverService;
import com.mikhailau.training.motordepot.service.VehicleService;
import com.mikhailau.training.motordepot.service.VehicleTypeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:service-context-test.xml" })
public class VehicleServiceTest {
	@Inject
	private VehicleService vehicleService;

	@Inject
	private DriverService driverService;

	@Inject
	private VehicleTypeService vehicleTypeService;

	@Inject
	private CategoryLicenseService categoryLicenseService;

	private static List<CategoryLicense> category;
	private static List<CategoryLicense> resultCategoryLicense;
	private static List<VehicleType> resultVehicleType;
	private static List<Vehicle> resultVehicle;
	private static List<VehicleType> vehicleTypeResult;

	private static VehicleType vehicleType;
	private static CategoryLicense categoryLicense;
	private static Credentials credentials;
	private static Driver driver;
	private static Vehicle vehicle;

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
			credentials = new Credentials();
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
			credentials.setLogin("logindriver" + i);
			credentials.setPassword("pswddriver" + i);
			credentials.setRole(Role.DRIVER);
			driverService.registerDriver(vehicle, driver, credentials);
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
	public void testUpdatedVehicle() {
		VehicleFilter vehicleFilter = new VehicleFilter();
		vehicleFilter.setVehicleLicensePlate("12341");
		resultVehicle = vehicleService.find(vehicleFilter);
		vehicle = resultVehicle.get(0);
		boolean stateAfterFreight = false;
		vehicle.setStateAfterFreight(stateAfterFreight);
		vehicleService.update(vehicle);
		Assert.assertEquals(stateAfterFreight, vehicleService.getVehicle(vehicle.getId()).getStateAfterFreight());
	}

	@Test
	public void testSearchStateTrue() {
		VehicleFilter vehicleFilter = new VehicleFilter();
		vehicleFilter.setStateTrue(true);
		resultVehicle = vehicleService.find(vehicleFilter);
		Assert.assertEquals(5, resultVehicle.size());
	}

	@Test
	public void testSearchStateFalse() {
		VehicleFilter vehicleFilter = new VehicleFilter();
		vehicleFilter.setStateFalse(true);
		resultVehicle = vehicleService.find(vehicleFilter);
		Assert.assertEquals(0, resultVehicle.size());
	}

	@Test
	public void testSearchDriver() {
		VehicleFilter vehicleFilter = new VehicleFilter();
		vehicleFilter.setDriver("logindriver1");
		vehicleFilter.setFetchDriver(true);
		resultVehicle = vehicleService.find(vehicleFilter);
		Assert.assertEquals(1, resultVehicle.size());
	}

	@Test
	public void testSearchVehicleType() {
		VehicleFilter vehicleFilter = new VehicleFilter();
		VehicleTypeFilter vehicleTypefilter = new VehicleTypeFilter();
		vehicleTypefilter.setTypeForFilter("type3");
		vehicleTypeResult = vehicleTypeService.find(vehicleTypefilter);
		vehicleType = vehicleTypeResult.get(0);
		vehicleFilter.setVehicleType(vehicleType);
		vehicleFilter.setFetchVehicleType(true);
		resultVehicle = vehicleService.find(vehicleFilter);
		Assert.assertEquals(1, resultVehicle.size());
	}
}
