package com.example.hp.mark_01.DB_Project_Type;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by hp on 2016/8/29.
 */
@DatabaseTable
public class Dish_evaluate_Type {
    @DatabaseField(id = true)
    private int DishID;
    @DatabaseField
    private String evaluate;
    @DatabaseField
    private int score;
    @DatabaseField
    private String time;
    @DatabaseField
    private String lacation;

    public Dish_evaluate_Type() {
    }

    public int getDishID() {
        return DishID;
    }

    public void setDishID(int dishID) {
        DishID = dishID;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLacation() {
        return lacation;
    }

    public void setLacation(String lacation) {
        this.lacation = lacation;
    }
}
