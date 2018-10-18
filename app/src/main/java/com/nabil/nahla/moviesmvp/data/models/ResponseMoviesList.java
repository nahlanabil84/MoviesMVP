package com.nabil.nahla.moviesmvp.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseMoviesList {

	@SerializedName("page")
	private int page;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("results")
	private ArrayList<Movie> movies;

	@SerializedName("total_results")
	private int totalResults;

	public int getPage(){
		return page;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public ArrayList<Movie> getMovies(){
		return movies;
	}

	public int getTotalResults(){
		return totalResults;
	}
}