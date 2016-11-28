package com.example.hp.mark_01.Text;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;
import com.example.hp.mark_01.MainActivity;
import com.example.hp.mark_01.R;

public class Activity_Json extends AppCompatActivity {
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__json);
        text= (TextView) findViewById(R.id.showdish);

    }
    public void showtext(View view){
    }
}
