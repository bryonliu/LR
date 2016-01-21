package com.top.bryon.lr.orm.database.table;

import android.database.sqlite.SQLiteDatabase;

import com.top.bryon.lr.app.LrApplication;
import com.top.bryon.lr.orm.database.helper.MainDbHelper;
import com.top.bryon.lr.orm.database.helper.SqliteHelper;

/**
 * Created by bryonliu on 2016/1/21.
 */
public class BookTable implements IBaseTable {
    private static final int TABLE_VERSION = 1;
    public static final String TABLE_NAME = BookTable.class.getSimpleName();
    private static final String SQL_CREATE_TABLE = "CREATE TABLE if not exists "  + TABLE_NAME + "("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT ," + " title TEXT, "  + " subtitle TEXT, "
            + " author TEXT, "  + " images TEXT, "  + " tags TEXT, "+ " pubdate TEXT, "+ " summary TEXT, "
            + " isbn TEXT ); ";

    public class Columns{
        public static final String ID  = "_id";
        public static final String TITLE  = "title";
        public static final String SUBTITLE  = "subtitle";
        public static final String AUTHOR  = "author";
        public static final String IMAGES  = "images";
        public static final String TAGS  = "tags";
        public static final String PUBDATE  = "pubdate";
        public static final String SUMMARY  = "summary";
        public static final String ISBN  = "isbn";

    }
    @Override
    public int tableVersion() {
        return TABLE_VERSION;
    }

    @Override
    public String tableName() {
        return TABLE_NAME;
    }

    @Override
    public void beforeCreateTable(SQLiteDatabase db) {

    }

    @Override
    public String createTableSQL() {
        return SQL_CREATE_TABLE;
    }

    @Override
    public void afterCreateTable(SQLiteDatabase db) {

    }

    @Override
    public String[] getAlterSQL(int oldVersion, int newVersion) {
        return new String[0];
    }

    @Override
    public void beforeTableAlter(int oldVersion, int newVersion, SQLiteDatabase db) {

    }

    @Override
    public void afterTableAlter(int oldVersion, int newVersion, SQLiteDatabase db) {

    }

    @Override
    public SqliteHelper getHelper() {
        return MainDbHelper.get(LrApplication.self());
    }

}
