package com.javierarboleda.newyorktimesarticlesearch.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.javierarboleda.newyorktimesarticlesearch.models.NytResponse;
import com.javierarboleda.newyorktimesarticlesearch.network.NytNetworkHelper;

/**
 * Created on 10/18/16.
 */
public class NewsStreamActivity extends AppCompatActivity implements NytNetworkHelper.Callback {

    public static final String TAG = NewsStreamActivity.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NytNetworkHelper.testRetrofit(this);
    }

    @Override
    public void onResponseResult(NytResponse nytResponse) {
        Log.d(TAG, nytResponse.getResponse().getDocs().get(0).getSnippet());
    }
}
