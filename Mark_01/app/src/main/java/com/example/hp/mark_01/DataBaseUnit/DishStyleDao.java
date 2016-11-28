package com.example.hp.mark_01.DataBaseUnit;

import android.content.Context;

import com.example.hp.mark_01.DB_Project_Type.DB_DishStyle_Type;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2016/8/24.
 */
public class DishStyleDao {
    private Dao<DB_DishStyle_Type,Integer> dao;
    public DishStyleDao(Context context){
        DishBaseHelper baseHelper=DishBaseHelper.getInstanse(context);

        try {
            dao=baseHelper.getDao(DB_DishStyle_Type.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDishTypeToTable(DB_DishStyle_Type dishstyle){

        try {
            dao.createIfNotExists(dishstyle);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<DB_DishStyle_Type> findAllDishStyle(){
        List<DB_DishStyle_Type> dishstylelist = new ArrayList<>();
        try {
            dishstylelist=dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishstylelist;
    }

}
