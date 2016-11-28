package com.example.hp.mark_01.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hp.mark_01.Activity.Activity_purchase;
import com.example.hp.mark_01.Adapter.Adapter_purchase;
import com.example.hp.mark_01.DB_Project_Type.Ingredient_Type;
import com.example.hp.mark_01.DataBaseUnit.IngredientDao;
import com.example.hp.mark_01.R;
import com.example.hp.mark_01.Unit.ListViewDecoration;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;

import java.util.ArrayList;
import java.util.List;

public class PurchaseFragment extends Fragment {
    View view;
    SwipeMenuRecyclerView recycyle_menu;
    IngredientDao dao;
    List<Ingredient_Type> ingredientlist;

    Adapter_purchase adapter_purchase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_purchase, null);
        dao = new IngredientDao(getContext());
        initView();
        initData();

        setRecycyle_menu();
        return view;
    }

    private void initView() {
        recycyle_menu = (SwipeMenuRecyclerView) view.findViewById(R.id.recycle_menu);
    }

    private void initData() {
        ingredientlist = new ArrayList<>();
        ingredientlist = dao.findAllIngredient();
    }

    private void setRecycyle_menu() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycyle_menu.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        recycyle_menu.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
        recycyle_menu.addItemDecoration(new ListViewDecoration());// 添加分割线。
        recycyle_menu.setLayoutManager(layoutManager);

        // 为SwipeRecyclerView的Item创建菜单就两句话，不错就是这么简单：
        // 设置菜单创建器。
        recycyle_menu.setSwipeMenuCreator(MyswipeMenuCreator);
        // 设置菜单Item点击监听。
        recycyle_menu.setSwipeMenuItemClickListener(menuItemClickListener);

//        mMenuAdapter = new MenuAdapter(mStrings);
        adapter_purchase=new Adapter_purchase(ingredientlist, getContext());
        recycyle_menu.setAdapter(adapter_purchase);

    }


    private SwipeMenuCreator MyswipeMenuCreator =new SwipeMenuCreator() {

        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            SwipeMenuItem addItem = new SwipeMenuItem(getContext())
                    .setBackgroundColor(getResources().getColor(R.color.colorAccent))
                    .setText("删除")
                    .setTextColor(Color.WHITE)
                    .setWidth(200);
            SwipeMenuItem addItem1 = new SwipeMenuItem(getContext())
                    .setBackgroundColor(getResources().getColor(R.color.colorAccent))
                    .setText("删除")
                    .setTextColor(Color.WHITE)
                    .setWidth(200);
            SwipeMenuItem addItem2 = new SwipeMenuItem(getContext())
                    .setBackgroundColor(getResources().getColor(R.color.colorAccent))
                    .setText("删除")
                    .setTextColor(Color.WHITE)
                    .setWidth(200);

            swipeRightMenu.addMenuItem(addItem);
            swipeRightMenu.addMenuItem(addItem1);
            swipeRightMenu.addMenuItem(addItem2);
        }
    };

    /**
     * 菜单点击监听。
     */
    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        /**
         * Item的菜单被点击的时候调用。
         * @param closeable       closeable. 用来关闭菜单。
         * @param adapterPosition adapterPosition. 这个菜单所在的item在Adapter中position。
         * @param menuPosition    menuPosition. 这个菜单的position。比如你为某个Item创建了2个MenuItem，那么这个position可能是是 0、1，
         * @param direction       如果是左侧菜单，值是：SwipeMenuRecyclerView#LEFT_DIRECTION，如果是右侧菜单，值是：SwipeMenuRecyclerView#RIGHT_DIRECTION.
         */
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                Toast.makeText(getContext(), "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(getContext(), "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }
        }
    };

}
