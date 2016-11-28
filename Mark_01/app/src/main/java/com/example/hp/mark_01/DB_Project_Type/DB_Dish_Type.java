package com.example.hp.mark_01.DB_Project_Type;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by hp on 2016/8/22.
 */
@DatabaseTable(tableName = "dish")
public class DB_Dish_Type implements Parcelable {
    @DatabaseField(id = true)
    private int ID;
    @DatabaseField
    private String title;
    @DatabaseField
    private String tags;
    @DatabaseField
    private String imtro;

    @DatabaseField
    private String ingredient;
    @DatabaseField
    private String burden;
    @DatabaseField
    private String albums;
    @DatabaseField
    private String steps;

    @DatabaseField
    private boolean isCollect;

    public DB_Dish_Type() {
    }

    public DB_Dish_Type(int ID, String title, String tags,
                        String imtro, String ingredient, String burden, String albums, String steps) {
        this.ID = ID;
        this.title = title;
        this.tags = tags;
        this.imtro = imtro;
        this.ingredient = ingredient;
        this.burden = burden;
        this.albums = albums;
        this.steps = steps;
    }

    protected DB_Dish_Type(Parcel in) {
        ID = in.readInt();
        title = in.readString();
        tags = in.readString();
        imtro = in.readString();
        ingredient = in.readString();
        burden = in.readString();
        albums = in.readString();
        steps = in.readString();
        isCollect = in.readByte() != 0;
    }

    public static final Creator<DB_Dish_Type> CREATOR = new Creator<DB_Dish_Type>() {
        @Override
        public DB_Dish_Type createFromParcel(Parcel in) {
            return new DB_Dish_Type(in);
        }

        @Override
        public DB_Dish_Type[] newArray(int size) {
            return new DB_Dish_Type[size];
        }
    };

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

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getBurden() {
        return burden;
    }

    public void setBurden(String burden) {
        this.burden = burden;
    }

    public String getAlbums() {
        return albums;
    }

    public void setAlbums(String albums) {
        this.albums = albums;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(title);
        dest.writeString(tags);
        dest.writeString(imtro);
        dest.writeString(ingredient);
        dest.writeString(burden);
        dest.writeString(albums);
        dest.writeString(steps);
        dest.writeByte((byte) (isCollect ? 1 : 0));
    }
}
