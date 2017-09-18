package com.ioana.google.places.dao.impl;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.google.inject.Inject;
import com.ioana.google.places.dao.entities.City;

public class CityDao extends GenericDaoImpl<City> {

	@Inject
	public CityDao() {
		super(City.class);
		// TODO Auto-generated constructor stub
	}

	public City findByName(String name) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<City> q = cb.createQuery(City.class);
		Root<City> c = q.from(City.class);
		q.select(c);
		q.where(cb.equal(c.get("name"), name));
		Query query = em.createQuery(q);
		List<City> cities = query.getResultList();

		/*
		 * Query query = em.createNamedQuery("City.findByName").setParameter(
		 * "name", name); List<City> cities = query.getResultList();
		 */
		if (cities.size() > 0) {
			return cities.get(0);
		} else
			return null;
	}
}
