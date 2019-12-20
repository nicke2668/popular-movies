package com.example.movies.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class MoviesResponse {

	private int page;
	@SerializedName("total_results")
	private int totalResults;
	@SerializedName("total_pages")
	private int totalPages;
	@SerializedName("results")
	private List<Movie> movieResults = new ArrayList<>();

	@SuppressWarnings({"unused", "required by Retrofit"})
	public MoviesResponse() {
	}

	public List<Movie> getMovieResults() {
		return movieResults;
	}

}
