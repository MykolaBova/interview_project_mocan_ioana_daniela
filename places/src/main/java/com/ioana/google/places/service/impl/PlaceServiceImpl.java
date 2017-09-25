package com.ioana.google.places.service.impl;

import java.util.List;
import java.util.UUID;

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
		CityDao cityDao = injector.getInstance(CityDao.class);
		City city = cityDao.findByName(placeDTO.getCity());
		Place newPlace = new Place();
		newPlace.setName(placeDTO.getName());
		newPlace.setReference(UUID.randomUUID().toString());
		newPlace.setIconLink(placeDTO.getIconLink());
		newPlace.setScope(placeDTO.getScope());
		newPlace.setDirty(true);
		newPlace.setCity(city);
		placeDAO.create(newPlace);
		return placeDTO;
	}

	@Override
	@Transactional
	public void savePlaces(List<PlaceDTO> places, CityDTO cityDTO) {
		PlaceDao placeDAO = injector.getInstance(PlaceDao.class);
		CityDao cityDao = injector.getInstance(CityDao.class);
		City city = cityDao.findByName(cityDTO.getName());
		placeDAO.deleteForCity(city);
		for (PlaceDTO placeDTO : places) {
			Place newPlace = new Place();
			newPlace.setName(placeDTO.getName());
			newPlace.setReference(placeDTO.getReference());
			newPlace.setCity(city);
			newPlace.setDirty(false);
			newPlace.setIconLink(placeDTO.getIconLink());
			newPlace.setScope(placeDTO.getScope());
			placeDAO.create(newPlace);
		}
	}

	@Override
	@Transactional
	public void updatePlace(PlaceDTO placeDTO) {
		PlaceDao placeDAO = injector.getInstance(PlaceDao.class);
		Place place = placeDAO.findByReference(placeDTO.getReference());
		place.setDirty(true);
		place.setName(placeDTO.getName());
		place.setIconLink(placeDTO.getIconLink());
		place.setScope(placeDTO.getScope());
		placeDAO.update(place);
	}

}
