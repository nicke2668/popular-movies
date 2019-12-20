package com.example.movies.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.annotation.StringDef;

@StringDef(value = {SortOrder.POPULAR, SortOrder.FAVORITES, SortOrder.TOP_RATED})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.SOURCE)
public @interface SortOrder {

	String POPULAR = "popular";
	String FAVORITES = "favorites";
	String TOP_RATED = "top_rated";
}
