package com.example.hp.mark_01.DB_Project_Type;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by hp on 2016/8/24.
 */
@DatabaseTable(tableName = "dishstyle")
public class DB_DishStyle_Type {
    @DatabaseField(id = true)
    private int id;
    @DatabaseField
    private String title;
    @DatabaseField
    private String picurl;
    @DatabaseField
    private int parentid;

    public DB_DishStyle_Type() {
    }

    public DB_DishStyle_Type(int id, String title, int parentid) {
        this.id = id;
        this.title = title;
        this.parentid = parentid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }
}
