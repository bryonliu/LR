package com.top.bryon.lr.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.top.bryon.lr.R;
import com.top.bryon.lr.entity.Book;
import com.top.bryon.lr.ui.activity.BookScanActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by bryonliu on 2016/1/21.
 */
public class BookShelfAdapter extends BaseAdapter {

    private Context mContext;
    private List<Book> bookList;

    private LayoutInflater inflater;

    public BookShelfAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        bookList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return bookList.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        if (position < bookList.size())
            return bookList.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_book_shelf, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == bookList.size()) {

            holder.mTvBookTitle.setVisibility(View.INVISIBLE);
            holder.mIvBookCover.setImageResource(R.drawable.ic_add_48dp);
            return convertView;
        }
        Book book = (Book) getItem(position);

        Observable.just(book.images[0]).map(new Func1<String, Bitmap>() {
            @Override
            public Bitmap call(String s) {
                return BookScanActivity.getBitmapFromURL(s);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {
                holder.mIvBookCover.setImageBitmap(bitmap);
            }
        });
        holder.mTvBookTitle.setText(book.title);
        return convertView;
    }

    public void update(List<Book> books) {
        bookList = books;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.iv_book_cover)
        ImageView mIvBookCover;
        @Bind(R.id.tv_book_title)
        TextView mTvBookTitle;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
