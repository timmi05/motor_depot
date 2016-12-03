package com.mikhailau.training.motordepot.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import com.mikhailau.training.motordepot.dataaccess.VehicleDao;
import com.mikhailau.training.motordepot.dataaccess.filters.VehicleFilter;
import com.mikhailau.training.motordepot.datamodel.Credentials_;
import com.mikhailau.training.motordepot.datamodel.Driver_;
import com.mikhailau.training.motordepot.datamodel.Vehicle;
import com.mikhailau.training.motordepot.datamodel.VehicleType_;
import com.mikhailau.training.motordepot.datamodel.Vehicle_;

@Repository
public class VehicleDaoImpl extends AbstractDaoImpl<Vehicle, Long> implements VehicleDao {

	protected VehicleDaoImpl() {
		super(Vehicle.class);
	}

	@Override
	public Long count(VehicleFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Vehicle> from = cq.from(Vehicle.class);
		cq.select(cb.count(from));
		setAttributes(filter, cb, cq, from);
		TypedQuery<Long> q = em.createQuery(cq);

		return q.getSingleResult();
	}

	@Override
	public List<Vehicle> find(VehicleFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Vehicle> cq = cb.createQuery(Vehicle.class);
		Root<Vehicle> from = cq.from(Vehicle.class);
		// set selection
		cq.select(from);
		setAttributes(filter, cb, cq, from);

		// set fetching
		if (filter.isFetchDriver()) {
			from.fetch(Vehicle_.driver, JoinType.LEFT);
		}
		if (filter.isFetchVehicleType()) {
			from.fetch(Vehicle_.vehicleType, JoinType.LEFT);
		}
		// set sort params
		if (filter.getSortProperty() != null) {
			Path<Object> expression;
			if (Driver_.lastName.equals(filter.getSortProperty())) {
				expression = from.get(Vehicle_.driver).get(filter.getSortProperty());
			} else if (Driver_.firstName.equals(filter.getSortProperty())) {
				expression = from.get(Vehicle_.driver).get(filter.getSortProperty());
			} else if (Driver_.leadTime.equals(filter.getSortProperty())) {
				expression = from.get(Vehicle_.driver).get(filter.getSortProperty());
			} else if (Driver_.stateFree.equals(filter.getSortProperty())) {
				expression = from.get(Vehicle_.driver).get(filter.getSortProperty());
			} else if (VehicleType_.type.equals(filter.getSortProperty())) {
				expression = from.get(Vehicle_.vehicleType).get(filter.getSortProperty());
			} else {
				expression = from.get(filter.getSortProperty());
			}
			cq.orderBy(new OrderImpl(expression, filter.isSortOrder()));
		}
		TypedQuery<Vehicle> q = em.createQuery(cq);
		// set paging
		setPaging(filter, q);
		// set execute query
		List<Vehicle> allVehicle = q.getResultList();

		return allVehicle;
	}

	private void setAttributes(VehicleFilter filter, CriteriaBuilder cb, CriteriaQuery<?> cq, Root<Vehicle> from) {
		
		if (filter.isStateTrue()) {
			Predicate stateDriveerEqualCondition = cb.equal(from.get(Vehicle_.stateAfterFreight), true);
			Predicate stateVehicleEqualCondition = cb.equal(from.get(Vehicle_.driver).get(Driver_.stateFree), true);
			cq.where(cb.and(stateDriveerEqualCondition, stateVehicleEqualCondition));
		}
		if (filter.isStateFalse()) {
			Predicate stateDriveerEqualCondition = cb.equal(from.get(Vehicle_.stateAfterFreight), false);
			Predicate stateVehicleEqualCondition = cb.equal(from.get(Vehicle_.driver).get(Driver_.stateFree), false);
			cq.where(cb.or(stateDriveerEqualCondition, stateVehicleEqualCondition));
		}
		if (filter.getDriver() != null) {
			cq.where(cb.equal(from.get(Vehicle_.driver).get(Driver_.credentials).get(Credentials_.login),
					filter.getDriver()));
		}
		if (filter.getVehicleType() != null) {
			cq.where(cb.equal(from.get(Vehicle_.vehicleType), filter.getVehicleType()));
		}
		if (filter.getVehicleLicensePlate() != null) {
			cq.where(cb.equal(from.get(Vehicle_.licensePlate), filter.getVehicleLicensePlate()));
		}
		if (filter.getVehicleModel() != null) {
			cq.where(cb.equal(from.get(Vehicle_.model), filter.getVehicleModel()));
		}
	}

	@Override
	public Vehicle getWithAttributes(Long id) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Vehicle> cq = cb.createQuery(Vehicle.class);
		Root<Vehicle> from = cq.from(Vehicle.class);
		// set selection
		cq.select(from);

		from.fetch(Vehicle_.vehicleType, JoinType.LEFT);
		from.fetch(Vehicle_.driver, JoinType.LEFT);
		from.fetch(Vehicle_.driver, JoinType.LEFT).fetch(Driver_.credentials, JoinType.LEFT);
		from.fetch(Vehicle_.driver, JoinType.LEFT).fetch(Driver_.categoryLicense, JoinType.LEFT);

		cq.where(cb.equal(from.get(Vehicle_.id), id));
		cq.distinct(true);
		TypedQuery<Vehicle> q = em.createQuery(cq);
		
		return q.getSingleResult();
	}
}
