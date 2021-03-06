package com.top.bryon.lr.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.top.bryon.lr.R;
import com.top.bryon.lr.entity.Book;
import com.top.bryon.lr.orm.database.dao.BookDao;
import com.top.bryon.lr.ui.adapter.BookShelfAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by bryonliu on 2016/1/21.
 */
public class BookShelfActivity extends BaseActivity {

    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.gv_book_shelf)
    GridView mGvBookShelf;

    private BookShelfAdapter shelfAdapter;

    private List<Book> bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_book_shelf);
        initView();
        fillData();
    }

    private void initView() {
        shelfAdapter = new BookShelfAdapter(this);
        mGvBookShelf.setAdapter(shelfAdapter);

        mGvBookShelf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (bookList != null && position == bookList.size()) {
                    CaptureActivityPortrait.start(BookShelfActivity.this);
                }
            }
        });
        mGvBookShelf.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO  处理除了最后一项之外的其他项的长按，进入编辑状态
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
            } else {
                BookScanActivity.jumpFromMainActivity(this, result.getContents());
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void fillData() {
        Observable.just("").map(new Func1<String, List<Book>>() {
            @Override
            public List<Book> call(String s) {
                return BookDao.instance().findAll();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Book>>() {
                    @Override
                    public void call(List<Book> books) {
                        bookList = books;
                        shelfAdapter.update(books);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.iv_giveup})
    public void onCilck(View view) {
        switch (view.getId()) {
            case R.id.iv_giveup:
                //TODO
            default:
                break;
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, BookShelfActivity.class);
        context.startActivity(starter);
    }
}
