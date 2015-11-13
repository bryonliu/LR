package com.top.bryon.lr.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.top.bryon.lr.app.AppManager;

/**
 * Created by bryonliu on 2015/11/13.
 */
public class BaseActivity  extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.add(this);
    }
}
