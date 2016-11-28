package com.example.hp.mark_01;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;


import com.example.hp.mark_01.Activity.Activity_Collection;
import com.example.hp.mark_01.Activity.Activity_purchase;
import com.example.hp.mark_01.Fragment.MainContent_Fragment;
import com.example.hp.mark_01.Fragment.MainSide_Fragment;
import com.example.hp.mark_01.Fragment.PurchaseFragment;

import java.lang.ref.SoftReference;

public class MainActivity extends AppCompatActivity{
    SoftReference<MainSide_Fragment> sideFragment;

    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    Fragment searchFragment,purchaseFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragment();
        listenFragmentItemClick();

        SlidingPaneLayout slidingPaneLayout= (SlidingPaneLayout) findViewById(R.id.main_sidebar);
        if (slidingPaneLayout!=null){
            slidingPaneLayout.setCoveredFadeColor(getResources().getColor(R.color.Mainside_Covered));
            slidingPaneLayout.setPanelSlideListener(new MyslidingPanelListenter());
        }

    }


    private void initView(){
        fragmentManager=getSupportFragmentManager();
        MainSide_Fragment fragment= (MainSide_Fragment) fragmentManager.findFragmentById(R.id.sidefragment);
        sideFragment=new SoftReference<MainSide_Fragment>(fragment);
    }

    private void initFragment(){
        searchFragment=new MainContent_Fragment();
        purchaseFragment=new PurchaseFragment();

        transaction=fragmentManager.beginTransaction();
    }

    private void listenFragmentItemClick(){
        sideFragment.get().setOnImageClickListener(new MyImageViewClickListener());
    }

    class MyImageViewClickListener implements MainSide_Fragment.OnImageClickListener{

        @Override
        public void imageClickListener(int position) {
            Log.i("侧边栏页面跳转","position"+position);
            switch (position){
                case 1:
                    Intent ToCollect=new Intent(MainActivity.this, Activity_Collection.class);
                    startActivity(ToCollect);
                    break;
                case 3:
                    Intent ToPurchase=new Intent(MainActivity.this, Activity_purchase.class);
                    startActivity(ToPurchase);
                    break;

            }
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
