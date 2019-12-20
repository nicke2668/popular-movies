package com.example.movies.repository;

import java.util.Objects;

import com.example.movies.BuildConfig;
import com.example.movies.model.Movie;
import com.example.movies.model.MoviesResponse;
import com.example.movies.model.SortOrder;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Loads snapshots of downloaded movie_item data into a PagedList.
 *
 * Reference: @see "https://proandroiddev.com/8-steps-to-implement-paging-library-in-android-d02500f7fffe"
 * "https://proandroiddev.com/exploring-paging-library-from-jetpack-c661c7399662"
 */
public class RemoteMovieDataSource extends PageKeyedDataSource<Integer, Movie> implements GetMovieDataConsumer {

	private static final int NEXT_PAGE_KEY_TWO = 2;
	private static final int PREVIOUS_PAGE_KEY_ONE = 1;
	private final String sortOrder;

	public RemoteMovieDataSource(@SortOrder String sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
		final int currentPage = params.key;
		Call<MoviesResponse> call = getService().getMovies(sortOrder, BuildConfig.POPULAR_MOVIES_API_KEY, "en-US", 1);
		call.enqueue(new Callback<MoviesResponse>() {
			@Override
			public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable throwable) {
				Timber.e("Failed initializing a PageList: %s", throwable.getMessage());
			}

			@Override
			public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
				if (!response.isSuccessful()) {
					Timber.e("Unsuccessful response. Response code: %s", response.code());
					return;
				}
				int nextKey = currentPage + 1;
				callback.onResult(Objects.requireNonNull(response.body()).getMovieResults(), nextKey);
			}

		});

	}

	@Override
	public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
		//do nothing
	}

	@Override
	public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Movie> callback) {
		Call<MoviesResponse> call = getService().getMovies(sortOrder, BuildConfig.POPULAR_MOVIES_API_KEY, "en-US", 1);
		call.enqueue(new Callback<MoviesResponse>() {
			@Override
			public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable throwable) {
				Timber.e("Failed initializing a PageList: %s", throwable.getMessage());
			}

			@Override
			public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
				if (!response.isSuccessful()) {
					Timber.e("Unsuccessful response. Response code: %s", response.code());
					return;
				}
				callback.onResult(Objects.requireNonNull(response.body()).getMovieResults(), PREVIOUS_PAGE_KEY_ONE, NEXT_PAGE_KEY_TWO);
			}

		});
	}

}
