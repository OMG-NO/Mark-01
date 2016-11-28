package com.example.hp.mark_01.DataManages;

import android.content.Context;
import android.util.Log;

import com.example.hp.mark_01.Application.NewsApplication;
import com.example.hp.mark_01.DB_Project_Type.DB_Dish_Type;
import com.example.hp.mark_01.DB_Project_Type.Dish_evaluate_Type;
import com.example.hp.mark_01.DataBaseUnit.DishDao;
import com.example.hp.mark_01.DataBaseUnit.DishEvaluateDao;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2016/8/28.
 */
public class DataManages_Dish {
    public static SoftReference<List<DB_Dish_Type>> DISH_STYLELIST;

    public static SoftReference<List<DB_Dish_Type>> getDish_List() {
        if (DISH_STYLELIST == null) {
            Log.i("软引用数据", "数据为空,创建");
            return updataSoftData();
        } else {
            Log.i("软引用数据", "数据不为空");
            return DISH_STYLELIST;
        }
    }

    public static SoftReference<List<DB_Dish_Type>> updataSoftData() {
        Log.i("软引用数据", "数据更新");
        Context context = NewsApplication.getInstance();
        DishDao dishDao = new DishDao(context);

        List<DB_Dish_Type> dish_typeList = dishDao.findAllDish();
        return DISH_STYLELIST = new SoftReference<List<DB_Dish_Type>>(dish_typeList);
    }


    public static class Evaluate {
        public static List<Dish_evaluate_Type> evaluate_types;

        public static void getAllEvaluate() {
            evaluate_types = new ArrayList<>();
            Log.i("评论数据", "读取数据");
            DishEvaluateDao dishEvaluateDao = new DishEvaluateDao(NewsApplication.getInstance());
            evaluate_types = dishEvaluateDao.getAllEvaluate();
        }

        public static Dish_evaluate_Type getEvaluateById(int DishID) {
            if (evaluate_types != null) {
                for (Dish_evaluate_Type evaluate : evaluate_types
                        ) {
                    if (evaluate.getDishID() == DishID) {
                        return evaluate;
                    }
                }
            }
            return null;
        }

        public static void updateEvaluate(Dish_evaluate_Type Newevaluate) {
            if (evaluate_types != null) {

                for (int i = 0; i < evaluate_types.size(); i++) {
                    if (evaluate_types.get(i).getDishID() == Newevaluate.getDishID()) {
                        evaluate_types.remove(i);
                        evaluate_types.add(i, Newevaluate);
                        DishEvaluateDao dao = new DishEvaluateDao(NewsApplication.getInstance());
                        dao.updateEvaluate(Newevaluate);
                    }
                }

            }
        }
    }
}
