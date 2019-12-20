package com.example.movies.repository;

import com.example.movies.model.persistence.MovieDao;
import com.example.movies.model.persistence.MovieDatabaseValueHolder;

interface MovieDatabaseClient {

	default MovieDao getMovieDao() {
		return MovieDatabaseValueHolder.INSTANCE.getValue().getMovieDao();
	}
}
