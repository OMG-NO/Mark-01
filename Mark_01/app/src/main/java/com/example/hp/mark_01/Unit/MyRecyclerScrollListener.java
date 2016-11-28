package com.example.hp.mark_01.Unit;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by hp on 2016/8/24.
 */
public abstract class MyRecyclerScrollListener extends RecyclerView.OnScrollListener {
    private static final int Scroll_Distance = 50;
    private int totalScrollDistance;
    private boolean isShow = true;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int firstVisableItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        if (firstVisableItem == 0) {
            return;
        }
        if ((dy > 0 && isShow) || (dy < 0 || !isShow)) {
            totalScrollDistance += dy;
        }
        if (firstVisableItem == 1) {
            ShowToolbar();
            isShow = true;
            totalScrollDistance = 0;
        }
        if (totalScrollDistance > Scroll_Distance && isShow) {
            hideToolbar();
            isShow = false;
            totalScrollDistance = 0;
        }
        if (totalScrollDistance < -Scroll_Distance && !isShow) {
            ShowToolbar();
            isShow = true;
            totalScrollDistance = 0;
        }

    }

    public abstract void hideToolbar();

    public abstract void ShowToolbar();
}
