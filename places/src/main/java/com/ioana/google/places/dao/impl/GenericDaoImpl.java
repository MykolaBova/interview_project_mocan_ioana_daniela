package com.ioana.google.places.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.ioana.google.places.dao.api.GenericDao;

public class GenericDaoImpl<T> implements GenericDao<T> {

	protected EntityManager entityManager;

	protected Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public GenericDaoImpl(Class<T> entityClass) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("default");
		entityManager = emf.createEntityManager();
		this.entityClass = entityClass;
	}

	@Override
	public T create(T t) {
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.persist(t);
		tx.commit();
		entityManager.close();
		return t;
	}

	@Override
	public T read(Integer id) {
		return this.entityManager.find(entityClass, id);
	}

	@Override
	public T update(T t) {
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		this.entityManager.merge(t);
		tx.commit();
		entityManager.close();
		return t;
	}

	@Override
	public void delete(T t) {
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		t = this.entityManager.merge(t);
		this.entityManager.remove(t);
		tx.commit();
		entityManager.close();
	}

}
