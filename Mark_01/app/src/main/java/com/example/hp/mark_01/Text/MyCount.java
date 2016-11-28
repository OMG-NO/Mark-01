package com.example.hp.mark_01.Text;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by hp on 2016/8/20.
 */
public class MyCount extends CountDownTimer {
    TextView textView;
    public MyCount(long millisInFuture, long countDownInterval,TextView textView) {
        super(millisInFuture, countDownInterval);
        this.textView=textView;
    }

    @Override
    public void onTick(long l) {
        long myhour = (l / 1000) / 3600;
        long myminute = ((l / 1000) - myhour * 3600) / 60;
        long mysecond = l / 1000 - myhour * 3600
                - myminute * 60;
        textView.setText("剩余时间" + myhour + ":" + myminute + ":" + mysecond);
    }

    @Override
    public void onFinish() {
        textView.setText("剩余时间0：0：0");
    }
}
