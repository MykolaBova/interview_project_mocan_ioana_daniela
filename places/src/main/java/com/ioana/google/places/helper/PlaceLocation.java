package com.ioana.google.places.helper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ioana.google.places.dto.LocationDTO;

public class PlaceLocation {
	static Logger LOG = Logger.getLogger(PlaceLocation.class);

	public static LocationDTO getLocation(String address) {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(
					"http://maps.googleapis.com/maps/api/geocode/json?address="
							+ address + "&sensor=true");
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			InputStreamReader in = new InputStreamReader(conn.getInputStream());

			StringBuilder jsonResults = new StringBuilder();
			int read;
			char[] buff = new char[1024];
			while ((read = in.read(buff)) != -1) {
				jsonResults.append(buff, 0, read);
			}

			// retrieve latitude, longitude from results
			JSONObject jsonObj = new JSONObject(jsonResults.toString());
			JSONArray predsJsonArray = jsonObj.getJSONArray("results");
			JSONObject jObj = predsJsonArray.getJSONObject(0);
			JSONObject geometry = (JSONObject) jObj.get("geometry");
			JSONObject location = (JSONObject) geometry.get("location");
			Double lat = location.getDouble("lat");
			Double lng = location.getDouble("lng");

			return new LocationDTO(lat, lng);
		} catch (MalformedURLException e) {
			LOG.error("An error occurred.", e);
		} catch (IOException e1) {
			LOG.error("An error occurred.", e1);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return null;
	}
}
