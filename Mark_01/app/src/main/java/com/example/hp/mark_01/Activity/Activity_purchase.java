package com.example.hp.mark_01.Activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 * Created by hp on 2016/8/29.
 */
public class Activity_purchase extends AppCompatActivity {

    SwipeMenuRecyclerView recycyle_menu;
    IngredientDao dao;
    List<Ingredient_Type> ingredientlist;

    Adapter_purchase adapter_purchase;

    Toolbar toolbar;
    ImageButton back_icon;
    TextView pageTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_purchase);
        dao = new IngredientDao(Activity_purchase.this);
        initView();
        initData();
        setToolbarandSystemBar();

        setRecycyle_menu();
    }

    private void initView() {
        recycyle_menu = (SwipeMenuRecyclerView) findViewById(R.id.recycle_menu);

        toolbar= (Toolbar) findViewById(R.id.unit_toolbar);
        back_icon= (ImageButton) findViewById(R.id.back_icon);
    }

    private void initData() {
        ingredientlist = new ArrayList<>();
        ingredientlist = dao.findAllIngredient();
    }

    private void setRecycyle_menu() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(Activity_purchase.this);
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
        adapter_purchase = new Adapter_purchase(ingredientlist, Activity_purchase.this);
        recycyle_menu.setAdapter(adapter_purchase);

    }

    //设置标题 和状态栏的样式
    public void setToolbarandSystemBar() {
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private SwipeMenuCreator MyswipeMenuCreator = new SwipeMenuCreator() {

        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int height = getResources().getDimensionPixelSize(R.dimen.item_height);
            int width = getResources().getDimensionPixelSize(R.dimen.item_width);
            int size = getResources().getDimensionPixelSize(R.dimen.item_textsize);

            SwipeMenuItem buyItem = new SwipeMenuItem(Activity_purchase.this)
                    .setBackgroundColor(getResources().getColor(R.color.purchase_menu_buy))
                    .setText("已购买")
                    .setTextColor(Color.WHITE)
                    .setHeight(height)
                    .setWidth(width);

            SwipeMenuItem stressItem = new SwipeMenuItem(Activity_purchase.this)
                    .setBackgroundColor(getResources().getColor(R.color.purchase_menu_stress))
                    .setText("强调")
                    .setTextColor(Color.WHITE)
                    .setHeight(height)
                    .setWidth(width);

            SwipeMenuItem delectItem = new SwipeMenuItem(Activity_purchase.this)
                    .setBackgroundColor(getResources().getColor(R.color.purchase_menu_delect))
                    .setText("删除")
                    .setTextColor(Color.WHITE)
                    .setHeight(height)
                    .setWidth(width);

            swipeRightMenu.addMenuItem(buyItem);
            swipeRightMenu.addMenuItem(stressItem);
            swipeRightMenu.addMenuItem(delectItem);

        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        dao.updateIngredient(ingredientlist);
    }

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
                Toast.makeText(Activity_purchase.this, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(Activity_purchase.this, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }

            if (menuPosition == 0) {
                ingredientlist.get(adapterPosition).setIsbuy(true);
                adapter_purchase.notifyDataSetChanged();
            } else if (menuPosition == 1) {
                ingredientlist.get(adapterPosition).setStress(true);
                adapter_purchase.notifyDataSetChanged();
            } else if (menuPosition == 2) {

                Log.i("删除操作", "删除Item位置" + adapterPosition);
                adapter_purchase.setRemoveItem(adapterPosition);

                adapter_purchase.setOnItemRemoveListner(new Adapter_purchase.OnItemRemoveListener() {
                    @Override
                    public void onItemRemove(View itemView, int itemPosition) {
                        itemView.setAnimation(getRemoveAnim());
                    }
                });

                Log.i("删除操作", "删除");

                adapter_purchase.notifyItemRemoved(adapterPosition);
                dao.removeIngredient(ingredientlist.get(adapterPosition));
                ingredientlist.remove(adapterPosition);
            }
        }
    };

    public Animation getRemoveAnim() {
        Log.i("删除操作", "添加删除动画");
        Animation animation = AnimationUtils.loadAnimation(Activity_purchase.this, R.anim.purchase_item_remove);
        return animation;
    }

}
