package com.example.movies.repository;

import com.example.movies.network.GetMovieDataService;
import com.example.movies.network.RetrofitClient;

import androidx.annotation.NonNull;

interface GetMovieDataConsumer {

	@NonNull
	default GetMovieDataService getService() {
		return RetrofitClient.getInstance().create(GetMovieDataService.class);
	}
}
