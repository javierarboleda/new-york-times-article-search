package com.javierarboleda.newyorktimesarticlesearch.adapters;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.javierarboleda.newyorktimesarticlesearch.R;
import com.squareup.picasso.Picasso;

/**
 * Created on 10/20/16.
 */
public class BindingAdapterUtils {
    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String url) {

        if (!url.equals("http://www.nytimes.com/null")) {
            Picasso.with(view.getContext())
                    .load(url)
                    .placeholder(R.drawable.backdrop_placeholder_w780)
                    .into(view);
        }
    }
}
