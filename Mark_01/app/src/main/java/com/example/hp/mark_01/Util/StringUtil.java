package com.example.hp.mark_01.Util;

import com.example.hp.mark_01.Application.Constants;
import com.example.hp.mark_01.DB_Project_Type.DB_DishStyle_Type;
import com.example.hp.mark_01.DB_Project_Type.DB_Dish_Type;
import com.example.hp.mark_01.DB_Project_Type.Parent_DishStyle_Type;
import com.example.hp.mark_01.Type.Dish_Type;
import com.example.hp.mark_01.DB_Project_Type.Ingredient_Type;
import com.example.hp.mark_01.Type.Steps_Type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2016/8/18.
 */
//字符串操作
public class StringUtil {
    //返回搜索标题处小图标的样式  鱼 肉 菜 禽
    public static String chectStringContainsSpecial(String willChectString) {

        for (String specialString : Constants.Dish.DISH_TAGS) {
            int index = willChectString.indexOf(specialString);
            if (index != -1) {
                return specialString;
            }
        }
        return "普通";
    }

    //处理网络请求返回的 data Json字符串
    public static List<JSONObject> parserJson(String content) {
        List<JSONObject> dishlist = new ArrayList<>();

        try {

            JSONObject resultjson = new JSONObject(content);
            int resultcode = resultjson.getInt("resultcode");
            String reason = resultjson.getString("reason");
            JSONObject result = resultjson.getJSONObject("result");

            JSONArray data = result.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                JSONObject dishJson = data.getJSONObject(i);
                dishlist.add(dishJson);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dishlist;
    }

    //因为在向数据库中保存的时候,数据是保存的最后的  data部分.所以要把处理 data部分的方法单独拿出来
    public static Dish_Type parseDataJsonToDish(JSONObject dataJson) throws JSONException {
        Dish_Type dish = new Dish_Type(
                dataJson.getInt("id"),
                dataJson.getString("title"),
                dataJson.getString("tags"),
                dataJson.getString("imtro")
        );

        dish.setAlbums(getAlbumsString(dataJson.getJSONArray("albums")));
        dish.setIngredient(getIngredientsList(dataJson.getString("ingredients")));
        dish.setBurden(getIngredientsList(dataJson.getString("burden")));
        dish.setSteps(getStepsList(dataJson.getJSONArray("steps")));

        return dish;
    }

    //将传入的 dataJson 对象转换成数据库存储的类型
    public static DB_Dish_Type parseJsonToDBDish(JSONObject dataJson) {
        DB_Dish_Type db_dish = null;
        try {
            db_dish = new DB_Dish_Type(
                    dataJson.getInt("id"),
                    dataJson.getString("title"),
                    dataJson.getString("tags"),
                    dataJson.getString("imtro"),
                    dataJson.getString("ingredients"),
                    dataJson.getString("burden"),
                    dataJson.getJSONArray("albums").toString(),
                    dataJson.getJSONArray("steps").toString()

            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return db_dish;
    }

    //原料列表形式
    public static List<Ingredient_Type> getIngredientsList(String ingredients) {
        String[] ingredientsArray = ingredients.split(";");
        List<Ingredient_Type> ingredientList = new ArrayList<>();
        for (int i = 0; i < ingredientsArray.length; i++) {
            String[] content = ingredientsArray[i].split(",");
            Ingredient_Type ingredient = new Ingredient_Type(
                    content[0],
                    content[1]
            );
            ingredientList.add(ingredient);
        }
        return ingredientList;
    }

    //步骤列表形式
    public static List<Steps_Type> getStepsList(JSONArray steps) {
        List<Steps_Type> stepsList = new ArrayList<>();
        try {
            for (int i = 0; i < steps.length(); i++) {
                JSONObject stepJson = steps.getJSONObject(i);
                Steps_Type step = new Steps_Type(
                        stepJson.getString("img"),
                        stepJson.getString("step")
                );
                stepsList.add(step);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stepsList;
    }

    //取得标题图片
    public static String getAlbumsString(JSONArray albums) {
        String albumsUrl = null;
        try {
            albumsUrl = albums.getString(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return albumsUrl;
    }

    public static String getDishFirstTag(String tags) {
        String[] tagsArray = tags.split(",");
        return tagsArray[0];
    }
    //处理Json字符串


    public static class ParseDishStyleJson {

        public static List<JSONObject> parseJsonToParentDishStyle(String JsonString) {
            List<JSONObject> parentStyleJsonList = new ArrayList<>();
            try {
                JSONObject response = new JSONObject(JsonString);
                int resultcode = response.getInt("resultcode");
                String reason = response.getString("reason");
                JSONArray result = response.getJSONArray("result");

                for (int i = 0; i < result.length(); i++) {
                    parentStyleJsonList.add(result.getJSONObject(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return parentStyleJsonList;
        }

        /**
         * 返回父类的菜品分类
         *
         * @param parentStyleJsonList 列表
         * @return
         */
        public static List<Parent_DishStyle_Type> getParentDishStyleList(List<JSONObject> parentStyleJsonList) {
            List<Parent_DishStyle_Type> dishStyleList = new ArrayList<>();
            try {
                for (JSONObject parentDishJson : parentStyleJsonList) {
                    //int ID, String name
                    Parent_DishStyle_Type parentStyle = new Parent_DishStyle_Type(
                            parentDishJson.getInt("parentId"),
                            parentDishJson.getString("name")
                    );
                    dishStyleList.add(parentStyle);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return dishStyleList;
        }
    }

    public static class ParseDishStyle {
        /**
         * 取得每一个父类菜谱分类中的子类
         *
         * @param parentStyle
         * @return
         */
        public static List<DB_DishStyle_Type> getDishStyleList(JSONObject parentStyle) {
            List<DB_DishStyle_Type> stylelist = new ArrayList<>();
            try {
                JSONArray list = parentStyle.getJSONArray("list");
                for (int i = 0; i < list.length(); i++) {
                    JSONObject dishstyleJson = list.getJSONObject(i);
                    //int id, String title, String picurl, int parentid
                    DB_DishStyle_Type dishstyle = new DB_DishStyle_Type(
                            dishstyleJson.getInt("id"),
                            dishstyleJson.getString("name"),
                            dishstyleJson.getInt("parentId")
                    );
                    stylelist.add(dishstyle);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return stylelist;
        }
    }

}
