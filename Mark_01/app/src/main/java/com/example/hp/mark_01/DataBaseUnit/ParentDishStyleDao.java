package com.example.hp.mark_01.DataBaseUnit;

import android.content.Context;

import com.example.hp.mark_01.DB_Project_Type.Parent_DishStyle_Type;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2016/8/25.
 */
public class ParentDishStyleDao {
    private Dao<Parent_DishStyle_Type, Integer> dao;

    public ParentDishStyleDao(Context context) {
        DishBaseHelper baseHelper = DishBaseHelper.getInstanse(context);

        try {
            dao = baseHelper.getDao(Parent_DishStyle_Type.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addParentDishType(Parent_DishStyle_Type dishStyle_type) {
        try {
            dao.createIfNotExists(dishStyle_type);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateParentDishType(Parent_DishStyle_Type dishStyle_type) {
        try {
            dao.update(dishStyle_type);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Parent_DishStyle_Type> findAllParentDishType(){
        List<Parent_DishStyle_Type> dishStyle_typeList=new ArrayList<>();
        try {
            dishStyle_typeList=dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishStyle_typeList;
    }

}
