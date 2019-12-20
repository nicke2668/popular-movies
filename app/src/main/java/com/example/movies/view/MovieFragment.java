package com.example.movies.view;

import java.util.Objects;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.fragment.app.Fragment;

abstract class MovieFragment extends Fragment {

	boolean isNetworkConnected() {
		ConnectivityManager connectivityManager = (ConnectivityManager)
				requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isConnected();
	}
}
