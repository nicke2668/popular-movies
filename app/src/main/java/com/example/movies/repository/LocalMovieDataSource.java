package com.example.movies.repository;

import java.util.List;

import com.example.movies.model.Movie;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

/**
 * Class for loading snapshots of local movie_item data into a PagedList.
 *
 * Reference: "https://stackoverflow.com/questions/50030049/how-to-convert-a-listobject-to-pagedlistobject-and-vice-versa"
 */
public class LocalMovieDataSource extends PageKeyedDataSource<Integer, Movie> implements MovieDatabaseClient {

	private final List<Movie> movies;

	private List<Movie> getSubList(int page, int pageSize) {
		int initialIndex = Math.min(0, page * pageSize);
		int finalIndex = Math.min(movies.size(), initialIndex + pageSize);
		return movies.subList(initialIndex, finalIndex);
	}

	public LocalMovieDataSource(@NonNull List<Movie> movies) {
		this.movies = movies;
	}

	@Override
	public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
		//do nothing
	}

	@Override
	public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
		//do nothing
	}

	@Override
	public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Movie> callback) {
		callback.onResult(getSubList(0, params.requestedLoadSize), 1, 2);
	}
}
