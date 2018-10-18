package com.nabil.nahla.moviesmvp.ui.listMovies;

import com.nabil.nahla.moviesmvp.data.models.ResponseMoviesList;

public interface MoviesModelMVP {

    interface OnDataLoadedListener {
        void onFailed(int stringResourceId);
        void onSuccess(ResponseMoviesList response);
    }

    void getMovies(int page, OnDataLoadedListener listener);

}
