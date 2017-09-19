package com.ioana.google.places.service.impl;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import com.ioana.google.places.dao.entities.City;
import com.ioana.google.places.dao.entities.Place;
import com.ioana.google.places.dao.impl.CityDao;
import com.ioana.google.places.dao.impl.PlaceDao;
import com.ioana.google.places.dto.CityDTO;
import com.ioana.google.places.dto.PlaceDTO;
import com.ioana.google.places.service.api.PlaceService;

@Singleton
public class PlaceServiceImpl implements PlaceService {

	private Injector injector;

	@Inject
	public PlaceServiceImpl(Injector injector) {
		this.injector = injector;
	}

	@Override
	@Transactional
	public PlaceDTO createPlace(PlaceDTO placeDTO) {
		PlaceDao placeDAO = injector.getInstance(PlaceDao.class);
		Place newPlace = new Place();
		newPlace.setName(placeDTO.getName());
		newPlace.setReference(placeDTO.getReference());
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void savePlaces(List<PlaceDTO> places, CityDTO cityDTO) {
		PlaceDao placeDAO = injector.getInstance(PlaceDao.class);
		CityDao cityDao = injector.getInstance(CityDao.class);
		City city = cityDao.findByName(cityDTO.getName());
		for (PlaceDTO placeDTO : places) {
			Place newPlace = new Place();
			newPlace.setName(placeDTO.getName());
			newPlace.setReference(placeDTO.getReference());
			newPlace.setCity(city);
			placeDAO.create(newPlace);
		}
	}

}