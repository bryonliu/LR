package com.top.bryon.lr.ui.activity;

import android.app.Activity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.CaptureActivity;

/**
 * Created by bryonliu on 2016/1/15.
 */
public class CaptureActivityPortrait extends CaptureActivity {

    public static void start(Activity context) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(context);
        intentIntegrator.setCaptureActivity(CaptureActivityPortrait.class);
        // intentIntegrator.setOrientationLocked(true);
        intentIntegrator.initiateScan();
    }
}
