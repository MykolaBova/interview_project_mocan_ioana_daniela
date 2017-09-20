package com.ioana.google.places.dao.impl;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.ioana.google.places.dao.entities.City;
import com.ioana.google.places.dao.entities.Place;

public class PlaceDao extends GenericDaoImpl<Place> {

	public PlaceDao() {
		super(Place.class);
		// TODO Auto-generated constructor stub
	}

	public void deleteForCity(City city) {
		CriteriaBuilder cb = this.em.getCriteriaBuilder();

		CriteriaDelete<Place> delete = cb.createCriteriaDelete(Place.class);
		Root<Place> c = delete.from(Place.class);
		delete.where(cb.equal(c.get("city"), city));

		// perform update
		int result = this.em.createQuery(delete).executeUpdate();
	}

	public Place findByReference(String reference) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Place> q = cb.createQuery(Place.class);
		Root<Place> c = q.from(Place.class);
		q.select(c);
		q.where(cb.equal(c.get("reference"), reference));
		Query query = em.createQuery(q);
		List<Place> places = query.getResultList();

		/*
		 * Query query = em.createNamedQuery("City.findByName").setParameter(
		 * "name", name); List<City> cities = query.getResultList();
		 */
		if (places.size() > 0) {
			return places.get(0);
		} else
			return null;
	}
}
