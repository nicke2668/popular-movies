package com.example.movies.network;

import com.example.movies.model.ReviewsResponse;
import com.example.movies.model.MoviesResponse;
import com.example.movies.model.TrailersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetMovieDataService {

	@GET("movie/{sort_criteria}")
	Call<MoviesResponse> getMovies(
			@Path("sort_criteria") String sortCriteria,
			@Query("api_key") String apiKey,
			@Query("language") String language,
			@Query("page") int page
	);

	@GET("movie/{id}/reviews")
	Call<ReviewsResponse> getReviews(
			@Path("id") int id,
			@Query("api_key" ) String apiKey,
			@Query("language") String language,
			@Query("page") int page
	);

	@GET("movie/{id}/videos")
	Call<TrailersResponse> getTrailers(
			@Path("id") int id,
			@Query("api_key") String apiKey,
			@Query("language") String language
	);
}