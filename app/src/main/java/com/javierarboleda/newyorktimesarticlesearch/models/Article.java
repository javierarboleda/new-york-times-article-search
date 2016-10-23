package com.javierarboleda.newyorktimesarticlesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.javierarboleda.newyorktimesarticlesearch.utils.NytApiUtil;

/**
 * Created on 10/21/16.
 */
public class Article implements Parcelable {
    private String headline;
    private String webUrl;
    private String imageUrl;

    public Article() {

    }

    public Article(String headline, String webUrl, String imageUrl) {
        this.headline = headline;
        this.webUrl = webUrl;
        this.imageUrl = imageUrl;
    }

    // Using the `in` variable, we can retrieve the values that
    // we originally wrote into the `Parcel`.  This constructor is usually
    // private so that only the `CREATOR` field can access.
    private Article(Parcel in) {
        headline = in.readString();
        webUrl = in.readString();
        imageUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(headline);
        out.writeString(webUrl);
        out.writeString(imageUrl);
    }

    public static final Parcelable.Creator<Article> CREATOR
            = new Parcelable.Creator<Article>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getImageUrl() {
        return NytApiUtil.NYT_BASE_URL + imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
