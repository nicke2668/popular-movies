package com.example.movies.view;

import com.example.movies.databinding.DashboardFragmentBinding;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider.Factory;

class DashboardViewModelFactory implements Factory {

	private final DashboardFragmentBinding binding;

	public DashboardViewModelFactory(DashboardFragmentBinding binding) {
		this.binding = binding;
	}

	@NonNull
	@Override
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		return null;
	}
}
