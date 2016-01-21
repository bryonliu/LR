package com.top.bryon.lr.orm.database.helper;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.top.bryon.lr.orm.database.table.BookTable;


/**
 */
public class MainDbHelper extends SqliteHelper {


    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "top.bryon.lr";

    /** 后续有表的增加或减少，这里进行维护 */
    private static final Class<?>[] TABLESS ={
            BookTable.class
    };
    private volatile static SqliteHelper instance;


    public static synchronized SqliteHelper get(Context context) {
        if (instance == null) {
            instance = new MainDbHelper(context, DB_NAME, null, DB_VERSION);
        }
        return instance;
    }

    public MainDbHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public Class<?>[] getTables() {
        return TABLESS;
    }

    @Override
    public int getDBVersion() {
        return DB_VERSION;
    }


}