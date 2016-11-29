package com.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Dog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String dogPictureUrl;
	private String breed;
	private Integer favCount;
	private Integer unFavCount;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDogPictureUrl() {
		return dogPictureUrl;
	}

	public void setDogPictureUrl(String dogPictureUrl) {
		this.dogPictureUrl = dogPictureUrl;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public Integer getFavCount() {
		return favCount;
	}

	public void setFavCount(Integer favCount) {
		this.favCount = favCount;
	}

	public Integer getUnFavCount() {
		return unFavCount;
	}

	public void setUnFavCount(Integer unFavCount) {
		this.unFavCount = unFavCount;
	}

}
