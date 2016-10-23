package com.javierarboleda.newyorktimesarticlesearch.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.javierarboleda.newyorktimesarticlesearch.R;
import com.javierarboleda.newyorktimesarticlesearch.databinding.ActivityArticleBinding;
import com.javierarboleda.newyorktimesarticlesearch.models.Article;
import com.javierarboleda.newyorktimesarticlesearch.utils.AppConstants;

public class ArticleActivity extends AppCompatActivity {

    public ActivityArticleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_article);

        setSupportActionBar(binding.toolbar);

        final Article article = getIntent().getParcelableExtra(AppConstants.ARTICLE_KEY_NAME);

        binding.wvArticle.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(article.getWebUrl());
                return true;
            }
        });

        binding.wvArticle.loadUrl(article.getWebUrl());
    }
}
