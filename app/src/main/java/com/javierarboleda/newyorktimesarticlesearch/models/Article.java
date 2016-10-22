package com.javierarboleda.newyorktimesarticlesearch.models;

import com.javierarboleda.newyorktimesarticlesearch.utils.NytApiUtil;

/**
 * Created on 10/21/16.
 */
public class Article {
    private String headline;
    private String imageUrl;


    public Article(String headline, String imageUrl) {
        this.headline = headline;
        this.imageUrl = imageUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getImageUrl() {
        return NytApiUtil.NYT_BASE_URL + imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
