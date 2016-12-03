package com.mikhailau.training.motordepot.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.mikhailau.training.motordepot.dataaccess.CredentialsDao;
import com.mikhailau.training.motordepot.dataaccess.filters.CredentialsFilter;
import com.mikhailau.training.motordepot.datamodel.Credentials;
import com.mikhailau.training.motordepot.datamodel.Credentials_;

@Repository
public class CredentialsDaoImpl extends AbstractDaoImpl<Credentials, Long> implements CredentialsDao {

	protected CredentialsDaoImpl() {
		super(Credentials.class);
	}

	@Override
	public Credentials find(String login, String password, CredentialsFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Credentials> cq = cb.createQuery(Credentials.class);
		Root<Credentials> from = cq.from(Credentials.class);

		// set selection
		cq.select(from);

		if (filter.isCredentials()) {
			Predicate loginEqualCondition = cb.equal(from.get(Credentials_.login), login);
			Predicate passwordEqualCondition = cb.equal(from.get(Credentials_.password), password);
			cq.where(cb.and(loginEqualCondition, passwordEqualCondition));
		}
		TypedQuery<Credentials> q = em.createQuery(cq);
		List<Credentials> user = q.getResultList();
		if (user.isEmpty()) {
			return null;
		} else if (user.size() == 1) {
			return user.get(0);
		} else {
			throw new IllegalStateException("more than 1 user found ");
		}
	}

	@Override
	public Credentials find(String login, CredentialsFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Credentials> cq = cb.createQuery(Credentials.class);
		Root<Credentials> from = cq.from(Credentials.class);

		// set selection
		cq.select(from);

		if (filter.isRegistration()) {
			cq.where(cb.equal(from.get(Credentials_.login), login));
		}
		TypedQuery<Credentials> q = em.createQuery(cq);
		List<Credentials> user = q.getResultList();
		if (user.isEmpty()) {
			return null;
		} else {
			return user.get(0);
		}
	}
}
