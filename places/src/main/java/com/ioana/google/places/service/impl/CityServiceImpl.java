package com.ioana.google.places.service.impl;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import com.ioana.google.places.dao.entities.City;
import com.ioana.google.places.dao.impl.CityDao;
import com.ioana.google.places.dto.CityDTO;
import com.ioana.google.places.dto.LocationDTO;
import com.ioana.google.places.helper.PlaceLocation;
import com.ioana.google.places.service.api.CityService;

@Singleton
public class CityServiceImpl implements CityService {

	private Injector injector;

	@Inject
	public CityServiceImpl(Injector injector) {
		this.injector = injector;
	}

	@Override
	@Transactional
	public CityDTO processCity(String cityName) {
		CityDao cityDao = injector.getInstance(CityDao.class);

		CityDTO newCityDTO = new CityDTO();
		City city = cityDao.findByName(cityName);
		if (city == null) {
			LocationDTO location = PlaceLocation.getLocation(cityName);
			// TODO handle location not found (wrong city name)
			City newCity = new City();
			newCity.setName(cityName);
			newCity.setLatitude(location.getLatitude());
			newCity.setLongitude(location.getLongitude());
			city = cityDao.create(newCity);
		}
		newCityDTO.setLatitude(city.getLatitude());
		newCityDTO.setName(city.getName());
		newCityDTO.setLongitude(city.getLongitude());

		return newCityDTO;
	}

}
