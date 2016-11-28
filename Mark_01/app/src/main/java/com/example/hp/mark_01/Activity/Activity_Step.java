package com.example.hp.mark_01.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.hp.mark_01.Adapter.PagerAdapter_Step;
import com.example.hp.mark_01.DB_Project_Type.DB_Dish_Type;
import com.example.hp.mark_01.Fragment.Fragment_steppage;
import com.example.hp.mark_01.R;
import com.example.hp.mark_01.Type.Steps_Type;
import com.example.hp.mark_01.Util.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Activity_Step extends AppCompatActivity {
    TextView title;
    TabLayout step_tab;
    ViewPager step_viewpager;

    String titleString;

    List<String> stepTabTitle;
    List<Steps_Type> stepsDataList;
    List<Fragment> stepFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__step);
        initView();

        initStepData();
        initTabTitle();
        initFragmentPager();
        initFragmentViewPager();
    }

    private void initFragmentViewPager() {
        Log.i("页面数量","stepFragmentList长度"+stepFragmentList.size()+"");
        PagerAdapter adapter_fragmentStep = new PagerAdapter_Step(getSupportFragmentManager(), stepFragmentList, stepTabTitle);

        step_viewpager.setAdapter(adapter_fragmentStep);
        step_tab.setupWithViewPager(step_viewpager);
    }

    private void initView() {
        title = (TextView) findViewById(R.id.title);
        step_tab = (TabLayout) findViewById(R.id.step_tab);
        step_viewpager = (ViewPager) findViewById(R.id.step_viewpager);
    }

    //取得从 Acitvity_Dish 传来的数据
    private void initStepData() {
        stepsDataList = new ArrayList<>();
        DB_Dish_Type DB_dish = getIntent().getExtras().getParcelable("dishdata");
        if (DB_dish != null) {
            titleString = DB_dish.getTitle();
            try {
                stepsDataList.addAll(StringUtil.getStepsList(new JSONArray(DB_dish.getSteps())));
                Log.i("数据长度","stepsDataList长度"+stepsDataList.size()+"");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            title.setText(DB_dish.getTitle());
        }
    }

    private void initTabTitle() {
        stepTabTitle=new ArrayList<>();
        for (Steps_Type db_dish : stepsDataList) {
            stepTabTitle.add(db_dish.getStep());
        }
    }

    private void initFragmentPager() {
        stepFragmentList = new ArrayList<>();
        for (Steps_Type db_dish : stepsDataList) {
            Fragment steppage = new Fragment_steppage();
            Bundle bundle = new Bundle();
            bundle.putParcelable("step", db_dish);
            steppage.setArguments(bundle);

            stepFragmentList.add(steppage);
        }
    }

}
