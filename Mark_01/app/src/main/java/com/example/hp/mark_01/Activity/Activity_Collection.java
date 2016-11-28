package com.example.hp.mark_01.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hp.mark_01.Adapter.Adapter_RecycleAdapter;
import com.example.hp.mark_01.DB_Project_Type.DB_Dish_Type;
import com.example.hp.mark_01.DataManages.DataManages_Dish;
import com.example.hp.mark_01.R;
import com.example.hp.mark_01.Type.Dish_Type;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class Activity_Collection extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recycle_collection;
    List<DB_Dish_Type> collectedDish;

    Adapter_RecycleAdapter adapter_recycleAdapter;

    ImageButton back_icon;
    TextView page_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__collection);
        initView();
        initData();

        setAdapter_recycleAdapter();
        setToolbarandSystemBar();
    }

    private void initView(){
        toolbar= (Toolbar) findViewById(R.id.unit_toolbar);
        recycle_collection= (RecyclerView) findViewById(R.id.recycle_collect);

        back_icon= (ImageButton) findViewById(R.id.back_icon);
        page_title= (TextView) findViewById(R.id.page_title);
    }

    private void initData(){
        collectedDish=new ArrayList<>();
        List<DB_Dish_Type> dish_types=new ArrayList<>();
        dish_types=DataManages_Dish.getDish_List().get();
        for (DB_Dish_Type dish: dish_types
             ) {
            if (dish.isCollect()){
                collectedDish.add(dish);
            }
        }

    }

    private void setAdapter_recycleAdapter(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter_recycleAdapter=new Adapter_RecycleAdapter(collectedDish,Activity_Collection.this);

        recycle_collection.setLayoutManager(layoutManager);
        recycle_collection.setAdapter(adapter_recycleAdapter);
    }

    //设置标题 和状态栏的样式
    public void setToolbarandSystemBar(){
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.bringToFront();
        toolbar.setBackground(getDrawable(R.drawable.transparent_bg));
        page_title.setText("我的收藏");
    }
}
