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
    public void loadMovies(int page) {
        viewMVP.showLoading();
        modelMVP.getMovies(page, this);
    }

    @Override
    public void onFailed(int stringResourceId) {
        viewMVP.hideLoading();
        viewMVP.showMessage(stringResourceId);
    }

    @Override
    public void onSuccess(ResponseMoviesList response) {
        viewMVP.hideLoading();
        viewMVP.listMovies(response);
    }
}
