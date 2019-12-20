package com.example.movies.viewmodel;

import com.example.movies.model.Movie;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

	private final Movie movie;

	public DetailViewModelFactory(Movie movie) {
		this.movie = movie;
	}

	@NonNull
	@Override
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		return (T) new DetailViewModel(movie);
	}
}
