package com.ioana.google.places.service.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Singleton;
import com.ioana.google.places.dto.CityDTO;
import com.ioana.google.places.dto.PlaceDTO;
import com.ioana.google.places.service.api.GoogleAPIService;

@Singleton
public class GoogleAPIServiceImpl implements GoogleAPIService {

	Logger LOG = Logger.getLogger(GoogleAPIServiceImpl.class);

	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";

	private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
	private static final String TYPE_DETAILS = "/details";
	private static final String TYPE_SEARCH = "/nearbysearch";

	private static final String OUT_JSON = "/json";

	// KEY!
	private static final String API_KEY = "AIzaSyBBE_9Dg0NlO3XqGcmoNc_mm9gUtJh22Xk";

	@Override
	public ArrayList<PlaceDTO> getPlacesForCity(CityDTO cityDTO, int radius) {
		ArrayList<PlaceDTO> resultList = null;

		HttpURLConnection conn = null;
		StringBuilder jsonResults = new StringBuilder();
		try {
			StringBuilder sb = new StringBuilder(PLACES_API_BASE);
			sb.append(TYPE_SEARCH);
			sb.append(OUT_JSON);
			sb.append("?sensor=false");
			sb.append("&key=" + API_KEY);
			sb.append("&location=" + String.valueOf(cityDTO.getLatitude())
					+ "," + String.valueOf(cityDTO.getLongitude()));
			sb.append("&radius=" + String.valueOf(radius));

			URL url = new URL(sb.toString());
			conn = (HttpURLConnection) url.openConnection();
			InputStreamReader in = new InputStreamReader(conn.getInputStream());

			int read;
			char[] buff = new char[1024];
			while ((read = in.read(buff)) != -1) {
				jsonResults.append(buff, 0, read);
			}
			System.out.println(jsonResults);
		} catch (MalformedURLException e) {
			LOG.error("Error processing Places API URL", e);
			return resultList;
		} catch (IOException e) {
			LOG.error("Error connecting to Places API", e);
			return resultList;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		try {
			// Create a JSON object hierarchy from the results
			ObjectMapper mapper = new ObjectMapper();
			JSONObject jsonObj = new JSONObject(jsonResults.toString());
			JSONArray predsJsonArray = jsonObj.getJSONArray("results");

			// Extract the Place descriptions from the results
			resultList = new ArrayList<PlaceDTO>(predsJsonArray.length());
			for (int i = 0; i < predsJsonArray.length(); i++) {
				PlaceDTO place = mapper.readValue(
						predsJsonArray.getJSONObject(i).toString(),
						PlaceDTO.class);
				resultList.add(place);
			}
		} catch (JSONException | IOException e) {
			LOG.error("Error processing JSON results", e);
		}

		return resultList;
	}

}
