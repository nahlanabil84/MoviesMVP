package com.nabil.nahla.moviesmvp.ui.listMovies;

import com.nabil.nahla.moviesmvp.data.models.ResponseMoviesList;

public interface MoviesViewMVP {

    void showLoading();

    void hideLoading();

    void showMessage(int stringResourceId);

    void listMovies(ResponseMoviesList response);

}
