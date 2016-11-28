package com.example.hp.mark_01.DB_Project_Type;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by hp on 2016/8/18.
 */
@DatabaseTable
public class Ingredient_Type {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String variety;
    @DatabaseField
    private String dosage;
    @DatabaseField
    private boolean isbuy=false;
    @DatabaseField
    private boolean isStress=false;

    public Ingredient_Type() {
    }

    public boolean isStress() {
        return isStress;
    }

    public void setStress(boolean stress) {
        isStress = stress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isbuy() {
        return isbuy;
    }

    public void setIsbuy(boolean isbuy) {
        this.isbuy = isbuy;
    }

    public Ingredient_Type(String variety, String dosage) {
        this.variety = variety;
        this.dosage = dosage;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
}
