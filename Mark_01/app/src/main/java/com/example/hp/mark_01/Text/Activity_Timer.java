package com.example.hp.mark_01.Text;

import android.app.Activity;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.hp.mark_01.MainActivity;
import com.example.hp.mark_01.R;

public class Activity_Timer extends AppCompatActivity {
    TextView timer;
    MyCount count;

    /**
     * 页面关闭时,后台不能继续运行
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__timer);
        timer= (TextView) findViewById(R.id.showtime);
        count=new MyCount(50000,1000,timer);

    }
    public void starttime(View view){
        count.start();
    }

}
