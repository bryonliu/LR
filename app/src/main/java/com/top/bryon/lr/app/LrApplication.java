package com.top.bryon.lr.app;

import android.app.Application;

/**
 * Created by bryonliu on 2015/11/13.
 */
public class LrApplication extends Application {
    private static LrApplication mApp;

    public static LrApplication self() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }
}
