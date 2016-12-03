package com.mikhailau.training.motordepot.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import com.mikhailau.training.motordepot.dataaccess.VehicleTypeDao;
import com.mikhailau.training.motordepot.dataaccess.filters.VehicleTypeFilter;
import com.mikhailau.training.motordepot.datamodel.VehicleType;
import com.mikhailau.training.motordepot.datamodel.VehicleType_;

@Repository
public class VehicleTypeDaoImpl extends AbstractDaoImpl<VehicleType, Long> implements VehicleTypeDao {

	protected VehicleTypeDaoImpl() {
		super(VehicleType.class);
	}

	@Override
	public Long count(VehicleTypeFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<VehicleType> from = cq.from(VehicleType.class);
		cq.select(cb.count(from));
		setAttributes(filter, cb, cq, from);
		TypedQuery<Long> q = em.createQuery(cq);

		return q.getSingleResult();
	}

	@Override
	public List<VehicleType> find(VehicleTypeFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<VehicleType> cq = cb.createQuery(VehicleType.class);
		Root<VehicleType> from = cq.from(VehicleType.class);
		// set selection
		cq.select(from);
		setAttributes(filter, cb, cq, from);
		// set sort params
		if (filter.getSortProperty() != null) {
			cq.orderBy(new OrderImpl(from.get(filter.getSortProperty()), filter.isSortOrder()));
		}
		TypedQuery<VehicleType> q = em.createQuery(cq);
		// set paging
		setPaging(filter, q);
		// set execute query
		List<VehicleType> alltype = q.getResultList();

		return alltype;
	}

	private void setAttributes(VehicleTypeFilter filter, CriteriaBuilder cb, CriteriaQuery<?> cq,
			Root<VehicleType> from) {
		if (filter.isExistsTrue()) {
			cq.where(cb.equal(from.get(VehicleType_.exists), true));
		}
		if (filter.getTypeForFilter() != null) {
			cq.where(cb.equal(from.get(VehicleType_.type), filter.getTypeForFilter()));
		}
	}
}
