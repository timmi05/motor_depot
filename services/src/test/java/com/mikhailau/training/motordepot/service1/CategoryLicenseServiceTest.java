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
import org.springframework.transaction.TransactionSystemException;

import com.mikhailau.training.motordepot.dataaccess.filters.CategoryLicenseFilter;
import com.mikhailau.training.motordepot.datamodel.CategoryLicense;
import com.mikhailau.training.motordepot.datamodel.CategoryLicense_;
import com.mikhailau.training.motordepot.service.CategoryLicenseService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:service-context-test.xml" })
public class CategoryLicenseServiceTest {
	@Inject
	private CategoryLicenseService categoryLicenseService;

	private static CategoryLicense categoryLicense;
	private static CategoryLicense categoryLicense1;
	private static List<CategoryLicense> result;
	private static List<CategoryLicense> resulElect;

	@Before
	public void testIn() {
		int testObjectsCount = 6;
		for (int i = 0; i < testObjectsCount; i++) {
			categoryLicense = new CategoryLicense();
			categoryLicense.setCategory("B" + i);
			categoryLicenseService.saveOrUpdate(categoryLicense);
		}
	}

	@After
	public void testOut() {
		List<CategoryLicense> all = categoryLicenseService.getAll();
		for (CategoryLicense categoryLicense : all) {
			categoryLicenseService.delete(categoryLicense.getId());
		}
	}

	@Test
	public void testCategoryLicenseInsert() {
		try {
			categoryLicense = new CategoryLicense();
			categoryLicense.setCategory("qwerty");
			categoryLicenseService.saveOrUpdate(categoryLicense);
			CategoryLicense insertCategoryLicense = categoryLicenseService.getCategoryLicense(categoryLicense.getId());
			Assert.assertNotNull(insertCategoryLicense);
		} catch (TransactionSystemException e) {
			System.out.print("Данная каиегория уже есть в списке");
		}
	}

	@Test
	public void testCategoryLicenseUpdated() {
		resulElect = new ArrayList<CategoryLicense>();
		CategoryLicenseFilter filter = new CategoryLicenseFilter();
		filter.setСategory("B2");
		result = categoryLicenseService.find(filter);
		categoryLicense1 = result.get(0);
		String updatedCategory = "update";
		categoryLicense1.setCategory(updatedCategory);
		try {
			categoryLicenseService.saveOrUpdate(categoryLicense1);
			Assert.assertEquals(updatedCategory,
					categoryLicenseService.getCategoryLicense(categoryLicense1.getId()).getCategory());
		} catch (TransactionSystemException e) {
			System.out.print("Данная каиегория уже есть в списке");
		}
	}

	@Test
	public void testCategoryLicenseDelet() {
		resulElect = new ArrayList<CategoryLicense>();
		CategoryLicenseFilter filter = new CategoryLicenseFilter();
		filter.setСategory("B3");
		result = categoryLicenseService.find(filter);
		categoryLicense1 = result.get(0);
		categoryLicenseService.delete(categoryLicense1.getId());
		Assert.assertNull(categoryLicenseService.getCategoryLicense(categoryLicense1.getId()));
	}

	@Test
	public void testFiltrSort() {
		CategoryLicenseFilter filter = new CategoryLicenseFilter();
		filter.setSortOrder(true);
		filter.setSortProperty(CategoryLicense_.category);
		result = categoryLicenseService.find(filter);
		Assert.assertEquals(6, result.size());
	}

	@Test
	public void testFiltrSearch() {
		resulElect = new ArrayList<CategoryLicense>();
		CategoryLicenseFilter filter = new CategoryLicenseFilter();
		filter.setСategory("B1");
		result = categoryLicenseService.find(filter);
		if (!result.isEmpty()) {
			resulElect.add(result.get(0));
		}
		filter.setСategory("B3");
		result = categoryLicenseService.find(filter);
		if (!result.isEmpty()) {
			resulElect.add(result.get(0));
		}
		filter.setСategory("B4");
		result = categoryLicenseService.find(filter);
		if (!result.isEmpty()) {
			resulElect.add(result.get(0));
		}
		Assert.assertEquals(3, resulElect.size());
	}
}
