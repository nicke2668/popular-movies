package com.example.movies.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * An object includes review  response information
 *
 */
public class ReviewsResponse {

	private int page;
	@SerializedName("total_results")
	private int totalResults;
	@SerializedName("total_pages")
	private int totalPages;
	@SerializedName("results")
	private List<Review> reviewResults = new ArrayList<>();

	@SuppressWarnings({"unused", "used by Retrofit"})
	public ReviewsResponse() {
	}

	public List<Review> getReviewResults() {
		return reviewResults;
	}

}
