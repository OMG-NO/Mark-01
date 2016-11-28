package com.example.hp.mark_01.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.hp.mark_01.Activity.Activity_Dish;
import com.example.hp.mark_01.Adapter.Adapter_RecycleAdapter;
import com.example.hp.mark_01.DB_Project_Type.DB_Dish_Type;
import com.example.hp.mark_01.DataBaseUnit.DishDao;
import com.example.hp.mark_01.R;
import com.example.hp.mark_01.Type.GetDishByStyleURL;
import com.example.hp.mark_01.Util.CallBack;
import com.example.hp.mark_01.Util.OnRecyclerItemClickListener;
import com.example.hp.mark_01.Util.OnSlidPanelItemClickListener;
import com.example.hp.mark_01.Util.StringUtil;
import com.example.hp.mark_01.Util.VolleyUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Fragment_ContentPanel extends Fragment {
    RecyclerView recycle_menu;

    int selectID = 0;
    String selectcontent = null;
    DishDao dao;

    String URL;
    List<DB_Dish_Type> dishlist;
    Adapter_RecycleAdapter adapter_recycle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("创建页面", "创建内容页面");
        View view = inflater.inflate(R.layout.fragment__content_panel, container, false);
        recycle_menu = (RecyclerView) view.findViewById(R.id.contentPanel_recycler);
        dao = new DishDao(getContext());

        setSlidItemClick();
        return view;
    }

    //页面开始时,进行数据的加载
    @Override
    public void onStart() {
        super.onStart();
        initData();
        initRecycleMenu();
    }

    public void initData() {
        if (selectID == 0) {
            dishlist = new ArrayList<>();
            DishDao dao = new DishDao(getContext());
            dishlist = dao.findAllDish();
        } else {
            dishlist.clear();       //清空列表 准备添加新的数据
            dishlist.addAll(dao.findDishByStyle(selectcontent));
            if (dishlist.size() == 0) {
                downloadNewStyleDish();
            }
        }
    }

    public void downloadNewStyleDish() {
        GetDishByStyleURL getDishByStyleURL = new GetDishByStyleURL();
        getDishByStyleURL.setCid(selectID);
        URL = getDishByStyleURL.getGETDishByNameURL();
        VolleyUtil.get(URL)
                .setMethod(Request.Method.GET)
                .setCallBack(new MyCallBack())
                .build()
                .start();
    }

    public void initRecycleMenu() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle_menu.setLayoutManager(layoutManager);

        adapter_recycle = new Adapter_RecycleAdapter(dishlist, getContext());
        recycle_menu.setAdapter(adapter_recycle);

        adapter_recycle.setmItemClickLitener(new RecyclerItemClickLitener());

    }

    private void setSlidItemClick() {
        Fragment_SlidPanel sidefragment =
                (Fragment_SlidPanel) getFragmentManager().findFragmentById(R.id.sidefragment);
        sidefragment.setOnSlidPanelItemClickListener(new MyOnSideItemClickListener());
    }

    class MyOnSideItemClickListener implements OnSlidPanelItemClickListener {

        @Override
        public void onItemClickListener(int selectContentID, String selectContent, View viwe) {
            selectID = selectContentID;
            selectcontent = selectContent;
            initData();
        }
    }

    class RecyclerItemClickLitener implements OnRecyclerItemClickListener {

        @Override
        public void onItemClick(View view, int position) {
            Toast.makeText(getContext(), dishlist.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), Activity_Dish.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("dishdata", dishlist.get(position));
            intent.putExtra("dishdata", bundle);
            startActivity(intent);
        }
    }

    class MyCallBack implements CallBack {

        @Override
        public void onSuccess(String response) {
            List<JSONObject> datajson = StringUtil.parserJson(response);
            Log.i("请求数据", "请求的数据长度" + datajson.size());
            for (int i = 0; i < datajson.size(); i++) {
                DB_Dish_Type db_dish = StringUtil.parseJsonToDBDish(datajson.get(i));
                dishlist.add(db_dish);
                Log.i("保存数据", "保存的数据" + db_dish.getTitle());
                dao.addDishToTable(db_dish);
                Log.i("列表更新", "下一步执行更新");
                adapter_recycle.notifyDataSetChanged();
            }
        }

        @Override
        public void onError(VolleyError error) {
            Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
        }
    }
}
