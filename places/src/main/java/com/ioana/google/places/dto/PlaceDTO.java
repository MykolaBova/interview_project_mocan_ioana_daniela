package com.ioana.google.places.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4247802774547964924L;

	private String iconLink;

	private String name;

	private String scope;

	private List<String> types;

	private String vicinity;

	private String reference;

	public String getIconLink() {
		return iconLink;
	}

	public void setIconLink(String iconLink) {
		this.iconLink = iconLink;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public String getVicinity() {
		return vicinity;
	}

	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

}
