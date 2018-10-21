package com.nabil.nahla.moviesmvp.ui.listMovies;

import com.nabil.nahla.moviesmvp.data.models.ResponseMoviesList;

public interface MoviesModelMVP {

    interface OnDataLoadedListener {
        void onFailed(int stringResourceId);

        void onFailed(String errorMsg);

        void onSuccess(ResponseMoviesList response);
    }

    void getMovies(int page, OnDataLoadedListener listener);

    void searchMovies(int page, String query, OnDataLoadedListener listener);

}
