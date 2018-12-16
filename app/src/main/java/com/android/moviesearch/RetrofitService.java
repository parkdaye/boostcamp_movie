package com.android.moviesearch;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;

import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.QueryMap;

public interface RetrofitService {
    @GET("movie.json")
    Call<movieRepo> getMovieItem(@HeaderMap Map<String, String> headers,
                                 @QueryMap Map<String, String> option);
}
