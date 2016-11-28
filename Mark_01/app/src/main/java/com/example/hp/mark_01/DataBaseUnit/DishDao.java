package com.example.hp.mark_01.DataBaseUnit;

import android.content.Context;
import android.util.Log;
import android.view.WindowManager;

import com.example.hp.mark_01.DB_Project_Type.DB_Dish_Type;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2016/8/22.
 */
public class DishDao {
    private Dao<DB_Dish_Type, Integer> dishdao;

    public DishDao(Context context) {
        DishBaseHelper baseHelper = DishBaseHelper.getInstanse(context);

        try {
            dishdao = baseHelper.getDao(DB_Dish_Type.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long addDishToTable(DB_Dish_Type dish) {
        long id = 0;

        try {
            dishdao.createIfNotExists(dish);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public List<DB_Dish_Type> findAllDish() {
        try {
            return dishdao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<DB_Dish_Type> findDishByTitle(String title) {
        QueryBuilder<DB_Dish_Type, Integer> builder = dishdao.queryBuilder();
        List<DB_Dish_Type> db_dishList = new ArrayList<>();
        try {
            builder.where().like("title", "%" + title + "%");
            db_dishList = builder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return db_dishList;
    }

    public List<DB_Dish_Type> findDishByStyle(String dishstyle) {
        List<DB_Dish_Type> dish_typeList = new ArrayList<>();

        QueryBuilder<DB_Dish_Type, Integer> builder = dishdao.queryBuilder();
        try {
            dish_typeList = builder.where().like("tags", "%" + dishstyle + "%").query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dish_typeList;
    }

    //更新菜品的收藏状态
    public void updataDish(DB_Dish_Type dish_type) {
        Log.i("数据更新", "执行更新");
        try {
            dishdao.deleteById(dish_type.getID());
            addDishToTable(dish_type);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
