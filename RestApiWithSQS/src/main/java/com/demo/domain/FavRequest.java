package com.demo.domain;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FavRequest {
	private long dogId;
	private boolean favOrUnfav;
	
	

	public long getDogId() {
		return dogId;
	}

	public void setDogId(long dogId) {
		this.dogId = dogId;
	}

	public FavRequest() {
		super();
	}

	public FavRequest(long dogId, boolean favOrUnfav) {
		super();
		this.dogId = dogId;
		this.favOrUnfav = favOrUnfav;
	}

	public boolean isFavOrUnfav() {
		return favOrUnfav;
	}

	public void setFavOrUnfav(boolean favOrUnfav) {
		this.favOrUnfav = favOrUnfav;
	}

	public static FavRequest fromJSON(String json)
			throws JsonProcessingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, FavRequest.class);
	}
	
	public static String toJSON(FavRequest favRequest)
			throws JsonProcessingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString( favRequest);
	}
}
