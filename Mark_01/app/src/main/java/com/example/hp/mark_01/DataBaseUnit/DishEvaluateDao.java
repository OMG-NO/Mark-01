package com.example.hp.mark_01.DataBaseUnit;

import android.content.Context;
import android.util.Log;

import com.example.hp.mark_01.DB_Project_Type.Dish_evaluate_Type;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2016/9/2.
 */
public class DishEvaluateDao {
    private Dao<Dish_evaluate_Type, Integer> dao;

    public DishEvaluateDao(Context context) {
        DishBaseHelper baseHelper = DishBaseHelper.getInstanse(context);

        try {
            dao = baseHelper.getDao(Dish_evaluate_Type.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addEvaluateToTable(Dish_evaluate_Type evaluate_type) {
        Log.i("评论表", "保存评论" + evaluate_type.getDishID());
        try {
            dao.createIfNotExists(evaluate_type);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Dish_evaluate_Type> getAllEvaluate(){
        List<Dish_evaluate_Type> evaluate_types=new ArrayList<>();
        try {
            evaluate_types=dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return evaluate_types;
    }

    public Dish_evaluate_Type getEvaluateByID(int dishID) {
        Dish_evaluate_Type evaluate_type = null;
        try {
            evaluate_type = dao.queryForId(dishID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return evaluate_type;
    }

    public void updateEvaluate(Dish_evaluate_Type evaluate_type){
        try {
            dao.deleteById(evaluate_type.getDishID());
            dao.create(evaluate_type);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
