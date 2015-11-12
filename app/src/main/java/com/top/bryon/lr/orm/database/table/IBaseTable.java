package com.top.bryon.lr.orm.database.table;

import android.database.sqlite.SQLiteDatabase;

import com.top.bryon.lr.orm.database.helper.SqliteHelper;


/**
 * 数据库表的基类
 *
 * 注意:继续该类的子类一定要有默认的构造方法
 */
public interface IBaseTable {

    /**
     * 表的版本号,在表结构变化时，务必升级该字段
     *
     * @return
     */
    public int tableVersion();

    /**
     * 表名
     * @return
     */
    public String tableName();

    /**
     * 创建数据表之前的操作
     * @param db
     */
    void beforeCreateTable(SQLiteDatabase db);
    /**
     * 创建表的sql语句
     *
     * @return
     */
    public String createTableSQL();

    void afterCreateTable(SQLiteDatabase db);
    /**
     * 升级的alter语句
     * 增量升级, oldVersion 比newVersion 小1的传到相关的表中
     * 如 1-2 2-3 3-4 4-5...
     * @param oldVersion
     * @param newVersion
     * @return
     */
    public String[] getAlterSQL(int oldVersion, int newVersion);

    /**
     * 数据表升级前的数据迁移
     * 增量升级, oldVersion 比newVersion 小1的传到相关的表中
     * 如 1-2 2-3 3-4 4-5...
     * @param oldVersion
     * @param newVersion
     * @return
     */
    public void beforeTableAlter(int oldVersion, int newVersion, SQLiteDatabase db);

    /**
     * 数据表升级后的数据迁移
     * 增量升级, oldVersion 比newVersion 小1的传到相关的表中
     * 如 1-2 2-3 3-4 4-5...
     * @param oldVersion
     * @param newVersion
     * @return
     */
    public void afterTableAlter(int oldVersion, int newVersion, SQLiteDatabase db);

    /**
     * 返回数据库管理的Helper, 一个Helper的子类代表一个数据库
     * @return
     */
    public SqliteHelper getHelper();

}
