package com.example.hp.mark_01.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.hp.mark_01.DataBaseUnit.DishStyleDao;
import com.example.hp.mark_01.DataBaseUnit.ParentDishStyleDao;
import com.example.hp.mark_01.DB_Project_Type.Parent_DishStyle_Type;
import com.example.hp.mark_01.R;

import java.util.List;

/**
 * Created by hp on 2016/8/26.
 */
public class Activity_Panel extends AppCompatActivity {
    List<Parent_DishStyle_Type> parent_dishStylelist;
    ParentDishStyleDao parentdao;
    DishStyleDao childrendao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);

        parentdao=new ParentDishStyleDao(Activity_Panel.this);
        childrendao=new DishStyleDao(Activity_Panel.this);

        SlidingPaneLayout slidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.panel);

        if (slidingPaneLayout != null) {
            slidingPaneLayout.setCoveredFadeColor(Color.rgb(0,0,0));
            slidingPaneLayout.setPanelSlideListener(new MyslidingPanelListenter());
        }
    }


    class MyslidingPanelListenter implements SlidingPaneLayout.PanelSlideListener {

        @Override
        public void onPanelSlide(View panel, float slideOffset) {

        }

        @Override
        public void onPanelOpened(View panel) {
            Log.i("侧边栏事件","侧边栏打开");
        }

        @Override
        public void onPanelClosed(View panel) {

        }
    }
}
