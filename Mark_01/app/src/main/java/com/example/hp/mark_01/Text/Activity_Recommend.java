package com.example.hp.mark_01.Text;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.hp.mark_01.Adapter.Adapter_GridRecycle;
import com.example.hp.mark_01.Application.Constants;
import com.example.hp.mark_01.DB_Project_Type.DB_DishStyle_Type;
import com.example.hp.mark_01.DataBaseUnit.DishStyleDao;
import com.example.hp.mark_01.DataBaseUnit.ParentDishStyleDao;
import com.example.hp.mark_01.R;
import com.example.hp.mark_01.DB_Project_Type.Parent_DishStyle_Type;
import com.example.hp.mark_01.Util.CallBack;
import com.example.hp.mark_01.Util.OnRecyclerItemClickListener;
import com.example.hp.mark_01.Util.StringUtil;
import com.example.hp.mark_01.Util.VolleyUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Activity_Recommend extends AppCompatActivity {
    RecyclerView rec_recycle;

    ParentDishStyleDao parentdao;
    DishStyleDao childrendao;
    List<Parent_DishStyle_Type> parent_dishStylelist;

    Adapter_GridRecycle adapter_gridRecycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__recommend);
        parentdao = new ParentDishStyleDao(Activity_Recommend.this);
        childrendao = new DishStyleDao(Activity_Recommend.this);
        initView();

        initData();
        setRec_recycle();


    }

    private void initView() {
        rec_recycle = (RecyclerView) findViewById(R.id.recommend_recycler);
    }

    private void setRec_recycle() {
        GridLayoutManager layoutManager = new GridLayoutManager(Activity_Recommend.this, 3);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter_gridRecycle = new Adapter_GridRecycle(parent_dishStylelist, Activity_Recommend.this);

        rec_recycle.setLayoutManager(layoutManager);
        rec_recycle.setAdapter(adapter_gridRecycle);

        adapter_gridRecycle.setOnItemClicklistener(new MyItemClickListener());
    }

    private void initData() {
        parent_dishStylelist = new ArrayList<>();

        List<Parent_DishStyle_Type> dishlist = parentdao.findAllParentDishType();
        Log.i("数据长度", "数据库中数据长度  " + dishlist.size());
        if (dishlist.size() > 0) {
            parent_dishStylelist.addAll(dishlist);
        } else {
            downloadParentDishStyle();
        }
    }

//数据处理部分

    private void downloadParentDishStyle() {
        Log.i("网络请求", "下载菜单标签");
        String URL = "http://apis.juhe.cn/cook/category?key=" + Constants.key;         //将来提取出去,保存在统一位置

        VolleyUtil.get(URL)
                .setMethod(Request.Method.GET)
                .setCallBack(new ParentCallBack())
                .build()
                .start();
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
                parentdao.addParentDishType(parentstyle);
                Log.i("数据处理", "parentstyle保存完成");
            }
            saveDishStyleToTable(parentstylejsonlist);
            return parentstylelist;
        }

        @Override
        protected void onPostExecute(List<Parent_DishStyle_Type> parent_dishStyle_types) {
            super.onPostExecute(parent_dishStyle_types);
            parent_dishStylelist.addAll(parent_dishStyle_types);
            adapter_gridRecycle.notifyDataSetChanged();
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
            childrendao.addDishTypeToTable(dishstyle);
            Log.i("数据处理", "childrendao保存完成");
        }
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
//数据处理部分结束

    private class MyItemClickListener implements OnRecyclerItemClickListener {

        @Override
        public void onItemClick(View view, int position) {
            PopupWindow popup = new PopupWindow(Activity_Recommend.this);
            Toast.makeText(Activity_Recommend.this, "点击发生" + position, Toast.LENGTH_SHORT).show();

        }
    }

    private void initPopupData() {
        String[] array = {"1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111",};
        View view = LayoutInflater.from(Activity_Recommend.this).inflate(R.layout.unit_popup, null);
        ListView menu_list = (ListView) view.findViewById(R.id.menu_list);
    }
}
