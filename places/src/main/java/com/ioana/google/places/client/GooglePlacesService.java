package com.ioana.google.places.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.ioana.google.places.dto.PlaceDTO;

@RemoteServiceRelativePath("googlePlaces")
public interface GooglePlacesService extends RemoteService {

	public ArrayList<PlaceDTO> search(String keyword, int radius);

	public void updatePlace(PlaceDTO place);

	public PlaceDTO createPlace(PlaceDTO place);
}
