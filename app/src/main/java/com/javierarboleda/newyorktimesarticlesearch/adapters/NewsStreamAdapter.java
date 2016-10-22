package com.javierarboleda.newyorktimesarticlesearch.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.javierarboleda.newyorktimesarticlesearch.R;
import com.javierarboleda.newyorktimesarticlesearch.databinding.ItemArticleBinding;
import com.javierarboleda.newyorktimesarticlesearch.models.Article;

import java.util.ArrayList;

/**
 * Created on 10/20/16.
 */
public class NewsStreamAdapter extends RecyclerView.Adapter<NewsStreamAdapter.NewsStreamViewHolder> {

    private Context mContext;
    private ArrayList<Article> mArticles;

    public NewsStreamAdapter(Context context, ArrayList<Article> articles) {
        mContext = context;
        mArticles = articles;
    }


    public void setArticles(ArrayList<Article> articles) {
        mArticles = articles;
    }

    @Override

    public NewsStreamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemArticleBinding viewDataBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_article, parent, false);

        // Return a new holder instance
        NewsStreamViewHolder viewHolder = new NewsStreamViewHolder(viewDataBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsStreamViewHolder holder, int position) {

        final Article article = mArticles.get(position);
        holder.binding.setArticle(article);
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public class NewsStreamViewHolder extends RecyclerView.ViewHolder {
        public ItemArticleBinding binding;

        public NewsStreamViewHolder(ItemArticleBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            binding = viewDataBinding;
        }
    }


}
