package com.berkantcanerkanat.myseries;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MyApiCalls {

    @GET("3/search/tv?")
    Call<ReturnClass> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query
    );
    @GET("3/tv/{tv_id}?")
    Call<ReturnClassWId> getTheSeris(
            @Path("tv_id") int tv_id,
            @Query("api_key") String key
    );
    @GET("3/tv/{tv_id}?")
    Call<Result> getMySeries(
            @Path("tv_id") int tv_id,
            @Query("api_key") String key
    );

}
