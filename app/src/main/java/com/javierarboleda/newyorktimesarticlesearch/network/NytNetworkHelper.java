package com.javierarboleda.newyorktimesarticlesearch.network;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.javierarboleda.newyorktimesarticlesearch.models.NytResponse;
import com.javierarboleda.newyorktimesarticlesearch.utils.NytApiUtil;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created on 10/18/16.
 */
public class NytNetworkHelper {

    public static final String TAG = NytNetworkHelper.class.getName();

    public interface Callback {
        void onResponseResult(NytResponse nytResponse);
    }

    public static void callNytApi(final Context context, String query, String beginDate,
                      String endDate, String sort, String page) {

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NytApiUtil.NYT_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        NytServiceInterface nytService = retrofit.create(NytServiceInterface.class);

        Call<NytResponse> call = nytService.getResponse(
                "35f523ef394540c9b7c155ef41ac6483",
                query,
                beginDate,
                endDate,
                sort,
                page
                );
        call.enqueue(new retrofit2.Callback<NytResponse>() {
            @Override
            public void onResponse(Call<NytResponse> call,
                                   retrofit2.Response<NytResponse> response) {

                NytResponse nytResponse = response.body();

                ((Callback) context).onResponseResult(nytResponse);
            }

            @Override
            public void onFailure(Call<NytResponse> call, Throwable t) {
                // Log error here since request failed
                Log.d(TAG, "Error in onFailure: " + t.getMessage());
            }
        });
    }

}
