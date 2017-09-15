package com.ioana.google.places.service.api;

import java.util.ArrayList;

import com.ioana.google.places.dto.CityDTO;
import com.ioana.google.places.dto.PlaceDTO;

public interface GoogleAPIService {

	ArrayList<PlaceDTO> getPlacesForCity(CityDTO cityDTO, int radius);
}
