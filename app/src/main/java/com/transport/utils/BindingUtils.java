package com.transport.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.google.android.gms.common.api.Api;
import com.transport.api.ApiClient;
import com.transport.app.GlideApp;

/**
 * Created by SAM on 16/3/20.
 */
public class BindingUtils {

    @BindingAdapter("image")
    public static void loadImage(ImageView view, String url) {
        GlideApp.with(view.getContext()).load(ApiClient.PREFIX + url).centerCrop().into(view);
    }

    @BindingAdapter("bind:circleImage")
    public static void loadCircleImage(ImageView view, String url) {
        GlideApp.with(view.getContext()).load(url).circleCrop().into(view);
    }
}
