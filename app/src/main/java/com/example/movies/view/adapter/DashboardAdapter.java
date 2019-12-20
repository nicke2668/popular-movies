package com.example.movies.view.adapter;

import java.util.Objects;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movies.R;
import com.example.movies.databinding.MovieItemBinding;
import com.example.movies.model.Movie;
import com.example.movies.view.adapter.DashboardAdapter.DashboardViewHolder;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Responsible for presenting movie_item data from PagedList in a RecyclerView.
 */
public class DashboardAdapter extends PagedListAdapter<Movie, DashboardViewHolder> {

	public interface DashboardAdapterOnClickHandler {

		void onItemClick(Movie movie);
	}

	public class DashboardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		private MovieItemBinding movieItemBinding;

		DashboardViewHolder(MovieItemBinding movieItemBinding) {
			super(movieItemBinding.getRoot());
			this.movieItemBinding = movieItemBinding;
			itemView.setOnClickListener(this);
		}

		void bind(Movie movie) {
			String thumbnail = IMAGE_BASE_URL + IMAGE_FILE_SIZE + movie.getPosterPath();
			Picasso.with(itemView.getContext())
					.load(thumbnail)
					.error(R.drawable.ic_launcher_foreground)
					.into(movieItemBinding.thumbnail);
			movieItemBinding.title.setText(movie.getTitle());
		}

		@Override
		public void onClick(View v) {
			int adapterPosition = getAdapterPosition();
			Movie movie = getItem(adapterPosition);
			onClickHandler.onItemClick(movie);
		}
	}
	public static final String BACKDROP_FILE_SIZE = "w500";

	//Compute difference between elements
	private static DiffUtil.ItemCallback<Movie> DIFF_CALLBACK =
			new DiffUtil.ItemCallback<Movie>() {
				@Override
				public boolean areContentsTheSame(Movie oldItem, @NonNull Movie newItem) {
					return oldItem.equals(newItem);
				}

				// The ID property identifies when items are the same
				@Override
				public boolean areItemsTheSame(Movie oldItem, Movie newItem) {
					return oldItem.getId() == newItem.getId();
				}
			};
	public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
	private static final String IMAGE_FILE_SIZE = "w185";

	private final DashboardAdapterOnClickHandler onClickHandler;

	public DashboardAdapter(DashboardAdapterOnClickHandler onClickHandler) {
		super(DashboardAdapter.DIFF_CALLBACK);
		this.onClickHandler = onClickHandler;
	}

	@Override
	public void onBindViewHolder(@NonNull DashboardViewHolder holder, int position) {
		holder.bind(Objects.requireNonNull(getItem(position)));
	}

	@NonNull
	@Override
	public DashboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		MovieItemBinding mMovieItemBinding = DataBindingUtil.inflate(
				layoutInflater, R.layout.movie_item, parent, false);

		return new DashboardViewHolder(mMovieItemBinding);
	}

}
