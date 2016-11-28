package com.example.hp.mark_01.Text;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.hp.mark_01.Application.Constants;
import com.example.hp.mark_01.DB_Project_Type.DB_Dish_Type;
import com.example.hp.mark_01.DataBaseUnit.DishDao;
import com.example.hp.mark_01.R;
import com.example.hp.mark_01.Type.GetDishByNameURL;
import com.example.hp.mark_01.Util.CallBack;
import com.example.hp.mark_01.Util.StringUtil;
import com.example.hp.mark_01.Util.VolleyUtil;

import org.json.JSONObject;

import java.util.List;

public class Main2Activity extends AppCompatActivity {
    TextView show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        show= (TextView) findViewById(R.id.showtext);
    }
    public void startActivity(View view){
//        Intent intent=new Intent(this,Activity_Timer.class);
//        startActivity(intent);
        DishDao dao=new DishDao(this);
        List<DB_Dish_Type> dishlist=dao.findDishByTitle("辣子鸡");
       if (dishlist==null){
           Log.i("数据库数据","空");
       }else{
           StringBuilder builder=new StringBuilder();
           for (DB_Dish_Type dish:dishlist){
               builder.append(dish.getTitle()+"  \n");
           }
           show.setText(builder.toString());
       }
    }

    public void adddish(View view){

        downloadNewDish();
    }

    public void downloadNewDish(){
        GetDishByNameURL getDishByNameURL = new GetDishByNameURL();
        getDishByNameURL.setMenu("红烧肉");
        String URL = getDishByNameURL.getGETDishByNameURL();
        Log.i("数据请求", "数据请求的网址" + URL);

        VolleyUtil.get(URL)
                .setMethod(Request.Method.GET)
                .setCallBack(new MYCallBack())
                .build()
                .start();
    }

    public void downloaddishstyle(View view){
        VolleyUtil.get(Constants.QUERY_DISHSTYLE_URL)
                .setMethod(Request.Method.GET)
                .setCallBack(new CallBack() {
                    @Override
                    public void onSuccess(String response) {

                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                })
                .build()
                .start();
    }

    public class MYCallBack implements CallBack {

        @Override
        public void onSuccess(String response) {
            DishDao dao=new DishDao(Main2Activity.this);
            List<JSONObject> datajson=StringUtil.parserJson(response);
            for (int i=0;i<datajson.size();i++){
                DB_Dish_Type db_dish=StringUtil.parseJsonToDBDish(datajson.get(i));
                dao.addDishToTable(db_dish);
                Log.i("保存数据","保存的数据"+db_dish.getTitle());
            }

        }

        @Override
        public void onError(VolleyError error) {
            Toast.makeText(Main2Activity.this, "请求失败", Toast.LENGTH_SHORT).show();
        }
    }

}
