package com.ioana.google.places.dao.impl;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.ioana.google.places.dao.api.GenericDao;

public class GenericDaoImpl<T> implements GenericDao<T> {

	@Inject
	protected Provider<EntityManager> emProvider;

	protected Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public GenericDaoImpl(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	@Override
	public T create(T t) {
		emProvider.get().persist(t);
		return t;
	}

	@Override
	public T read(Integer id) {
		return this.emProvider.get().find(entityClass, id);
	}

	@Override
	public T update(T t) {
		this.emProvider.get().merge(t);
		return t;
	}

	@Override
	public void delete(T t) {
		t = this.emProvider.get().merge(t);
		this.emProvider.get().remove(t);
	}

}
