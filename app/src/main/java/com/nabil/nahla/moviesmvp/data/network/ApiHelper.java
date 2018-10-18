package com.nabil.nahla.moviesmvp.data.network;

import com.nabil.nahla.moviesmvp.data.models.ResponseMoviesList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiHelper {

    @GET(ApiEndPoints.POPULAR)
    Call<ResponseMoviesList> getPopularMovies(@Query("api_key") String apiKey,
                                            @Query("page") int page);

    @GET(ApiEndPoints.SEARCH)
    Call<ResponseMoviesList> searchMovies(@Query("api_key") String apiKey,
                                          @Query("query") String query,
                                          @Query("page") int page);

}
