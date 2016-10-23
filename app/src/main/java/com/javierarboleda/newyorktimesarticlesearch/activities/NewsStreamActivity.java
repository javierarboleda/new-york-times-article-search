package com.javierarboleda.newyorktimesarticlesearch.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.javierarboleda.newyorktimesarticlesearch.R;
import com.javierarboleda.newyorktimesarticlesearch.adapters.EndlessRecyclerViewScrollListener;
import com.javierarboleda.newyorktimesarticlesearch.adapters.NewsStreamAdapter;
import com.javierarboleda.newyorktimesarticlesearch.databinding.ActivityNewsStreamBinding;
import com.javierarboleda.newyorktimesarticlesearch.models.Article;
import com.javierarboleda.newyorktimesarticlesearch.models.Doc;
import com.javierarboleda.newyorktimesarticlesearch.models.NytResponse;
import com.javierarboleda.newyorktimesarticlesearch.network.NytNetworkHelper;
import com.javierarboleda.newyorktimesarticlesearch.utils.AppConstants;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created on 10/18/16.
 */
public class NewsStreamActivity extends AppCompatActivity implements NytNetworkHelper.Callback {

    public static final String TAG = NewsStreamActivity.class.getName();

    private ActivityNewsStreamBinding binding;
    private NewsStreamAdapter mAdapter;
    private ArrayList<Doc> mDocs;
    private ArrayList<Article> mArticles;
    private String mQuery;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mArticles = new ArrayList<>();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_stream);

        setSupportActionBar(binding.toolbar);

        setUpRecyclerView();

        //callNytApi("0", null);
    }

    private void callNytApi(String page, String thisquery) {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        String query = thisquery;
        String beginDate = sharedPref.getString(AppConstants.BEGIN_DATE_QUERY_KEY_NAME, null);
        String endDate = null;
        String sort = sharedPref.getBoolean(AppConstants.SORT_NEWEST_KEY_NAME, true)
                        ? "newest" : "oldest";
        HashSet<String> newsDeskValues = (HashSet<String>) sharedPref
                .getStringSet(AppConstants.NEWS_DESK_VALUES_KEY_NAME, null);



        NytNetworkHelper.callNytApi(this, query, beginDate, endDate, sort, newsDeskValues, page);
    }

    private void setUpRecyclerView() {

        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        mAdapter = new NewsStreamAdapter(this, mArticles);
        binding.rvResults.setAdapter(mAdapter);
        binding.rvResults.setLayoutManager(gridLayoutManager);

        binding.rvResults.addOnScrollListener(
                new EndlessRecyclerViewScrollListener(gridLayoutManager) {

            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                callNytApi(String.valueOf(page), mQuery);
            }
        });

    }

    private void updateRecyclerView() {

        mAdapter.setArticles(mArticles);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResponseResult(NytResponse nytResponse) {

        if (nytResponse == null ) {
            System.out.print("null!");
            return;
        }

        mDocs = (ArrayList<Doc>) nytResponse.getResponse().getDocs();

        for (Doc doc : mDocs) {

            mArticles.add(new Article(
                    doc.getHeadline().getMain(),
                    doc.getWebUrl(),
                    doc.getMultimedia().size() > 0 ? doc.getMultimedia().get(0).getUrl() : null
            ));
        }

        updateRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_news_stream, menu);
        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here

                mQuery = query;
                callNytApi("0", mQuery);

                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.actionSettings:
                startSettingsActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
