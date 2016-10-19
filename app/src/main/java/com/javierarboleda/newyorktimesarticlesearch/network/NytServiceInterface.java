package com.javierarboleda.newyorktimesarticlesearch.network;

import com.javierarboleda.newyorktimesarticlesearch.models.NytResponse;
import com.javierarboleda.newyorktimesarticlesearch.utils.NytApiUtil;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created on 10/18/16.
 */
public interface NytServiceInterface {

    @GET("articlesearch.json")
    Call<NytResponse> getResponse(
            @Query(NytApiUtil.API_KEY_PARAM) String apiKey
    );
}
