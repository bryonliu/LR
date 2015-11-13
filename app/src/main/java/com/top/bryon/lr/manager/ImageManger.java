package com.top.bryon.lr.manager;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by bryonliu on 2015/11/13.
 */
public class ImageManger extends BaseManager {

    private static class SingleTon {
        private static ImageManger instance = new ImageManger();
    }

    private ImageManger() {

    }

    public static ImageManger instance() {
        return SingleTon.instance;
    }

    public void display(Context context, String url, ImageView view) {
        Glide.with(context).load(url).into(view);
    }
}
