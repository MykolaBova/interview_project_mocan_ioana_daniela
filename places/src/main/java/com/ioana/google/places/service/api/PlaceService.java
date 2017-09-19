package com.ioana.google.places.service.api;

import java.util.List;

import com.ioana.google.places.dto.CityDTO;
import com.ioana.google.places.dto.PlaceDTO;

public interface PlaceService {

	PlaceDTO createPlace(PlaceDTO placeDTO);

	void savePlaces(List<PlaceDTO> places, CityDTO cityDTO);
}
