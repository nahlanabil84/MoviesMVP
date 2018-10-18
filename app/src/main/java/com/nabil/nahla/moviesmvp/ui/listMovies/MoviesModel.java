package com.nabil.nahla.moviesmvp.ui.listMovies;

import com.nabil.nahla.moviesmvp.BuildConfig;
import com.nabil.nahla.moviesmvp.R;
import com.nabil.nahla.moviesmvp.data.models.ResponseMoviesList;
import com.nabil.nahla.moviesmvp.data.network.ApiHelper;
import com.nabil.nahla.moviesmvp.data.network.AppApiHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesModel implements MoviesModelMVP {
    private static final String API_KEY = BuildConfig.API_KEY;

    @Override
    public void getMovies(int page, final OnDataLoadedListener listener) {
        AppApiHelper.getRetrofitInstance().create(ApiHelper.class).getPopularMovies(API_KEY ,page).enqueue(new Callback<ResponseMoviesList>() {
            @Override
            public void onResponse(Call<ResponseMoviesList> call, Response<ResponseMoviesList> response) {
                if (response.isSuccessful())
                    listener.onSuccess(response.body());
                else
                    listener.onFailed(R.string.error_fetching_movies);
            }

            @Override
            public void onFailure(Call<ResponseMoviesList> call, Throwable t) {
                listener.onFailed(R.string.error_fetching_movies);
            }
        });
    }
}
