package com.example.hp.mark_01.DataBaseUnit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.hp.mark_01.DB_Project_Type.DB_DishStyle_Type;
import com.example.hp.mark_01.DB_Project_Type.DB_Dish_Type;
import com.example.hp.mark_01.DB_Project_Type.Dish_evaluate_Type;
import com.example.hp.mark_01.DB_Project_Type.Ingredient_Type;
import com.example.hp.mark_01.DB_Project_Type.Parent_DishStyle_Type;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by hp on 2016/8/22.
 */
public class DishBaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DB_NAME = "DISH.db";    //数据库名称
    private static final int DB_VISION = 3;             //数据库版本号

    public DishBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VISION);
    }

    private static DishBaseHelper dishBaseHelper;

    public synchronized static DishBaseHelper getInstanse(Context context) {
        if (dishBaseHelper == null) {
            dishBaseHelper = new DishBaseHelper(context);
        }
        return dishBaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            //保存所有菜品
            TableUtils.createTableIfNotExists(connectionSource, DB_Dish_Type.class);
            //保存菜品的分类
            TableUtils.createTableIfNotExists(connectionSource, DB_DishStyle_Type.class);
            //保存菜品的父类分类
            TableUtils.createTableIfNotExists(connectionSource, Parent_DishStyle_Type.class);
            //保存菜品的用料信息,用来制作采购单
            TableUtils.createTableIfNotExists(connectionSource, Ingredient_Type.class);
            //保存菜品的评价信息
            TableUtils.createTableIfNotExists(connectionSource, Dish_evaluate_Type.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
//            TableUtils.dropTable(connectionSource, DB_Dish_Type.class, true);
            TableUtils.dropTable(connectionSource, DB_DishStyle_Type.class, true);
            TableUtils.dropTable(connectionSource, Parent_DishStyle_Type.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
