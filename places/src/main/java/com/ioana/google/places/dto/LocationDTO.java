package com.ioana.google.places.dto;

public class LocationDTO {
	
	private Double latitude;
	
	private Double longitude;

	public LocationDTO(Double latitude, Double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}
	
	

}
