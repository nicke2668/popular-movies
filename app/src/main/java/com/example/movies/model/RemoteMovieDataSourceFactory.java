package com.example.movies.model;

import com.example.movies.repository.RemoteMovieDataSource;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

/**
 * Factory responsible for creating a RemoteMovieDataSource
 */
public class RemoteMovieDataSourceFactory extends DataSource.Factory<Integer, Movie> {

	private final String sortOrder;

	public RemoteMovieDataSourceFactory(@SortOrder String sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public DataSource<Integer, Movie> create() {
		return determineDataSource();
	}

	@NonNull
	private DataSource<Integer, Movie> determineDataSource() {
		return new RemoteMovieDataSource(sortOrder);
	}

}
