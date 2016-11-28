package com.example.hp.mark_01.Text;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.hp.mark_01.R;
import com.example.hp.mark_01.Util.StringUtil;

public class LocationActivity extends AppCompatActivity implements View.OnClickListener {
    Button button;
    TextView textView;

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        button = (Button) findViewById(R.id.locationbtn);
        textView = (TextView) findViewById(R.id.flag);
        initLocation();
        button.setOnClickListener(this);
    }

    private void initLocation() {
        //初始化 client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        //设置定位参数
        locationClient.setLocationOption(getLocationOption());
        //设置定位监听
        locationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    String result=Utils.getLocationStr(aMapLocation);
                    textView.setText(result);
                }else{
                    textView.setText("定位失败");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        textView.setText("正在定位");
        locationClient.startLocation();
    }

    private AMapLocationClientOption getLocationOption() {
        //设置定位参数
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        //高精度定位
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置gps优先
        mOption.setGpsFirst(true);
        //设置网络请求超时时间 默认 30秒
        mOption.setHttpTimeOut(30000);
        //设置定位事件间隔  默认2秒
        mOption.setInterval(2000);
        //设置 是否返回逆地理地理信息 默认true
        mOption.setNeedAddress(true);
        //设置 是否是单次定位
        mOption.setOnceLocation(false);
        //设置是否优先返回GPS定位结果,如果30秒内GPS没有返回定位,则进行网络定位
        mOption.setGpsFirst(false);
        return mOption;

    }

}
