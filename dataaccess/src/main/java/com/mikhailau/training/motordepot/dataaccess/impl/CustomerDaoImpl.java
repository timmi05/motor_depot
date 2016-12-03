package com.mikhailau.training.motordepot.dataaccess.impl;

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

import com.mikhailau.training.motordepot.dataaccess.CustomerDao;
import com.mikhailau.training.motordepot.dataaccess.filters.CustomerFilter;
import com.mikhailau.training.motordepot.datamodel.Credentials_;
import com.mikhailau.training.motordepot.datamodel.Customer;
import com.mikhailau.training.motordepot.datamodel.Customer_;

@Repository
public class CustomerDaoImpl extends AbstractDaoImpl<Customer, Long> implements CustomerDao {

	protected CustomerDaoImpl() {
		super(Customer.class);
	}

	@Override
	public Long count(CustomerFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Customer> from = cq.from(Customer.class);
		cq.select(cb.count(from));
		TypedQuery<Long> q = em.createQuery(cq);

		return q.getSingleResult();
	}

	@Override
	public List<Customer> find(CustomerFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
		Root<Customer> from = cq.from(Customer.class);
		// set selection
		cq.select(from);

		if (filter.isFetchСredentials()) {
			from.fetch(Customer_.credentials, JoinType.LEFT);
		}
		if (filter.getLogin() != null) {
			cq.where(cb.equal(from.get(Customer_.credentials).get(Credentials_.login), filter.getLogin()));
		}
		if (filter.getCustomerLName() != null) {
			cq.where(cb.equal(from.get(Customer_.lastName), filter.getCustomerLName()));
		}
		if ((filter.getCustomerLName() != null) && (filter.getCustomerFName()) != null) {
			Predicate fNameEqualCondition = cb.equal(from.get(Customer_.firstName), filter.getCustomerFName());
			Predicate lNameEqualCondition = cb.equal(from.get(Customer_.lastName), filter.getCustomerLName());
			cq.where(cb.and(fNameEqualCondition, lNameEqualCondition));
		}
		// set sort params
		if (filter.getSortProperty() != null) {
			cq.orderBy(new OrderImpl(from.get(filter.getSortProperty()), filter.isSortOrder()));
		}
		TypedQuery<Customer> q = em.createQuery(cq);
		// set paging
		setPaging(filter, q);
		List<Customer> allCustomer = q.getResultList();

		return allCustomer;
	}

	@Override
	public Customer getWithAttributes(Long id) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
		Root<Customer> from = cq.from(Customer.class);
		// set selection
		cq.select(from);

		from.fetch(Customer_.credentials, JoinType.LEFT);
		cq.where(cb.equal(from.get(Customer_.id), id));
		TypedQuery<Customer> q = em.createQuery(cq);

		return q.getSingleResult();
	}
}
