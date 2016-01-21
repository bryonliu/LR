package com.top.bryon.lr.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.top.bryon.lr.R;
import com.top.bryon.lr.entity.Book;
import com.top.bryon.lr.orm.database.dao.BookDao;
import com.top.bryon.lr.ui.widget.ExpandableTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by bryonliu on 2016/1/18.
 */
public class BookScanActivity extends BaseActivity {

    private static final String ISBN = "tag_isbn";

    private static final String DOU_BAN_URL_GET_BOOKINFO_BY_ISBN = "https://api.douban.com/v2/book/isbn/";
    private static final String TAG = "bryon";
    private String isbn;

    @Bind(R.id.iv_book_cover)
    SimpleDraweeView mIvBookImage;
    @Bind(R.id.tv_autor_name)
    TextView mTvBookAuthor;
    @Bind(R.id.tv_book_des)
    ExpandableTextView mTvBookSummary;
    @Bind(R.id.tv_book_title)
    TextView mTvBookTitle;

    @Bind(R.id.tag1)
    TextView mBookTagFirst;
    @Bind(R.id.tag2)
    TextView mBookTagSecond;

    @Bind(R.id.rl_title)
    View mBookContainView;
    private Book book = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_scan);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        isbn = intent.getStringExtra(ISBN);

        Observable.just(isbn).map(new Func1<String, Book>() {
            @Override
            public Book call(String s) {

                try {
                    OkHttpClient httpClient = new OkHttpClient();
                    Request request = new Request.Builder().get().url(DOU_BAN_URL_GET_BOOKINFO_BY_ISBN + s).build();
                    Response response = null;
                    response = httpClient.newCall(request).execute();
                    String body = response.body().string();
                    Log.d(TAG, "call: " + body);
                    book = translateJsonString2BookObject(body);
                } catch (Exception e) {
                    Log.e(TAG, "call: ", e);
                    book = null;
                }
                return book;
            }
        }).filter(new Func1<Book, Boolean>() {
            @Override
            public Boolean call(Book book) {
                if (book == null) {
                    finish();
                }
                return book != null;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Book>() {

            @Override
            public void call(Book book) {
                fillUI(book);
            }
        });

    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            Log.e(TAG, "getBitmapFromURL: ", e);
            return null;
        }
    }

    private void fillUI(Book book) {
        mTvBookAuthor.setText(book.author);
        mTvBookSummary.setText(book.summary);
        mTvBookTitle.setText(book.title);
        if (book.tags != null && book.tags.length > 0) {
            mBookTagFirst.setText(book.tags[0]);
        }
        if (book.tags != null && book.tags.length > 1) {
            mBookTagSecond.setText(book.tags[1]);
        }

        Observable.just(book.images[0]).map(new Func1<String, Bitmap>() {
            @Override
            public Bitmap call(String s) {
                return getBitmapFromURL(s);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Bitmap>() {
            @Override
            public void call(final Bitmap bitmap) {
                Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        // 有活力的暗色
                        Palette.Swatch darkVibrant = palette.getDarkVibrantSwatch();
                        mIvBookImage.setImageBitmap(bitmap);
                        mBookContainView.setBackground(new ColorDrawable(darkVibrant != null ? darkVibrant.getRgb() : getResources().getColor(R.color.darkRed)));
                        mTvBookSummary.setTextExpendColor(darkVibrant != null ? darkVibrant.getRgb() : getResources().getColor(R.color.darkRed));
                    }
                });
            }
        });
    }

    private Book translateJsonString2BookObject(String bookinfoStr) throws JSONException {
        Book book = new Book();
        JSONObject jsonObject = new JSONObject(bookinfoStr);
        book.title = jsonObject.getString("title");
        book.subtitle = jsonObject.getString("subtitle");
        book.author = jsonObject.getJSONArray("author").getString(0);
        book.summary = jsonObject.getString("summary");
        book.tags = getBookTas(jsonObject.getJSONArray("tags"));
        book.images = getImgs(jsonObject.getJSONObject("images"));
        book.pubdate = jsonObject.getString("pubdate");
        return book;
    }

    private String[] getImgs(JSONObject images) throws JSONException {
        String[] imgs = new String[images.length()];
        imgs[1] = images.getString("small");
        imgs[0] = images.getString("large");
        imgs[2] = images.getString("medium");
        return imgs;
    }

    private String[] getBookTas(JSONArray jsonArray) throws JSONException {

        String[] tags = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            tags[i] = jsonObject.getString("name");
        }
        return tags;
    }

    public static void jumpFromMainActivity(Activity activity, String isbn) {
        Intent intent = new Intent(activity, BookScanActivity.class);
        intent.putExtra(ISBN, isbn);
        activity.startActivity(intent);
    }

    @OnClick({R.id.iv_done, R.id.iv_giveup})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_giveup:
                finish();
                break;
            case R.id.iv_done:
                //TODO share the book

                Observable.just(book).map(new Func1<Book, Integer>() {
                    @Override
                    public Integer call(Book book) {
                        book.isbn = isbn;
                        return BookDao.instance().insert(book);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Toast.makeText(BookScanActivity.this, integer + "", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            default:
                break;
        }
    }

}
