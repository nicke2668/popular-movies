package com.example.movies.model;

import java.util.List;

import com.example.movies.repository.LocalMovieDataSource;

import androidx.paging.DataSource;

/**
 * Factory responsible for creating a LocalMovieDataSource.
 */
public class LocalMovieDataSourceFactory extends DataSource.Factory<Integer, Movie> {

	private final List<Movie> movies;

	public LocalMovieDataSourceFactory(List<Movie> movies) {
		this.movies = movies;
	}

	@Override
	public DataSource<Integer, Movie> create() {
		return new LocalMovieDataSource(movies);
	}

}
