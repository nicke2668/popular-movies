package com.example.movies;

import android.app.Application;

import com.example.movies.model.persistence.MovieDatabase;
import com.example.movies.model.persistence.MovieDatabaseValueHolder;

import androidx.room.Room;
import timber.log.Timber;

public class MovieApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		if (BuildConfig.DEBUG) {
			Timber.plant(new Timber.DebugTree());
		}
		MovieDatabaseValueHolder.INSTANCE.setValue(Room.databaseBuilder(this, MovieDatabase.class, "MovieDatabase").build());
	}
}
