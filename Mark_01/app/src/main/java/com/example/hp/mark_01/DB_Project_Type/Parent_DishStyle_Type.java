package com.example.hp.mark_01.DB_Project_Type;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by hp on 2016/8/25.
 */
@DatabaseTable
public class Parent_DishStyle_Type {
    @DatabaseField(id = true)
    private int ID;
    @DatabaseField
    private String name;

    public Parent_DishStyle_Type() {
    }

    public Parent_DishStyle_Type(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
