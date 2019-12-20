package com.example.movies.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class TrailersResponse {

	@SerializedName("id")
	private String id;

	public String getId() {
		return id;
	}

	@SerializedName("results")
	private List<Trailer> trailerResults = new ArrayList<>();

	@SuppressWarnings({"unused", "used by Retrofit"})
	public TrailersResponse() {
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Trailer> getTrailerResults() {
		return trailerResults;
	}

	public void setTrailerResults(List<Trailer> trailerResults) {
		this.trailerResults = trailerResults;
	}


}
