package com.top.bryon.lr.entity;

/**
 * Created by bryonliu on 2015/11/12.
 */
public class Book {

    //标题
    public String title;
    //子标题
    public String subtitle;
    //作者
    public String author;

    //封面图片
    public String[] images;
    //图书标签
    public String[] tags;

    //印刷日期
    public String pubdate;

    //内容简介
    public String summary;
    //isbn 其实有多种isbn，我们只存扫描到的
    public String isbn;

}
