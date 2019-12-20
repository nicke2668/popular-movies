package com.example.movies.model.persistence;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public enum MovieDatabaseValueHolder implements ChangeableValueHolder<MovieDatabase> {
	INSTANCE {

		private transient MovieDatabase database;

		@Override
		@Nullable
		public MovieDatabase getValue() {
			return database;
		}

		@Override
		public void setValue(@NonNull MovieDatabase value) {
			this.database = value;
		}

	}

}

