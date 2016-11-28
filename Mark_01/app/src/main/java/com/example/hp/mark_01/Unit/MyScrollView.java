package com.example.hp.mark_01.Unit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import com.example.hp.mark_01.Util.OnScrollHeightChangeListener;

/**
 * Created by hp on 2016/8/24.
 */
public class MyScrollView extends ScrollView {
    double totaleScrollDistance=0;

    OnScrollHeightChangeListener heightChangeListener;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setOnScrollHeightChangeListener(OnScrollHeightChangeListener heightChangeListener){
        this.heightChangeListener=heightChangeListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        heightChangeListener.onScrollchange(l, t, oldl, oldt);
    }
}
