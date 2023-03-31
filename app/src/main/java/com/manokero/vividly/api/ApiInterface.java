package com.manokero.vividly.api;

import static com.manokero.vividly.api.ApiProvider.API_KEY;

import com.manokero.vividly.model.ImageModel;
import com.manokero.vividly.model.SearchModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiInterface {

    @Headers("Authorization: Client-ID " + API_KEY)
    @GET("/photos")
    Call<List<ImageModel>> getImages(
            @Query("page") int page,
            @Query("per_page") int perPage);

    @Headers("Authorization: Client-ID " + API_KEY)
    @GET("/search/photos")
    Call<SearchModel> getSearchData(
            @Query("query") String query,
            @Query("page") int page,
            @Query("per_page") int perPage);
}
