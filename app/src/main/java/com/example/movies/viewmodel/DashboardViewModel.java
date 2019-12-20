package com.example.movies.viewmodel;

import android.app.Application;

import com.example.movies.model.Movie;
import com.example.movies.model.SortOrder;
import com.example.movies.repository.MovieRepository;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

public class DashboardViewModel extends AndroidViewModel {

	public String currentSortOrder = SortOrder.POPULAR;
	private MovieRepository repository = MovieRepository.INSTANCE;

	public DashboardViewModel(Application application) {
		super(application);
		repository.initializeDashboardView(currentSortOrder);
		repository.initializeFavorites();
	}

	public LiveData<PagedList<Movie>> getFavorites() {
		return repository.getFavorites();
	}

	public LiveData<PagedList<Movie>> getMoviePagedList() {
		return repository.getMovies();
	}

	public void updateDashboard(@SortOrder String sortOrder) {
		if (sortOrder.equals(currentSortOrder)) return;
		currentSortOrder = sortOrder;
		repository.updateDashboard(sortOrder);
	}

}