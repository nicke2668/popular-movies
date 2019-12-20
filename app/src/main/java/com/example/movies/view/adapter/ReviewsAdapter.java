package com.example.movies.view.adapter;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.movies.R;
import com.example.movies.databinding.ReviewItemBinding;
import com.example.movies.model.Review;
import com.example.movies.view.adapter.ReviewsAdapter.ReviewViewHolder;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

	class ReviewViewHolder extends RecyclerView.ViewHolder {

		ReviewItemBinding binding;

		ReviewViewHolder(@NonNull ReviewItemBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}

		private void bind(Review review) {
			binding.author.setText(review.getAuthor());
			binding.content.setText(review.getContent());
			binding.button.setOnClickListener(view -> onButtonClick(review));
		}

		private void onButtonClick(Review review) {
			contextWeakReference.get().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(review.getUrl())));
		}
	}

	private final List<Review> reviews;
	private final WeakReference<Context> contextWeakReference;

	public ReviewsAdapter(List<Review> reviews, Context context) {
		this.reviews = reviews;
		contextWeakReference = new WeakReference<>(context);
	}

	@Override
	public int getItemCount() {
		return reviews.size();
	}

	@Override
	public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
		holder.bind(Objects.requireNonNull(reviews.get(position)));
	}

	@NonNull
	@Override
	public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		ReviewItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
				R.layout.review_item, parent, false);
		return new ReviewViewHolder(binding);
	}
}
