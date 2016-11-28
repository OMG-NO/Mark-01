package com.example.hp.mark_01.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.hp.mark_01.Adapter.Adapter_ExpandAbleList;
import com.example.hp.mark_01.Application.Constants;
import com.example.hp.mark_01.DB_Project_Type.DB_DishStyle_Type;
import com.example.hp.mark_01.DataBaseUnit.DishStyleDao;
import com.example.hp.mark_01.DataBaseUnit.ParentDishStyleDao;
import com.example.hp.mark_01.DB_Project_Type.Parent_DishStyle_Type;
import com.example.hp.mark_01.R;
import com.example.hp.mark_01.Util.CallBack;
import com.example.hp.mark_01.Util.OnSlidPanelItemClickListener;
import com.example.hp.mark_01.Util.StringUtil;
import com.example.hp.mark_01.Util.VolleyUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_SlidPanel extends Fragment {
    ExpandableListView listview;

    List<Parent_DishStyle_Type> parentstylelist;
    List<DB_DishStyle_Type> childstylelist;
    List<List<DB_DishStyle_Type>> dishstyleArraylist;


    DishStyleDao childDao;
    ParentDishStyleDao parentDao;

    OnSlidPanelItemClickListener ItemClickListener;

    public Fragment_SlidPanel() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("创建页面", "创建内容页面");
        View view = inflater.inflate(R.layout.fragment__slid_panel, null);
        childDao = new DishStyleDao(getContext());
        parentDao = new ParentDishStyleDao(getContext());

        listview = (ExpandableListView) view.findViewById(R.id.slid_expandablelist);
        initData();
        initListData();
        initExpandableList();

        return view;
    }


    private void initData() {
        parentstylelist = new ArrayList<>();
        childstylelist = new ArrayList<>();

        List<Parent_DishStyle_Type> dishlist = parentDao.findAllParentDishType();
        Log.i("数据长度", "数据库中数据长度  " + dishlist.size());
        if (dishlist.size() > 0) {
            parentstylelist.addAll(dishlist);
            childstylelist.addAll(childDao.findAllDishStyle());

        } else {
            downloadParentDishStyle();
        }
    }

    //准备数据阶段
    public void initListData() {
        parentstylelist = new ArrayList<>();
        childstylelist = new ArrayList<>();

        parentstylelist = parentDao.findAllParentDishType();
        childstylelist = childDao.findAllDishStyle();

        Log.i("数据长度", "parentstylelist  " + parentstylelist.size() + "\n" + "childstylelist   " + childstylelist.size());
        //因为菜谱子分类在数据库中是没有分类存储的,这里对子菜谱进行分类
        dishstyleArraylist = new ArrayList<>();
        for (int i = 0; i < parentstylelist.size(); i++) {
            List<DB_DishStyle_Type> dishstyleGroup = new ArrayList<>();
            int parentID = parentstylelist.get(i).getID();

            for (DB_DishStyle_Type dishstyle : childstylelist
                    ) {

                if (dishstyle.getParentid() == parentID) {
                    dishstyleGroup.add(dishstyle);
                }
            }
            Log.i("数据长度", "第 " + i + "分组数据的长度" + dishstyleGroup.size());
            dishstyleArraylist.add(dishstyleGroup);
        }
    }

    private void downloadParentDishStyle() {
        Log.i("网络请求", "下载菜单标签");
        String URL = "http://apis.juhe.cn/cook/category?key=" + Constants.key;         //将来提取出去,保存在统一位置

        VolleyUtil.get(URL)
                .setMethod(Request.Method.GET)
                .setCallBack(new ParentCallBack())
                .build()
                .start();
    }

    class ParentCallBack implements CallBack {

        @Override
        public void onSuccess(String response) {
            Log.i("网络请求", "网络请求成功 \n" + response);
            SavaDataAsyncTask save = new SavaDataAsyncTask();
            save.execute(response);
        }

        @Override
        public void onError(VolleyError error) {
            Log.i("网络请求", "下载菜单标签请求失败");
        }
    }

    //从父类菜谱分类列表中取出每一个子类菜谱分类列表,形成一个总的列表  超级大 上百个数据分类
    //然后保存
    public void saveDishStyleToTable(List<JSONObject> parentStyleJsonList) {
        List<DB_DishStyle_Type> allDishStyleList = new ArrayList<>();
        for (JSONObject parentStyleJson : parentStyleJsonList) {
            List<DB_DishStyle_Type> dishStylelist =
                    StringUtil.ParseDishStyle.getDishStyleList(parentStyleJson);
            allDishStyleList.addAll(dishStylelist);
        }
        for (DB_DishStyle_Type dishstyle : allDishStyleList
                ) {
            childDao.addDishTypeToTable(dishstyle);
            Log.i("数据处理", "childrendao保存完成");
        }
    }

    //这里的异步操作处理的是 将 接收到的json字符串解析完成之后返回 parentDishStyle 还有保存 父类分类标签 子类分类标签
    //本来网络操作就已经是异步了,然后请求完成之后,转入另一个 子线程操作.感觉会有个问题
    public class SavaDataAsyncTask extends AsyncTask<String, Integer, List<Parent_DishStyle_Type>> {

        @Override
        protected List<Parent_DishStyle_Type> doInBackground(String... strings) {
            List<JSONObject> parentstylejsonlist =
                    StringUtil.ParseDishStyleJson.parseJsonToParentDishStyle(strings[0]);
            Log.i("数据处理", "parentstylejsonlist处理成功");
            List<Parent_DishStyle_Type> parentstylelist =
                    StringUtil.ParseDishStyleJson.getParentDishStyleList(parentstylejsonlist);
            Log.i("数据处理", "parentstylelist处理成功");
            for (Parent_DishStyle_Type parentstyle : parentstylelist) {
                parentDao.addParentDishType(parentstyle);
                Log.i("数据处理", "parentstyle保存完成");
            }
            saveDishStyleToTable(parentstylejsonlist);
            return parentstylelist;
        }

        @Override
        protected void onPostExecute(List<Parent_DishStyle_Type> parent_dishStyle_types) {
            super.onPostExecute(parent_dishStyle_types);
            parentstylelist.addAll(parent_dishStyle_types);
        }
    }

    public void setOnSlidPanelItemClickListener(OnSlidPanelItemClickListener itemClickListener) {
        this.ItemClickListener = itemClickListener;
    }


    public void initExpandableList() {
        BaseExpandableListAdapter adapter = new Adapter_ExpandAbleList(dishstyleArraylist, parentstylelist, getContext());

        listview.setAdapter(adapter);

        listview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                Toast.makeText(getContext(), "点击发生" + i, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        listview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                DB_DishStyle_Type selectstyle = dishstyleArraylist.get(i).get(i1);
                String selectContent = selectstyle.getTitle();
                int selectid = selectstyle.getId();
                Log.i("点击事件", "点击发生   " + selectContent);
                ItemClickListener.onItemClickListener(selectid, selectContent, view);
                return false;
            }
        });
    }


}