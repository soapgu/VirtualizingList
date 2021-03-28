package com.soapdemo.virtualizinglist.util;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

public class BindingAdapters {
    @BindingAdapter("imageFromUrl")
    public  static void  loadGlide(ImageView imageView , String imageUrl ){
        if (!TextUtils.isEmpty(imageUrl) ) {
            Glide.with(imageView.getContext())
                    .load(imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        }
    }
}
