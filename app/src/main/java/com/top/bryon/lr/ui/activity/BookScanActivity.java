package com.top.bryon.lr.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.top.bryon.lr.R;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by bryonliu on 2016/1/18.
 */
public class BookScanActivity extends BaseActivity {

    private static final String ISBN = "tag_isbn";

    private static final String DOU_BAN_URL_GET_BOOKINFO_BY_ISBN = "https://api.douban.com/v2/book/isbn/";
    private static final String TAG = "bryon";
    private String isbn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_scan);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        isbn = intent.getStringExtra(ISBN);

        //TODO
        Observable.just(isbn).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        });
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder().get().url(DOU_BAN_URL_GET_BOOKINFO_BY_ISBN + ISBN).build();
        Response response = null;
        try {
            response = httpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "initData: " + response.body().toString());
    }

    public static void jumpFromMainActivity(Activity activity, String isbn) {
        Intent intent = new Intent(activity, BookScanActivity.class);
        intent.putExtra(ISBN, isbn);
        activity.startActivity(intent);
    }
}
