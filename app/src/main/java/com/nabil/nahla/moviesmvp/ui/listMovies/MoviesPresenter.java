package com.nabil.nahla.moviesmvp.ui.listMovies;

import com.nabil.nahla.moviesmvp.data.models.ResponseMoviesList;
import com.nabil.nahla.moviesmvp.ui.listMovies.MoviesModelMVP.OnDataLoadedListener;

public class MoviesPresenter implements MoviesPresenterMVP, OnDataLoadedListener {
    MoviesModelMVP modelMVP;
    MoviesViewMVP viewMVP;

    public MoviesPresenter(MoviesViewMVP viewMVP) {
        this.viewMVP = viewMVP;
        this.modelMVP = new MoviesModel();
    }

    @Override
    public void loadMovies(int page, String query) {
        viewMVP.showLoading();
        if (query == null | query.equals("") | query.isEmpty()) {
            modelMVP.getMovies(page, this);
        } else {
            modelMVP.searchMovies(page, query, this);
        }
    }

    @Override
    public void onFailed(int stringResourceId) {
        viewMVP.hideLoading();
        viewMVP.showMessage(stringResourceId);
    }

    @Override
    public void onFailed(String errorMsg) {
        viewMVP.hideLoading();
        viewMVP.showMessage(errorMsg);
    }

    @Override
    public void onSuccess(ResponseMoviesList response) {
        viewMVP.hideLoading();
        viewMVP.listMovies(response);
    }
}
