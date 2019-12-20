package com.example.movies.model;

import java.util.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Serializable and persistable model object for a movie
 */
@Entity(tableName = "movie")
public class Movie implements Parcelable {

	@Override
	public boolean equals(@Nullable Object obj) {
		if (obj == null || getClass() != obj.getClass()) return false;
		return Objects.equals(((Movie) obj).getId(), id);
	}

	public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

		public Movie createFromParcel(Parcel in) {
			return new Movie(in);
		}


		public Movie[] newArray(int size) {
			return new Movie[size];
		}
	};

	@ColumnInfo(name = "backdrop_path")
	@SerializedName("backdrop_path")
	private String backdropPath;
	@PrimaryKey
	@SerializedName("id")
	private int id;
	@SerializedName("original_title")
	@ColumnInfo(name = "original_title")
	private String originalTitle;
	@SerializedName("overview")
	private String overview;
	@SerializedName("poster_path")
	@ColumnInfo(name = "poster_path")
	private String posterPath;
	@ColumnInfo(name = "release_date")
	@SerializedName("release_date")
	private String releaseDate;
	@SerializedName("title")
	private String title;
	@ColumnInfo(name = "vote_average")
	@SerializedName("vote_average")
	private double voteAverage;

	public Movie(int id, String originalTitle, String title, String posterPath, String overview,
			double voteAverage, String releaseDate, String backdropPath) {
		this.id = id;
		this.originalTitle = originalTitle;
		this.title = title;
		this.posterPath = posterPath;
		this.overview = overview;
		this.voteAverage = voteAverage;
		this.releaseDate = releaseDate;
		this.backdropPath = backdropPath;
	}

	public int getId() {
		return id;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	@Override
	public int hashCode() {
		return Objects.hash(title, id);
	}

	public String getTitle() {
		return title;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public String getOverview() {
		return overview;
	}

	public double getVoteAverage() {
		return voteAverage;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public String getBackdropPath() {
		return backdropPath;
	}

	private Movie(Parcel in) {
		id = in.readInt();
		originalTitle = in.readString();
		title = in.readString();
		posterPath = in.readString();
		overview = in.readString();
		voteAverage = in.readDouble();
		releaseDate = in.readString();
		backdropPath = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(originalTitle);
		dest.writeString(title);
		dest.writeString(posterPath);
		dest.writeString(overview);
		dest.writeDouble(voteAverage);
		dest.writeString(releaseDate);
		dest.writeString(backdropPath);
	}
}
