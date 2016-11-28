package com.example.hp.mark_01.Fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.hp.mark_01.Activity.Activity_Display;
import com.example.hp.mark_01.Activity.Activity_Panel;
import com.example.hp.mark_01.Activity.Activity_Select;
import com.example.hp.mark_01.Application.Constants;
import com.example.hp.mark_01.MainActivity;
import com.example.hp.mark_01.R;
import com.example.hp.mark_01.Util.CallBack;
import com.example.hp.mark_01.Util.VolleyUtil;
import com.example.hp.mark_01.Util.WeatherControl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * Created by hp on 2016/8/28.
 */
public class MainContent_Fragment extends Fragment {
    View view;

    public EditText content;
    public ImageButton search_food;
    LinearLayout linear;
    FrameLayout search_fram;

    TextView city;
    TextView date;
    TextView time;
    ImageView weather;

    int width, height;

    int search_foodY;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_main,null);

        initView();
        getWindowSize();
        linear.post(new Runnable() {
            @Override
            public void run() {
                getsearch_foodY();
            }
        });
        search_food.setOnClickListener(new SearchClickListener());
        setEditTextFocus();

        mainLoaction();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        RelativeLayout maincontent = (RelativeLayout) view.findViewById(R.id.maincontent_page);
//        maincontent.setBackground(getBackground());
        startEnterAnim();
    }

    public Drawable getBackground(){
        Random random=new Random();
        int backgroundcount=random.nextInt(2);
        Drawable background = null;
        if (backgroundcount==0){
            background=getResources().getDrawable(R.drawable.background_1);
        }else if (backgroundcount==1){
            background=getResources().getDrawable(R.drawable.background_2);
        }else if (backgroundcount==2){
            background=getResources().getDrawable(R.drawable.background_3);
        }
        return background;
    }

    private void initView(){
        content = (EditText) view.findViewById(R.id.search_content);
        search_fram= (FrameLayout) view.findViewById(R.id.search_fram);
        search_food = (ImageButton) view.findViewById(R.id.search_food);
        linear = (LinearLayout) view.findViewById(R.id.linear);
    }

    public void setEditTextFocus(){
        content.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    search_fram.setBackgroundColor(getResources().getColor(R.color.Edit_Opaque_background));
                }
            }
        });
    }


    public class SearchClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String search_content = content.getText().toString();
            Log.i("搜索内容", search_content + "内容");
            if (search_content.equals("")) {
//                Intent intent = new Intent(getContext(), Activity_Select.class);
                Intent intent = new Intent(getContext(), Activity_Panel.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getContext(), Activity_Display.class);
                intent.putExtra("content", search_content);
                startActivity(intent);
            }
            startExitAnim();
        }
    }

    //取得手机的高度和宽度(像素)
    public void getWindowSize() {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;

        Log.i("屏幕尺寸", "宽高:" + width + "  " + height);
    }

    private void startExitAnim() {
        if (search_foodY > 500) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(linear, "translationY", 0, 100, -(search_foodY + 130));
            animator.setDuration(500).start();
        }
    }

    private void startEnterAnim(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(linear, "translationY", -(search_foodY + 130), 100, 0);
        animator.setDuration(500).start();
    }

    public void getsearch_foodY() {
        int[] location = new int[2];
        content.getLocationOnScreen(location);
        search_foodY = location[1];
        Log.i("控件位置", "search_foodY  " + search_foodY);
    }


    private void setWeather(){
        city= (TextView) view.findViewById(R.id.city);
        date= (TextView) view.findViewById(R.id.date);
        time= (TextView) view.findViewById(R.id.time);
        weather= (ImageView) view.findViewById(R.id.weather);
        if (Constants.LOCATION_POSITION_CITY!=null) {

            VolleyUtil.get(Constants.WEATHER_URL + "?cityname="+"烟台")
                    .setMethod(Request.Method.GET)
                    .setCallBack(new WeatherCallBack())
                    .build()
                    .addRequestHeader("apikey", Constants.BAIDU_APIKEY)
                    .start();
        }
    }
    //页面的定位服务
    AMapLocationClient locationClient;
    AMapLocationClientOption clientOption;


    public void mainLoaction() {
        locationClient = new AMapLocationClient(getContext());
        clientOption = new AMapLocationClientOption();

        locationClient.setLocationOption(clientOption);
        locationClient.startLocation();
        locationClient.setLocationListener(new mLocationListener());
    }

    public void parseLoactionInfo(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
            Log.i("定位信息", "定位地址   " + aMapLocation.toString());

            String city = aMapLocation.getCity();
            Log.i("定位信息", "定位地址    城市: " + city);
            Constants.LOCATION_POSITION_CITY=city;

            setWeather();
            locationClient.onDestroy();
        }

        if (aMapLocation != null && aMapLocation.getErrorCode() != 0) {
            Log.i("定位信息", "定位错误  " + aMapLocation.getErrorCode() + "\n"
                    + aMapLocation.getErrorInfo());
        }
    }

    class mLocationListener implements AMapLocationListener {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            parseLoactionInfo(aMapLocation);
        }
    }
    //页面的定位服务
    class WeatherCallBack implements CallBack {

        @Override
        public void onSuccess(String response) {
            city.setText(Constants.LOCATION_POSITION_CITY);
            try {
                Log.i("天气","返回的天气"+response);
                JSONObject JsonResponse=new JSONObject(response);
                JSONObject JsonRetData=JsonResponse.getJSONObject("retData");

                date.setText(JsonRetData.getString("date"));
                time.setText(JsonRetData.getString("time"));
                String weatherInfo=JsonRetData.getString("weather");
                Log.i("天气","返回的天气"+weatherInfo);
                weather.setImageDrawable(WeatherControl.setWeatherType(weatherInfo));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onError(VolleyError error) {
            Log.i("网络请求","请求失败");
        }
    }
}
