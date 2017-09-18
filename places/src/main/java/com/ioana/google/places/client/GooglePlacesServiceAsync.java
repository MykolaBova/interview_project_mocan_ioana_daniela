package com.ioana.google.places.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.ioana.google.places.dto.PlaceDTO;

public interface GooglePlacesServiceAsync {
    void search(String keyword, int radius, AsyncCallback<ArrayList<PlaceDTO>> async);
}
