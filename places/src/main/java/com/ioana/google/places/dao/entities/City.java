package com.ioana.google.places.dao.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CITY", schema = "public")
@NamedQueries({ @NamedQuery(name = "City.findByName", query = "SELECT c FROM City c where c.name = :name"), })
public class City {

	@Id
	@SequenceGenerator(name = "city_seq_gen", sequenceName = "city_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_seq_gen")
	private int id;

	private String name;

	private double latitude;

	private double longitude;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Place> places;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}
