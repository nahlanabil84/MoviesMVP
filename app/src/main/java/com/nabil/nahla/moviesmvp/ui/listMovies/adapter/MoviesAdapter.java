package com.nabil.nahla.moviesmvp.ui.listMovies.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nabil.nahla.moviesmvp.R;
import com.nabil.nahla.moviesmvp.data.models.Movie;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MovieVH> {
    ArrayList<Movie> movies;

    public MoviesAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieVH holder, int position) {
        holder.clear();
        holder.onBind(movies);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
