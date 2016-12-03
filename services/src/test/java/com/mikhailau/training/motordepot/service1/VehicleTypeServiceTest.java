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

import com.mikhailau.training.motordepot.dataaccess.filters.VehicleTypeFilter;
import com.mikhailau.training.motordepot.datamodel.VehicleType;
import com.mikhailau.training.motordepot.datamodel.VehicleType_;
import com.mikhailau.training.motordepot.service.VehicleTypeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:service-context-test.xml" })
public class VehicleTypeServiceTest {
	@Inject
	private VehicleTypeService vehicleTypeService;

	private static VehicleType vehicleType;
	private static List<VehicleType> result;

	@Before
	public void testIn() {
		int testObjectsCount = 5;
		for (int i = 0; i < testObjectsCount; i++) {
			VehicleType vehicleType = new VehicleType();
			vehicleType.setType("type" + i);
			vehicleType.setExists(true);
			vehicleTypeService.saveOrUpdate(vehicleType);
		}
	}

	@After
	public void testOut() {
		// clean all data from users
		List<VehicleType> all = vehicleTypeService.getAll();
		for (VehicleType vehicleType : all) {
			vehicleTypeService.delete(vehicleType.getId());
		}
	}

	@Test
	public void testVehicleTypeInsert() {
		vehicleType = new VehicleType();
		vehicleType.setType("qwerty");
		vehicleType.setExists(true);
		vehicleTypeService.saveOrUpdate(vehicleType);
		VehicleType insertVehicleType = vehicleTypeService.getVehicleType(vehicleType.getId());
		Assert.assertNotNull(insertVehicleType);
	}

	@Test
	public void testVehicleTypeUpdate() {
		VehicleTypeFilter filter = new VehicleTypeFilter();
		filter.setTypeForFilter("type2");
		result = vehicleTypeService.find(filter);
		vehicleType = result.get(0);
		String updatedCategory = "asdfgh";
		vehicleType.setType(updatedCategory);
		vehicleTypeService.saveOrUpdate(vehicleType);
		Assert.assertEquals(updatedCategory, vehicleTypeService.getVehicleType(vehicleType.getId()).getType());
	}

	@Test
	public void testVehicleTypeDelete1() {
		VehicleTypeFilter filter = new VehicleTypeFilter();
		filter.setTypeForFilter("type3");
		result = vehicleTypeService.find(filter);
		vehicleType = result.get(0);
		vehicleTypeService.delete(vehicleType.getId());
		Assert.assertNull(vehicleTypeService.getVehicleType(vehicleType.getId()));
	}

	@Test
	public void testSearch() {
		VehicleTypeFilter filter = new VehicleTypeFilter();
		filter.setTypeForFilter("type1");
		result = vehicleTypeService.find(filter);
		Assert.assertEquals(1, result.size());
	}

	@Test
	public void testSearchAll() {
		VehicleTypeFilter filter = new VehicleTypeFilter();
		result = vehicleTypeService.find(filter);
		Assert.assertEquals(5, result.size());
	}

	@Test
	public void testPaging() {
		VehicleTypeFilter filter = new VehicleTypeFilter();
		int limit = 3;
		filter.setLimit(limit);
		filter.setOffset(0);
		result = vehicleTypeService.find(filter);
		Assert.assertEquals(limit, result.size());
	}

	@Test
	public void testSort() {
		VehicleTypeFilter filter = new VehicleTypeFilter();
		filter.setLimit(null);
		filter.setOffset(null);
		filter.setSortOrder(true);
		filter.setSortProperty(VehicleType_.type);
		result = vehicleTypeService.find(filter);
		Assert.assertEquals(5, result.size());
	}
}
