package com.mikhailau.training.motordepot.dataaccess.impl;

import java.util.ArrayList;
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

import com.mikhailau.training.motordepot.dataaccess.ApplicationDao;
import com.mikhailau.training.motordepot.dataaccess.filters.ApplicationFilter;
import com.mikhailau.training.motordepot.datamodel.Application;
import com.mikhailau.training.motordepot.datamodel.ApplicationState;
import com.mikhailau.training.motordepot.datamodel.Application_;
import com.mikhailau.training.motordepot.datamodel.Customer_;
import com.mikhailau.training.motordepot.datamodel.Driver_;
import com.mikhailau.training.motordepot.datamodel.VehicleType_;

@Repository
public class ApplicationDaoImpl extends AbstractDaoImpl<Application, Long> implements ApplicationDao {

	protected ApplicationDaoImpl() {
		super(Application.class);
	}

	@Override
	public Long count(ApplicationFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Application> from = cq.from(Application.class);
		cq.select(cb.count(from));
		setAttributes(filter, cb, cq, from);
		TypedQuery<Long> q = em.createQuery(cq);

		return q.getSingleResult();
	}

	@Override
	public List<Application> find(ApplicationFilter filter) {

		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Application> cq = cb.createQuery(Application.class);
		Root<Application> from = cq.from(Application.class);
		// set selection
		cq.select(from);
		setAttributes(filter, cb, cq, from);
		// set fetching
		if (filter.isFetchCustomer()) {
			from.fetch(Application_.customer, JoinType.LEFT);
		}
		if (filter.isFetchDriver()) {
			from.fetch(Application_.driver, JoinType.LEFT);
		}
		if (filter.isFetchVehicleType()) {
			from.fetch(Application_.vehicleType, JoinType.LEFT);
		}
		// set sort params
		if (filter.getSortProperty() != null) {
			Path<Object> expression;
			if (Customer_.lastName.equals(filter.getSortProperty())) {
				expression = from.get(Application_.customer).get(filter.getSortProperty());
			} else if (Driver_.lastName.equals(filter.getSortProperty())) {
				expression = from.get(Application_.driver).get(filter.getSortProperty());
			} else if (VehicleType_.type.equals(filter.getSortProperty())) {
				expression = from.get(Application_.vehicleType).get(filter.getSortProperty());
			} else {
				expression = from.get(filter.getSortProperty());
			}
			cq.orderBy(new OrderImpl(expression, filter.isSortOrder()));
		}
		TypedQuery<Application> q = em.createQuery(cq);
		// set paging
		setPaging(filter, q);
		// set execute query
		List<Application> allApplication = q.getResultList();

		return allApplication;
	}

	private void setAttributes(ApplicationFilter filter, CriteriaBuilder cb, CriteriaQuery<?> cq,
			Root<Application> from) {

		if (filter.getStates() != null && !filter.getStates().isEmpty()) {
			List<Predicate> statePredicates = new ArrayList<>(filter.getStates().size());
			for (final ApplicationState state : filter.getStates()) {
				statePredicates.add(cb.equal(from.get(Application_.applicationState), state));
			}
			cq.where(cb.or(statePredicates.toArray(new Predicate[statePredicates.size()])));
		}
		if (filter.getDriver() != null) {
			cq.where(cb.equal(from.get(Application_.driver), filter.getDriver()));
		}
		if (filter.getCustomer() != null) {
			cq.where(cb.equal(from.get(Application_.customer), filter.getCustomer()));
		}
		if (filter.getVehicleType() != null) {
			cq.where(cb.equal(from.get(Application_.vehicleType), filter.getVehicleType()));
		}
		if (filter.getStates() != null && (filter.getStates().size() == 1) && filter.getDriver() != null) {
			Predicate applicationStateEqualCondition = cb.equal(from.get(Application_.applicationState),
					filter.getStates().get(0));
			Predicate driverEqualCondition = cb.equal(from.get(Application_.driver), filter.getDriver());
			cq.where(cb.and(applicationStateEqualCondition, driverEqualCondition));
		}
		if (filter.getStates() != null && (filter.getStates().size() == 1) && filter.getCustomer() != null) {
			Predicate applicationStateEqualCondition = cb.equal(from.get(Application_.applicationState),
					filter.getStates().get(0));
			Predicate customerEqualCondition = cb.equal(from.get(Application_.customer), filter.getCustomer());
			cq.where(cb.and(applicationStateEqualCondition, customerEqualCondition));
		}
	}
}