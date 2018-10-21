package com.nabil.nahla.moviesmvp.ui.listMovies;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
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

public class MoviesActivity extends AppCompatActivity implements MoviesViewMVP
        , SearchView.OnCloseListener, SearchView.OnQueryTextListener {

    @BindView(R.id.movies)
    RecyclerView movies;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    ArrayList<Movie> moviesList;
    MoviesAdapter adapter;
    int totalPages = -1, page = 1;

    String query;

    MoviesPresenterMVP presenterMVP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        ButterKnife.bind(this);
        presenterMVP = new MoviesPresenter(this);
        query = "";

        setMoviesRV();

        if (isOnline()) {
            presenterMVP.loadMovies(page, query);
        } else {
            showSnackBar(getString(R.string.no_internet_connection));
        }
    }

    private void setMoviesRV() {
        moviesList = new ArrayList<>();
        adapter = new MoviesAdapter(moviesList);
        movies.setLayoutManager(new LinearLayoutManager(this));
        movies.setAdapter(adapter);
        movies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    if (page <= totalPages && totalPages != -1) {
                        presenterMVP.loadMovies(page, query);
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
                        if (isOnline()) presenterMVP.loadMovies(page, query);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);

        return true;
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
    public void showMessage(String errorMsg) {
        showSnackBar(errorMsg);
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

    @Override
    public boolean onClose() {
        query = "";
        page = 1;
        presenterMVP.loadMovies(page, query);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        if (query != s) {
            query = s;
            page = 1;
            presenterMVP.loadMovies(page, query);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (query != s) {
            query = s;
            page = 1;
            presenterMVP.loadMovies(page, query);
        }
        return false;
    }
}
