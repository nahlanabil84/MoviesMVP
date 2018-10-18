package com.nabil.nahla.moviesmvp.ui.listMovies;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nabil.nahla.moviesmvp.R;
import com.nabil.nahla.moviesmvp.data.models.Movie;
import com.nabil.nahla.moviesmvp.data.models.ResponseMoviesList;
import com.nabil.nahla.moviesmvp.ui.listMovies.adapter.MoviesAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesActivity extends AppCompatActivity implements MoviesViewMVP {

    @BindView(R.id.movies)
    RecyclerView movies;
    @BindView(R.id.searchV)
    SearchView searchV;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    ArrayList<Movie> moviesList;
    MoviesAdapter adapter;
    int totalPages = -1, page = 1;

    MoviesPresenterMVP presenterMVP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        ButterKnife.bind(this);
        presenterMVP = new MoviesPresenter(this);

        setMoviesRV();

        if (isOnline()) {
            presenterMVP.loadMovies(page);
        } else {
            showSnackBar(getString(R.string.no_internet_connection));
        }
    }

    private void setMoviesRV() {
        moviesList = new ArrayList<>();
        adapter = new MoviesAdapter(moviesList);
        movies.setLayoutManager(new GridLayoutManager(this, 2));
        movies.setAdapter(adapter);
        movies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    if (page <= totalPages && totalPages != -1) {
                        presenterMVP.loadMovies(page);
                    }
                }
            }
        });
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar
                .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.try_again), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isOnline()) presenterMVP.loadMovies(page);
                        else showSnackBar(getString(R.string.no_internet_connection));
                    }
                });
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(int stringResourceId) {
        showSnackBar(getString(stringResourceId));
    }

    @Override
    public void listMovies(ResponseMoviesList response) {
        if (response != null) {
            totalPages = response.getTotalPages();
            increasePage();

            if (page == 1) moviesList.clear();
            moviesList.addAll(response.getMovies());
            adapter.notifyDataSetChanged();
        }
    }

    private void increasePage() {
        if (page < totalPages) page++;
    }

}
