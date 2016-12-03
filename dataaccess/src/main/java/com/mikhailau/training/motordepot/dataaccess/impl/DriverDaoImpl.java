package com.mikhailau.training.motordepot.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import com.mikhailau.training.motordepot.dataaccess.DriverDao;
import com.mikhailau.training.motordepot.dataaccess.filters.DriverFilter;
import com.mikhailau.training.motordepot.datamodel.CategoryLicense;
import com.mikhailau.training.motordepot.datamodel.Credentials_;
import com.mikhailau.training.motordepot.datamodel.Driver;
import com.mikhailau.training.motordepot.datamodel.Driver_;

@Repository
public class DriverDaoImpl extends AbstractDaoImpl<Driver, Long> implements DriverDao {

	protected DriverDaoImpl() {
		super(Driver.class);
	}

	@Override
	public Long count(DriverFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Driver> from = cq.from(Driver.class);
		cq.select(cb.count(from));
		TypedQuery<Long> q = em.createQuery(cq);

		return q.getSingleResult();
	}

	@Override
	public List<Driver> find(DriverFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Driver> cq = cb.createQuery(Driver.class);
		Root<Driver> from = cq.from(Driver.class);
		// set selection
		cq.select(from);

		if (filter.isFetchСredentials()) {
			from.fetch(Driver_.credentials, JoinType.LEFT);
		}
		if (filter.isFetchCategories()) {
			from.fetch(Driver_.categoryLicense, JoinType.LEFT);
		}
		if (filter.isStateFreeTrue()) {
			cq.where(cb.equal(from.get(Driver_.stateFree), true));
		}
		if (filter.isStateFreeFalse()) {
			cq.where(cb.equal(from.get(Driver_.stateFree), false));
		}
		if (filter.getLogin() != null) {
			cq.where(cb.equal(from.get(Driver_.credentials).get(Credentials_.login), filter.getLogin()));
		}
		if ((filter.getDriverFName() != null) && (filter.getDriverLName()) != null) {
			Predicate fNameEqualCondition = cb.equal(from.get(Driver_.firstName), filter.getDriverFName());
			Predicate lNameEqualCondition = cb.equal(from.get(Driver_.lastName), filter.getDriverLName());
			cq.where(cb.and(fNameEqualCondition, lNameEqualCondition));
		}
		if (filter.getCategories() != null) {
			final List<Predicate> ands = new ArrayList<>();
			for (final CategoryLicense categories : filter.getCategories()) {
				ands.add(cb.isMember(categories, from.get(Driver_.categoryLicense)));
			}
			cq.where(cb.or(ands.toArray(new Predicate[ands.size()])));
		}
		// set sort params
		if (filter.getSortProperty() != null) {
			cq.orderBy(new OrderImpl(from.get(filter.getSortProperty()), filter.isSortOrder()));
		}
		TypedQuery<Driver> q = em.createQuery(cq);
		// set paging
		setPaging(filter, q);
		// set execute query
		List<Driver> allDriver = q.getResultList();

		return allDriver;
	}
}
