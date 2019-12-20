package com.example.movies.model;

import com.google.gson.annotations.SerializedName;

public class Trailer {

	@SerializedName("id")
	private String id;

	@SerializedName("key")
	private String key;

	@SerializedName("name")
	private String name;

	@SerializedName("site")
	private String site;

	@SerializedName("size")
	private int size;

	@SerializedName("type")
	private String type;

	public Trailer(){

	}

	public void setVideoId(String videoId) {
		id = videoId;
	}

	public String getVideoId() {
		return id;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getSite() {
		return site;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getSize() {
		return size;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
