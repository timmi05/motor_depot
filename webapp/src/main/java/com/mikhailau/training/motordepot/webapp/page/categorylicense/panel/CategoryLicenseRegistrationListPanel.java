package com.mikhailau.training.motordepot.webapp.page.categorylicense.panel;

import java.io.Serializable;
import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.mikhailau.training.motordepot.dataaccess.filters.CategoryLicenseFilter;
import com.mikhailau.training.motordepot.datamodel.CategoryLicense;
import com.mikhailau.training.motordepot.datamodel.CategoryLicense_;
import com.mikhailau.training.motordepot.service.CategoryLicenseService;
import com.mikhailau.training.motordepot.webapp.page.categorylicense.CategoryLicenseAddPage;

public class CategoryLicenseRegistrationListPanel extends Panel {
	@Inject
	private CategoryLicenseService categoryLicenseService;

	public CategoryLicenseRegistrationListPanel(String id) {
		super(id);

		CategoryLicensesDataProvider categoryLicensesDataProvider = new CategoryLicensesDataProvider();
		DataView<CategoryLicense> dataView = new DataView<CategoryLicense>("rows", categoryLicensesDataProvider, 20) {
			@Override
			protected void populateItem(Item<CategoryLicense> item) {
				CategoryLicense categoryLicense = item.getModelObject();

				item.add(new Label("category", categoryLicense.getCategory()));

				Link<Void> editLink = new Link<Void>("edit-link") {
					@Override
					public void onClick() {
						setResponsePage(new CategoryLicenseAddPage(categoryLicense));
					}
				};

				Link<Void> deleteLink = new Link<Void>("delete-link") {
					@Override
					public void onClick() {
						try {
							categoryLicenseService.delete(categoryLicense.getId());
							setResponsePage(new CategoryLicenseAddPage(new CategoryLicense()));
						} catch (PersistenceException e) {
							System.out.print("Попытка удалить задействованную категорию");
						}
					}
				};
				item.add(deleteLink);
				item.add(editLink);
				
			}
		};
		add(dataView);
		add(new OrderByBorder("sort-category", CategoryLicense_.category, categoryLicensesDataProvider));
	}

	private class CategoryLicensesDataProvider extends SortableDataProvider<CategoryLicense, Serializable> {

		private CategoryLicenseFilter categoryLicenseFilter;

		public CategoryLicensesDataProvider() {
			super();

			categoryLicenseFilter = new CategoryLicenseFilter();
			setSort((Serializable) CategoryLicense_.category, SortOrder.ASCENDING);
		}

		@Override
		public Iterator<CategoryLicense> iterator(long first, long count) {
			Serializable property = getSort().getProperty();
			SortOrder propertySortOrder = getSortState().getPropertySortOrder(property);
			categoryLicenseFilter.setSortProperty((SingularAttribute) property);
			categoryLicenseFilter.setSortOrder(propertySortOrder.equals(SortOrder.ASCENDING) ? true : false);
			categoryLicenseFilter.setLimit((int) count);
			categoryLicenseFilter.setOffset((int) first);

			return categoryLicenseService.find(categoryLicenseFilter).iterator();
		}

		@Override
		public long size() {
			return categoryLicenseService.count(categoryLicenseFilter);
		}

		@Override
		public IModel<CategoryLicense> model(CategoryLicense object) {
			return new Model(object);
		}
	}
}
