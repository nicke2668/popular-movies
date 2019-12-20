package com.example.movies.view.adapter;

import java.lang.ref.WeakReference;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.movies.R;
import com.example.movies.databinding.TrailerItemBinding;
import com.example.movies.model.Trailer;
import com.example.movies.view.adapter.TrailersAdapter.TrailerViewHolder;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class TrailersAdapter extends RecyclerView.Adapter<TrailerViewHolder> {

	class TrailerViewHolder extends RecyclerView.ViewHolder {

		private static final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";
		private final TrailerItemBinding binding;

		TrailerViewHolder(@NonNull TrailerItemBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}

		private void bind(Trailer trailer, int position) {
			binding.label.setText(contextWeakReference.get().getString(R.string.trailerLabel, position + 1));
			binding.playButton.setOnClickListener(view -> onItemClick(trailer));
		}

		private void onItemClick(Trailer trailer) {
			contextWeakReference.get().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_URL + trailer.getKey())));
		}
	}

	private final WeakReference<Context> contextWeakReference;
	private final List<Trailer> trailers;

	public TrailersAdapter(@NonNull List<Trailer> trailers, @NonNull Context context) {
		this.contextWeakReference = new WeakReference<>(context);
		this.trailers = trailers;
	}

	@Override
	public int getItemCount() {
		return trailers.size();
	}

	@Override
	public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
		holder.bind(trailers.get(position), position);
	}

	@NonNull
	@Override
	public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		TrailerItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
				R.layout.trailer_item, parent, false);
		return new TrailerViewHolder(binding);
	}
}
