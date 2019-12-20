package com.example.movies.repository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.movies.model.LocalMovieDataSourceFactory;
import com.example.movies.model.Movie;
import com.example.movies.model.RemoteMovieDataSourceFactory;
import com.example.movies.model.Review;
import com.example.movies.model.ReviewsResponse;
import com.example.movies.model.SortOrder;
import com.example.movies.model.Trailer;
import com.example.movies.model.TrailersResponse;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.paging.PagedList.Config;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.example.movies.BuildConfig.POPULAR_MOVIES_API_KEY;
import static com.example.movies.model.SortOrder.FAVORITES;

public class MovieRepository implements MovieDatabaseClient, GetMovieDataConsumer {

	private static final int INITIAL_LOAD_SIZE_HINT = 10;
	public static final MovieRepository INSTANCE = new MovieRepository();
	private static final String LANGUAGE = "en-US";
	private static final Void NOTHING = null;
	private static final int PAGE_SIZE = 20;
	private static final int PREFETCH_DISTANCE = 50;
	private LiveData<PagedList<Movie>> movies = new MutableLiveData<>();
	private LiveData<PagedList<Movie>> favorites = new MutableLiveData<>();
	private final MutableLiveData<List<Review>> reviews = new MutableLiveData<>();
	private final MutableLiveData<List<Trailer>> trailers = new MutableLiveData<>();


	private MovieRepository() {

	}

	@NonNull
	private Callable<Void> createDeleteAsyncCallable(Movie movie) {
		return () -> {
			getMovieDao().deleteMovie(movie);
			return NOTHING;
		};
	}

	@NonNull
	private Callable<Void> createInsertAsyncCallable(Movie movie) {
		return () -> {
			getMovieDao().insertMovie(movie);
			return NOTHING;
		};
	}

	private LiveData<PagedList<Movie>> createLivePagedListFromLocal(List<Movie> localMoviesList) {
		MutableLiveData<PagedList<Movie>> nullLiveData = new MutableLiveData<>();
		nullLiveData.setValue(null); //publish null if no local movie entries to show empty text view
		return localMoviesList.isEmpty() ? nullLiveData : new LivePagedListBuilder<>(new LocalMovieDataSourceFactory(localMoviesList), createPagedListConfig())
				.build();
	}

	@NonNull
	private Callable<Void> createReviewsAsyncCallable(int id) {
		return () -> {
			getService().getReviews(id, POPULAR_MOVIES_API_KEY, LANGUAGE, 1)
					.enqueue(new Callback<ReviewsResponse>() {
						@Override
						public void onFailure(@NonNull Call<ReviewsResponse> call, @NonNull Throwable t) {
							reviews.setValue(null);
						}

						@Override
						public void onResponse(@NonNull Call<ReviewsResponse> call, @NonNull Response<ReviewsResponse> response) {
							if (response.isSuccessful() && response.body() != null) {
								ReviewsResponse reviewsResponse = response.body();
								reviews.setValue(reviewsResponse.getReviewResults());
							}
						}
					});
			return NOTHING;
		};
	}

	@NonNull
	private Callable<Void> createTrailersAsyncCallable(int id) {
		return () -> {
			getService().getTrailers(id, POPULAR_MOVIES_API_KEY, LANGUAGE)
					.enqueue(new Callback<TrailersResponse>() {
						@Override
						public void onFailure(@NonNull Call<TrailersResponse> call, @NonNull Throwable t) {
							trailers.setValue(null);
						}

						@Override
						public void onResponse(@NonNull Call<TrailersResponse> call, @NonNull Response<TrailersResponse> response) {
							if (response.isSuccessful() && response.body() != null) {
								TrailersResponse trailersResponse = response.body();
								trailers.setValue(trailersResponse.getTrailerResults());
							}
						}
					});
			return NOTHING;
		};
	}

	@NonNull
	private Config createPagedListConfig() {
		return (new Config.Builder())
				.setEnablePlaceholders(false)
				.setInitialLoadSizeHint(INITIAL_LOAD_SIZE_HINT)
				.setPageSize(PAGE_SIZE)
				.setPrefetchDistance(PREFETCH_DISTANCE)
				.build();
	}

	private void executeAsync(Collection<Callable<Void>> callables, ExecutorService executor) {
		try {
			executor.invokeAll(callables);
		} catch (InterruptedException exception) {
			Timber.e(exception);
			Thread.currentThread().interrupt();
		}
	}

	public LiveData<PagedList<Movie>> getFavorites() {
		return favorites;
	}

	public LiveData<PagedList<Movie>> getMovies() {
		return movies;
	}

	public LiveData<List<Review>> getReviews() {
		return reviews;
	}

	public LiveData<List<Trailer>> getTrailers() {
		return trailers;
	}

	public void delete(Movie movie) {
		executeAsync(Collections.singleton(createDeleteAsyncCallable(movie)), Executors.newSingleThreadExecutor());
	}

	public void initializeDashboardView(@SortOrder String sortOrder) {
		movies = new LivePagedListBuilder<>(new RemoteMovieDataSourceFactory(sortOrder), createPagedListConfig())
				.setFetchExecutor(Executors.newFixedThreadPool(5))
				.build();
	}

	public void initializeFavorites() {
		favorites = Transformations.switchMap(getMovieDao().loadAllMovies(), this::createLivePagedListFromLocal);
	}

	public void insert(Movie movie) {
		executeAsync(Collections.singleton(createInsertAsyncCallable(movie)), Executors.newSingleThreadExecutor());
	}

	@NonNull
	public LiveData<Boolean> isFavorite(Movie movie) {
		return Transformations.map(getMovieDao().loadMovieByMovieId(movie.getId()), Objects::nonNull);
	}

	public void initializeMovieExtrasContent(int id) {
		executeAsync(Arrays.asList(createTrailersAsyncCallable(id), createReviewsAsyncCallable(id)), Executors.newFixedThreadPool(3));
	}

	public void updateDashboard(@SortOrder String sortOrder) {
		if (FAVORITES.equals(sortOrder)) {
			initializeFavorites();
			movies = favorites;
			return;
		}
		initializeDashboardView(sortOrder);
	}
}

