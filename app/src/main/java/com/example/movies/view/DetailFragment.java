package com.example.movies.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.movies.R;
import com.example.movies.databinding.DetailFragmentBinding;
import com.example.movies.model.Movie;
import com.example.movies.view.adapter.ReviewsAdapter;
import com.example.movies.view.adapter.TrailersAdapter;
import com.example.movies.viewmodel.DetailViewModel;
import com.example.movies.viewmodel.DetailViewModelFactory;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.movies.view.adapter.DashboardAdapter.BACKDROP_FILE_SIZE;
import static com.example.movies.view.adapter.DashboardAdapter.IMAGE_BASE_URL;

public class DetailFragment extends MovieFragment implements FloatingActionButtonCallback {

	private DetailFragmentBinding binding;
	private DetailViewModel viewModel;
	private boolean favorite;

	private String getBackdropPath(Movie movie) {
		return IMAGE_BASE_URL + BACKDROP_FILE_SIZE + movie.getBackdropPath();
	}

	private Movie getMovieDetailsFromBundle() {
		return DetailFragmentArgs.fromBundle(requireArguments()).getMovie();
	}

	private void initializeViews() {
//		((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//		((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
		((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
		Context context = requireContext();
		Movie movie = viewModel.movie;
		binding.setCallback(this);
		binding.title.setText(movie.getTitle());
		binding.releaseDate.setText(getString(R.string.releaseDate, movie.getReleaseDate()));
		binding.voteAverage.setText(getString(R.string.voteAverage, String.valueOf(movie.getVoteAverage())));
		binding.plotSynopsis.setText(movie.getOverview());
		binding.reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
		binding.reviewsRecyclerView.setHasFixedSize(true);
		binding.trailersRecyclerView.setHasFixedSize(true);
		binding.trailersRecyclerView.setLayoutManager(new LinearLayoutManager(context));
		Picasso.with(context).load(getBackdropPath(movie)).error(R.drawable.ic_launcher_foreground).into(binding.backdrop);
	}

	private void observeFavoriteStatus() {
		viewModel.isFavorite().observe(this, this::onFavoriteStatusChanged);
	}

	private void observeReviewResults() {
		viewModel.getReviews().observe(this, reviews ->
				binding.reviewsRecyclerView.setAdapter(new ReviewsAdapter(reviews, requireContext())));
	}

	private void observeTrailerResults() {
		viewModel.getTrailers().observe(this, trailers ->
				binding.trailersRecyclerView.setAdapter(new TrailersAdapter(trailers, requireContext())));
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupViewModel();
		setHasOptionsMenu(true);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		binding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false);
		initializeViews();
		observeTrailerResults();
		observeReviewResults();
		observeFavoriteStatus();
		return binding.getRoot();
	}

	private void onFavoriteStatusChanged(Boolean favorite) {
		if (favorite) binding.fab.setImageResource(R.drawable.ic_star_yellow);
		this.favorite = favorite;
	}

	@Override
	public void onFloatingActionButtonClicked() {
		if (favorite) {
			viewModel.delete();
			binding.fab.setImageResource(R.drawable.ic_star);
			Toast.makeText(requireContext(), "Removed from favorites.", Toast.LENGTH_SHORT).show();
			return;
		}
		viewModel.insert();
		binding.fab.setImageResource(R.drawable.ic_star_yellow);
		Toast.makeText(requireContext(), "Added to favorites.", Toast.LENGTH_SHORT).show();
	}

	private void setupViewModel() {
		viewModel = ViewModelProviders.of(this, new DetailViewModelFactory(getMovieDetailsFromBundle())).get(DetailViewModel.class);
	}
}
   	
