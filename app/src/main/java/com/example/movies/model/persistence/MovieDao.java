package com.example.movies.model.persistence;

import java.util.List;

import com.example.movies.model.Movie;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/**
 * API for interacting with room database
 *
 * Reference: https://developer.android.com/training/data-storage/room/accessing-data
 */

@Dao
public interface MovieDao {

	@Delete
	void deleteMovie(Movie movieEntry);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insertMovie(Movie movieEntry);

	@Query("SELECT * FROM movie")
	LiveData<List<Movie>> loadAllMovies();

	@Query("SELECT * FROM movie WHERE id = :id")
	LiveData<Movie> loadMovieByMovieId(int id);

}
