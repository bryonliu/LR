package com.top.bryon.lr.orm.database.dao;

import java.util.List;

/**
 * Created by bryonliu on 2016/1/21.
 */
public interface IBaseDao<T> {
    int insert(T t);

    List<T> findAll();

    T findById(int id);

    void delete(int id);

}
