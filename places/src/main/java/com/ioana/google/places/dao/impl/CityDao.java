package com.ioana.google.places.dao.impl;

import java.util.List;

import javax.persistence.Query;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ioana.google.places.dao.entities.City;

@Singleton
public class CityDao extends GenericDaoImpl<City> {

	@Inject
	public CityDao() {
		super(City.class);
		// TODO Auto-generated constructor stub
	}

	public City findByName(String name) {
		Query query = emProvider.get().createNamedQuery("City.findByName")
				.setParameter("name", name);
		List<City> cities = query.getResultList();
		if (cities.size() > 0) {
			return cities.get(0);
		} else
			return null;
	}
}
