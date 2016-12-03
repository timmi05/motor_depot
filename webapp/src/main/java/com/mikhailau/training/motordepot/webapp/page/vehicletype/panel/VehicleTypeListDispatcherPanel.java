package com.mikhailau.training.motordepot.webapp.page.vehicletype.panel;

import java.io.Serializable;
import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.mikhailau.training.motordepot.dataaccess.filters.VehicleTypeFilter;
import com.mikhailau.training.motordepot.datamodel.VehicleType;
import com.mikhailau.training.motordepot.datamodel.VehicleType_;
import com.mikhailau.training.motordepot.service.VehicleTypeService;
import com.mikhailau.training.motordepot.webapp.page.vehicletype.VehicleTypeAddPage;
import com.mikhailau.training.motordepot.webapp.page.vehicletype.VehicleTypePage;

public class VehicleTypeListDispatcherPanel extends Panel {
	@Inject
	private VehicleTypeService vehicleTypeService;

	public VehicleTypeListDispatcherPanel(String id) {
		super(id);

		VehicleTypesDataProvider vehicleTypesDataProvider = new VehicleTypesDataProvider();
		DataView<VehicleType> dataView = new DataView<VehicleType>("rows", vehicleTypesDataProvider, 5) {
			@Override
			protected void populateItem(Item<VehicleType> item) {
				VehicleType vehicleType = item.getModelObject();

				item.add(new Label("type", vehicleType.getType()));
				CheckBox checkbox = new CheckBox("thereIs", Model.of(vehicleType.getExists()));
				checkbox.setEnabled(false);
				item.add(checkbox);

				Link<Void> editLink = new Link<Void>("edit-link") {
					@Override
					public void onClick() {
						setResponsePage(new VehicleTypeAddPage(vehicleType));
					}
				};

				Link<Void> deleteLink = new Link<Void>("delete-link") {
					@Override
					public void onClick() {
						try {
							vehicleTypeService.delete(vehicleType.getId());
						} catch (PersistenceException e) {
							System.out.println("caughth persistance exception");
						}

						setResponsePage(new VehicleTypePage());
					}
				};

				item.add(deleteLink);
				item.add(editLink);
			}
		};
		add(dataView);
		add(new PagingNavigator("paging", dataView));
		add(new OrderByBorder("sort-type", VehicleType_.type, vehicleTypesDataProvider));
		OrderByBorder thereIsLable = new OrderByBorder("sort-thereIs", VehicleType_.exists, vehicleTypesDataProvider);

		Label actionsLable = new Label("actions");
		add(actionsLable);
		add(thereIsLable);
	}

	private class VehicleTypesDataProvider extends SortableDataProvider<VehicleType, Serializable> {

		private VehicleTypeFilter vehicleTypeFilter;

		public VehicleTypesDataProvider() {
			super();
			vehicleTypeFilter = new VehicleTypeFilter();

			setSort((Serializable) VehicleType_.id, SortOrder.ASCENDING);
		}

		@Override
		public Iterator<VehicleType> iterator(long first, long count) {
			Serializable property = getSort().getProperty();
			SortOrder propertySortOrder = getSortState().getPropertySortOrder(property);

			vehicleTypeFilter.setSortProperty((SingularAttribute) property);
			vehicleTypeFilter.setSortOrder(propertySortOrder.equals(SortOrder.ASCENDING) ? true : false);

			vehicleTypeFilter.setLimit((int) count);
			vehicleTypeFilter.setOffset((int) first);
			return vehicleTypeService.find(vehicleTypeFilter).iterator();
		}

		@Override
		public long size() {
			return vehicleTypeService.count(vehicleTypeFilter);
		}

		@Override
		public IModel<VehicleType> model(VehicleType object) {
			return new Model<VehicleType>(object);
		}
	}
}
