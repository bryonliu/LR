package com.top.bryon.lr.app;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bryonliu on 2015/11/13.
 */
public class AppManager {

    private static List<WeakReference<Activity>> activityList = new ArrayList<>();

    public static void add(Activity activity) {
        clearNUll();
        activityList.add(new WeakReference<Activity>(activity));
    }

    public static void remove(Activity activity) {
        for (WeakReference<Activity> weakReference : activityList) {
            Activity activityCurrent = weakReference.get();
            if (activity == null || activityCurrent == activity) {
                activityList.remove(weakReference);
            }
        }
        activity.finish();
    }

    private static void clearNUll() {
        for (WeakReference<Activity> weakReference : activityList) {
            Activity activity = weakReference.get();
            if (activity == null) {
                activityList.remove(weakReference);
            }
        }
    }

    public static void clear() {
        for (WeakReference<Activity> weakReference : activityList) {
            Activity activity = weakReference.get();
            if (activity == null) {
                activity.finish();
            }
        }
        activityList.clear();
    }

}
