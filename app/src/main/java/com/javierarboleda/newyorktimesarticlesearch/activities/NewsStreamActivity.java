package com.javierarboleda.newyorktimesarticlesearch.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.javierarboleda.newyorktimesarticlesearch.R;
import com.javierarboleda.newyorktimesarticlesearch.adapters.NewsStreamAdapter;
import com.javierarboleda.newyorktimesarticlesearch.databinding.ActivityNewsStreamBinding;
import com.javierarboleda.newyorktimesarticlesearch.models.Article;
import com.javierarboleda.newyorktimesarticlesearch.models.Doc;
import com.javierarboleda.newyorktimesarticlesearch.models.NytResponse;
import com.javierarboleda.newyorktimesarticlesearch.network.NytNetworkHelper;

import java.util.ArrayList;

/**
 * Created on 10/18/16.
 */
public class NewsStreamActivity extends AppCompatActivity implements NytNetworkHelper.Callback {

    public static final String TAG = NewsStreamActivity.class.getName();

    private ActivityNewsStreamBinding binding;
    private NewsStreamAdapter mAdapter;
    private ArrayList<Doc> mDocs;
    private ArrayList<Article> mArticles;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mArticles = new ArrayList<>();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_stream);

        setUpRecyclerView();

        NytNetworkHelper.callNytApi(this);
    }

    private void setUpRecyclerView() {

        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);

        mAdapter = new NewsStreamAdapter(this, mArticles);
        binding.rvResults.setAdapter(mAdapter);
        binding.rvResults.setLayoutManager(gridLayoutManager);
    }

    private void updateRecyclerView() {

        mAdapter.setArticles(mArticles);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResponseResult(NytResponse nytResponse) {

        mDocs = (ArrayList<Doc>) nytResponse.getResponse().getDocs();

        for (Doc doc : mDocs) {

            mArticles.add(new Article(
                    doc.getHeadline().getMain(),
                    doc.getMultimedia().size() > 0 ? doc.getMultimedia().get(0).getUrl() : null
            ));
        }

        updateRecyclerView();
    }

    public void onArticleSearch(View view) {

    }
}
