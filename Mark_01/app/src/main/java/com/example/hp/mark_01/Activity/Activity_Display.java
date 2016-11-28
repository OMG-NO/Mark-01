package com.example.hp.mark_01.Activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.hp.mark_01.Adapter.Adapter_RecycleAdapter;
import com.example.hp.mark_01.Type.GetDishByNameURL;
import com.example.hp.mark_01.Util.OnRecyclerItemClickListener;
import com.example.hp.mark_01.DB_Project_Type.DB_DishStyle_Type;
import com.example.hp.mark_01.DB_Project_Type.DB_Dish_Type;
import com.example.hp.mark_01.DataBaseUnit.DishDao;
import com.example.hp.mark_01.DataBaseUnit.DishStyleDao;
import com.example.hp.mark_01.R;
import com.example.hp.mark_01.Util.CallBack;
import com.example.hp.mark_01.Util.StringUtil;
import com.example.hp.mark_01.Util.VolleyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Activity_Display extends Activity {
    TextView search_content;
    ImageView show_dish_tag;
    RelativeLayout top_layout;

    Toolbar toolbar;
    ImageButton back_icon;
    TextView pageTitle;

    RecyclerView show_SearchDish;
    Adapter_RecycleAdapter adapter_showSearch;

    String content;

    private  String URL;
    private  String pn;     //数据返回起始下标

    DishDao dishdao;
    List<DB_Dish_Type> dbdishlist;

    DishStyleDao dishStyleDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__display);
        initView();         //绑定页面控件
        setToolbar();

        content = getIntent().getStringExtra("content");
        search_content.setText(content);
        setShow_dish_tag(content);

        dishdao=new DishDao(Activity_Display.this);
        dishStyleDao=new DishStyleDao(Activity_Display.this);

        initShowSearchDish();
    }

    //绑定页面控件
    public void initView(){
        search_content = (TextView) findViewById(R.id.search_content);
        show_dish_tag = (ImageView) findViewById(R.id.show_dish_tag);
        show_SearchDish = (RecyclerView) findViewById(R.id.show_searchdish);
        top_layout = (RelativeLayout) findViewById(R.id.top_layout);

        toolbar= (Toolbar) findViewById(R.id.unit_toolbar);
        back_icon= (ImageButton) findViewById(R.id.back_icon);
        pageTitle= (TextView) findViewById(R.id.page_title);
    }

    //设置标题的样式
    public void setToolbar(){
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    public void initShowSearchDish() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(Activity_Display.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        initData();
        adapter_showSearch = new Adapter_RecycleAdapter(dbdishlist, Activity_Display.this);

        show_SearchDish.setLayoutManager(layoutManager);
        show_SearchDish.setAdapter(adapter_showSearch);

        adapter_showSearch.setmItemClickLitener(new myRecyclerItemClickListener());

    }
    //页面启动准备数据
    public void initData() {
        dbdishlist=new ArrayList<>();

        List<DB_Dish_Type> dishfromTable=dishdao.findDishByTitle(content);
        Log.i("加载数据","加载数据长度"+dishfromTable.size());
        if (dishfromTable.size()>0){
            Log.i("加载数据","数据库加载");
            dbdishlist.addAll(dishfromTable);
        }else{
            Log.i("加载数据","数据库中数据为空,网络加载数据");
            downloadNewDish();
        }

        for (DB_Dish_Type db_dish:dbdishlist){
            getDishStyleToSave(db_dish);        //处理获取到的菜品,取得其菜品标签.然后将其标签存入标签数据库表
        }
    }

    public void downloadNewDish(){
        GetDishByNameURL getDishByNameURL = new GetDishByNameURL();
        getDishByNameURL.setMenu(content);
        URL = getDishByNameURL.getGETDishByNameURL();
        Log.i("数据请求", "数据请求的网址" + URL);

        VolleyUtil.get(URL)
                .setMethod(Request.Method.GET)
                .setCallBack(new MYCallBack())
                .build()
                .start();
    }

    //通过搜索内容,判断菜品类型的显示
    public void setShow_dish_tag(String content) {
        String tag = StringUtil.chectStringContainsSpecial(content);
        switch (tag) {   //"肉","鱼","菜","鸡"
            case "肉":
                show_dish_tag.setImageResource(R.drawable.meat);
                break;
            case "鱼":
                show_dish_tag.setImageResource(R.drawable.fish);
                break;
            case "菜":
                show_dish_tag.setImageResource(R.drawable.vegetable);
                break;
            case "鸡":
                show_dish_tag.setImageResource(R.drawable.chicken);
                break;
        }
    }

    public void getDishStyleToSave(DB_Dish_Type dish){
        DB_DishStyle_Type dishStyle=new DB_DishStyle_Type();
        dishStyle.setTitle(StringUtil.getDishFirstTag(dish.getTags()));
        try {
            dishStyle.setPicurl(StringUtil.getAlbumsString(new  JSONArray(dish.getAlbums())));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dishStyleDao.addDishTypeToTable(dishStyle);
    }

    public class MYCallBack implements CallBack {

        @Override
        public void onSuccess(String response) {
            List<JSONObject> datajson=StringUtil.parserJson(response);
            Log.i("请求数据","请求的数据长度"+datajson.size());
            for (int i=0;i<datajson.size();i++){
                DB_Dish_Type db_dish=StringUtil.parseJsonToDBDish(datajson.get(i));
                dbdishlist.add(db_dish);
                Log.i("保存数据","保存的数据"+db_dish.getTitle());
                adapter_showSearch.notifyDataSetChanged();
                dishdao.addDishToTable(db_dish);
            }
        }

        @Override
        public void onError(VolleyError error) {
            Toast.makeText(Activity_Display.this, "请求失败", Toast.LENGTH_SHORT).show();
        }
    }

    public class myRecyclerItemClickListener implements OnRecyclerItemClickListener{

        @Override
        public void onItemClick(View view, int position) {
            Toast.makeText(Activity_Display.this,dbdishlist.get(position).getTitle(),Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(Activity_Display.this,Activity_Dish.class);
            Bundle bundle=new Bundle();
            bundle.putParcelable("dishdata",dbdishlist.get(position));
            intent.putExtra("dishdata",bundle);
            startActivity(intent);
        }
    }
}
