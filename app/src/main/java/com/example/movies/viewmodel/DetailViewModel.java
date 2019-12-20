package com.example.movies.viewmodel;

import java.util.List;

import com.example.movies.model.Movie;
import com.example.movies.model.Review;
import com.example.movies.model.Trailer;
import com.example.movies.repository.MovieRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class DetailViewModel extends ViewModel {

	public final Movie movie;
	private MovieRepository repository = MovieRepository.INSTANCE;

	DetailViewModel(Movie movie) {
		this.movie = movie;
		initializeMovieExtrasContent();
	}

	public void delete() {
		repository.delete(movie);
	}

	public LiveData<List<Review>> getReviews() {
		return repository.getReviews();
	}

	public LiveData<List<Trailer>> getTrailers() {
		return repository.getTrailers();
	}

	public LiveData<Boolean> isFavorite() {

		return repository.isFavorite(movie);
	}



	public void insert() {
		repository.insert(movie);
	}

	private void initializeMovieExtrasContent() {
		repository.initializeMovieExtrasContent(movie.getId());
	}
}