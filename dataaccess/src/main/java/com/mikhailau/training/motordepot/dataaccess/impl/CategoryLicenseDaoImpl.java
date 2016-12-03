package com.mikhailau.training.motordepot.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import com.mikhailau.training.motordepot.dataaccess.CategoryLicenseDao;
import com.mikhailau.training.motordepot.dataaccess.filters.CategoryLicenseFilter;
import com.mikhailau.training.motordepot.datamodel.CategoryLicense;
import com.mikhailau.training.motordepot.datamodel.CategoryLicense_;

@Repository
public class CategoryLicenseDaoImpl extends AbstractDaoImpl<CategoryLicense, Long> implements CategoryLicenseDao {

	protected CategoryLicenseDaoImpl() {
		super(CategoryLicense.class);
	}

	@Override
	public Long count(CategoryLicenseFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<CategoryLicense> from = cq.from(CategoryLicense.class);
		cq.select(cb.count(from));
		TypedQuery<Long> q = em.createQuery(cq);

		return q.getSingleResult();
	}

	@Override
	public List<CategoryLicense> find(CategoryLicenseFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CategoryLicense> cq = cb.createQuery(CategoryLicense.class);
		Root<CategoryLicense> from = cq.from(CategoryLicense.class);

		// set selection
		cq.select(from);

		if (filter.getСategory() != null) {
			cq.where(cb.equal(from.get(CategoryLicense_.category), filter.getСategory()));
		}
		if (filter.getSortProperty() != null) {
			cq.orderBy(new OrderImpl(from.get(filter.getSortProperty()), filter.isSortOrder()));
		}
		TypedQuery<CategoryLicense> q = em.createQuery(cq);
		List<CategoryLicense> сategory = q.getResultList();

		return сategory;
	}
}
