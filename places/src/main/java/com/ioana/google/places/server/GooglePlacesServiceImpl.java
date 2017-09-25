package com.ioana.google.places.server;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ioana.google.places.client.GooglePlacesService;
import com.ioana.google.places.dto.CityDTO;
import com.ioana.google.places.dto.PlaceDTO;
import com.ioana.google.places.service.api.CityService;
import com.ioana.google.places.service.api.GoogleAPIService;
import com.ioana.google.places.service.api.PlaceService;

@Singleton
public class GooglePlacesServiceImpl extends RemoteServiceServlet implements
		GooglePlacesService {

	Logger LOG = Logger.getLogger(GooglePlacesServiceImpl.class);

	@Inject
	private CityService cityService;

	@Inject
	private PlaceService placeService;

	@Inject
	private GoogleAPIService googleAPIService;

	public ArrayList<PlaceDTO> search(String keyword, int radius) {
		ArrayList<PlaceDTO> resultList = null;

		CityDTO cityDTO = cityService.processCity(keyword);

		resultList = googleAPIService.getPlacesForCity(cityDTO, radius);

		placeService.savePlaces(resultList, cityDTO);

		return resultList;
	}

	@Override
	public void updatePlace(PlaceDTO placeDTO) {
		placeService.updatePlace(placeDTO);
	}

	@Override
	public PlaceDTO createPlace(PlaceDTO place) {
		placeService.createPlace(place);
		return place;
	}
}
