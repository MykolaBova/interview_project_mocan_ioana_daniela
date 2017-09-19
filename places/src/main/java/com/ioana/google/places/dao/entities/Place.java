package com.ioana.google.places.dao.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PLACE", schema = "public")
public class Place {

	@Id
	@SequenceGenerator(name = "place_seq_gen", sequenceName = "place_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "place_seq_gen")
	private int id;

	private String name;

	private String reference;

	@ManyToOne
	@JoinColumn(name = "CITY_ID")
	private City city;

	@ManyToMany
	@JoinTable(name = "PLACE_PLACETYPE", joinColumns = { @JoinColumn(name = "placeId") }, inverseJoinColumns = { @JoinColumn(name = "placeTypeId") })
	private List<PlaceType> types;

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

	public List<PlaceType> getTypes() {
		return types;
	}

	public void setTypes(List<PlaceType> types) {
		this.types = types;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

}
