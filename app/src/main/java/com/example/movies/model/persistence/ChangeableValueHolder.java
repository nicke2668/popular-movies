package com.example.movies.model.persistence;

interface ChangeableValueHolder<V> {

	V getValue();

	void setValue(V value);

}
