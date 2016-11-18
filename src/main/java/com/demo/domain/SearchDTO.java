package com.demo.domain;


public class SearchDTO {
    private String location;
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	private Double lat;
	private Double lng;
	private Integer radius = 30;

	public SearchDTO() {
	}

	public SearchDTO(Double lat, Double lng, Integer radius) {
		super();
		this.lat = lat;
		this.lng = lng;
		this.radius = radius;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Integer getRadius() {
		return radius;
	}

	public void setRadius(Integer radius) {
		this.radius = radius;
	}

}
