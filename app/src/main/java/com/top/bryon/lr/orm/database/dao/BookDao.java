package com.top.bryon.lr.orm.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.top.bryon.lr.app.LrApplication;
import com.top.bryon.lr.entity.Book;
import com.top.bryon.lr.orm.database.helper.MainDbHelper;
import com.top.bryon.lr.orm.database.helper.SQLiteDatabaseWrapper;
import com.top.bryon.lr.orm.database.table.BookTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bryonliu on 2015/11/12.
 */
public class BookDao implements IBaseDao<Book> {

    private static final String TAG = "bryon";
    private SQLiteDatabaseWrapper db;

    private static BookDao bookDao;

    public static synchronized BookDao instance() {
        if (bookDao == null) {
            bookDao = new BookDao();
        }
        return bookDao;
    }

    private BookDao() {
        db = MainDbHelper.get(LrApplication.self()).getWritableDatabaseWrapper();
    }

    @Override
    public int insert(Book book) {
        int ret = 0;
        ContentValues values = new ContentValues();
        values.put(BookTable.Columns.AUTHOR, book.author);
        values.put(BookTable.Columns.IMAGES, book.images[0]);
        values.put(BookTable.Columns.ISBN, book.isbn);
        values.put(BookTable.Columns.PUBDATE, book.pubdate);
        values.put(BookTable.Columns.SUBTITLE, book.subtitle);
        values.put(BookTable.Columns.SUMMARY, book.summary);
        values.put(BookTable.Columns.TAGS, book.tags[0]);
        values.put(BookTable.Columns.TITLE, book.title);
        ret += db.insert(BookTable.TABLE_NAME, null, values);
        return ret;
    }

    @Override
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        Cursor cursor = null;

        String[] coloms = {
                BookTable.Columns.AUTHOR,
                BookTable.Columns.ID,
                BookTable.Columns.IMAGES,
                BookTable.Columns.ISBN,
                BookTable.Columns.PUBDATE,
                BookTable.Columns.SUBTITLE,
                BookTable.Columns.SUMMARY,
                BookTable.Columns.TAGS,
                BookTable.Columns.TITLE,
        };
        try {
            cursor = db.query(BookTable.TABLE_NAME, coloms, null, null, null, null, null);
            while (cursor.moveToNext()) {
                Book book = new Book();
                book.author = cursor.getString(cursor.getColumnIndex(BookTable.Columns.AUTHOR));
                book.images = new String[1];
                book.images[0] = cursor.getString(cursor.getColumnIndex(BookTable.Columns.IMAGES));

                book.isbn = cursor.getString(cursor.getColumnIndex(BookTable.Columns.ISBN));
                book.pubdate = cursor.getString(cursor.getColumnIndex(BookTable.Columns.PUBDATE));
                book.subtitle = cursor.getString(cursor.getColumnIndex(BookTable.Columns.SUBTITLE));
                book.summary = cursor.getString(cursor.getColumnIndex(BookTable.Columns.SUMMARY));
                book.tags = new String[1];
                book.tags[0] = cursor.getString(cursor.getColumnIndex(BookTable.Columns.TAGS));
                book.title = cursor.getString(cursor.getColumnIndex(BookTable.Columns.TITLE));
                bookList.add(book);
            }
        } catch (Exception e) {
            Log.e(TAG, "findAll: ", e);
        } finally {
            cursor.close();
        }
        return bookList;
    }

    @Override
    public Book findById(int id) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
