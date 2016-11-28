package com.example.hp.mark_01.Type;

import com.example.hp.mark_01.DB_Project_Type.Ingredient_Type;

import java.util.List;

/**
 * Created by hp on 2016/8/18.
 */
public class Dish_Type {
    private int ID;
    private String title;
    private String tags;
    private String imtro;
    private List<Ingredient_Type> ingredient;
    private List<Ingredient_Type> burden;
    private String albums;
    private List<Steps_Type> steps;

    public Dish_Type(int ID, String title, String tags, String imtro) {
        this.ID = ID;
        this.title = title;
        this.tags = tags;
        this.imtro = imtro;
    }

    public Dish_Type() {
    }

    public List<Steps_Type> getSteps() {
        return steps;
    }

    public void setSteps(List<Steps_Type> steps) {
        this.steps = steps;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getImtro() {
        return imtro;
    }

    public void setImtro(String imtro) {
        this.imtro = imtro;
    }

    public List<Ingredient_Type> getIngredient() {
        return ingredient;
    }

    public void setIngredient(List<Ingredient_Type> ingredient) {
        this.ingredient = ingredient;
    }

    public List<Ingredient_Type> getBurden() {
        return burden;
    }

    public void setBurden(List<Ingredient_Type> burden) {
        this.burden = burden;
    }

    public String getAlbums() {
        return albums;
    }

    public void setAlbums(String albums) {
        this.albums = albums;
    }
}
