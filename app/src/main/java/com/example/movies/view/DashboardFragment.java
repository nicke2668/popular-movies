package com.example.movies.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.movies.R;
import com.example.movies.databinding.DashboardFragmentBinding;
import com.example.movies.model.Movie;
import com.example.movies.model.SortOrder;
import com.example.movies.view.adapter.DashboardAdapter;
import com.example.movies.viewmodel.DashboardViewModel;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import static com.example.movies.model.SortOrder.FAVORITES;
import static com.example.movies.model.SortOrder.POPULAR;
import static com.example.movies.model.SortOrder.TOP_RATED;

public class DashboardFragment extends MovieFragment {

	private final DashboardAdapter adapter = new DashboardAdapter(this::onItemClick);
	private DashboardFragmentBinding binding;
	private DashboardViewModel viewModel;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		setupViewModel();
	}

	@Override
	public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
		inflater.inflate(R.menu.menu_main, menu);
	}

	private void observeMoviePagedList() {
		viewModel.getMoviePagedList().observe(this, pagedList -> {
			binding.emptyTextView.setVisibility(pagedList == null ? View.VISIBLE : View.INVISIBLE);
			if (!viewModel.currentSortOrder.equals(FAVORITES)) {
				viewModel.getMoviePagedList().removeObservers(this);
			}
			adapter.submitList(pagedList);
		});
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		binding = DataBindingUtil.inflate(inflater, R.layout.dashboard_fragment, container, false);
		binding.recyclerView.setHasFixedSize(true);
		binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
		binding.recyclerView.setAdapter(adapter);
		return binding.getRoot();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.mostPopular:
				updateSorting(POPULAR);
				viewModel.currentSortOrder = POPULAR;
				viewModel.getFavorites().removeObservers(this);
				return true;
			case R.id.highestRated:
				updateSorting(TOP_RATED);
				viewModel.currentSortOrder = TOP_RATED;
				viewModel.getFavorites().removeObservers(this);
				return true;
			case R.id.viewFavorites:
				updateSorting(FAVORITES);
				viewModel.currentSortOrder = FAVORITES;
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void onItemClick(Movie movie) {
		NavHostFragment.findNavController(this).navigate(DashboardFragmentDirections.dashboardFragmentToDetailFragment(movie));
	}

	private void setupViewModel() {
		viewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
	}

	@Override
	public void onResume() {
		super.onResume();
		((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
		((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.popularMovies);
		if (!isNetworkConnected()) {
			showNetworkConnectionSnackbar();
			return;
		}
		viewModel.updateDashboard(viewModel.currentSortOrder);
		observeMoviePagedList();
	}

	private void showNetworkConnectionSnackbar() {
		Snackbar.make(binding.recyclerView, R.string.offlineMessage, Snackbar.LENGTH_SHORT).show();
	}

	private void updateSorting(@SortOrder String sortOrder) {
		if (!isNetworkConnected()) {
			showNetworkConnectionSnackbar();
			return;
		}
		viewModel.updateDashboard(sortOrder);
		observeMoviePagedList();
	}

}

