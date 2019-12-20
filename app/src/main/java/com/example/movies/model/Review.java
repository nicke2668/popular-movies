package com.example.movies.model;

import com.google.gson.annotations.SerializedName;

/**
 * An object that includes movie review information
 *
 */
public class Review {

	@SerializedName("author")
	private String author;
	@SerializedName("content")
	private String content;
	@SerializedName("url")
	private String url;

	private Review() {
	}

	public String getAuthor() {
		return author;
	}

	public String getContent() {

		return content;
	}

	public String getUrl() {
		return url;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
