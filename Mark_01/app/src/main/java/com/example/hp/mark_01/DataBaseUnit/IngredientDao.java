package com.example.hp.mark_01.DataBaseUnit;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.hp.mark_01.DB_Project_Type.Ingredient_Type;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2016/8/29.
 */
public class IngredientDao {
    private Dao<Ingredient_Type, Integer> dao;

    public IngredientDao(Context context) {
        DishBaseHelper baseHelper = DishBaseHelper.getInstanse(context);

        try {
            dao = baseHelper.getDao(Ingredient_Type.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Ingredient_Type> findAllIngredient() {
        Log.i("采购单","读取");
        List<Ingredient_Type> ingredientlist = new ArrayList<>();
        try {
            ingredientlist = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredientlist;
    }

    public void addIngredientToTable(List<Ingredient_Type> ingredient_types) {
        Log.i("采购单","保存");
        try {
            for (Ingredient_Type ingredient : ingredient_types
                    ) {
                dao.createIfNotExists(ingredient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateIngredient(List<Ingredient_Type> ingredient_types){
        Log.i("采购单","更新");

        try {
            for (Ingredient_Type ingredient : ingredient_types
                    ) {
                dao.update(ingredient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeIngredient(Ingredient_Type ingredient){
        Log.i("采购单","删除");

        try {
            dao.delete(ingredient);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
