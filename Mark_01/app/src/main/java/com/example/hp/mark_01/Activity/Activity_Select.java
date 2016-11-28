package com.example.hp.mark_01.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.mark_01.Adapter.Adapter_RecycleAdapter;
import com.example.hp.mark_01.DataManages.DataManages_Dish;
import com.example.hp.mark_01.Util.OnRecyclerItemClickListener;
import com.example.hp.mark_01.DB_Project_Type.DB_Dish_Type;
import com.example.hp.mark_01.R;
import com.example.hp.mark_01.Unit.MyRecyclerScrollListener;

import java.util.ArrayList;
import java.util.List;

public class Activity_Select extends AppCompatActivity {
    Toolbar toolbar;
    ImageButton back_icon;
    TextView pageTitle;

    RecyclerView recycle_menu;
    Adapter_RecycleAdapter adapter_recycle;

    List<DB_Dish_Type> dishlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__select);
        initView();
        setToolbarandSystemBar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
        initRecycleMenu();
        adapter_recycle.notifyDataSetChanged();
    }

    public void initView(){
        recycle_menu= (RecyclerView) findViewById(R.id.recycyle_menu);

        toolbar= (Toolbar) findViewById(R.id.unit_toolbar);
        back_icon= (ImageButton) findViewById(R.id.back_icon);
        pageTitle= (TextView) findViewById(R.id.page_title);

    }

    //设置标题 和状态栏的样式
    public void setToolbarandSystemBar(){
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.bringToFront();
        toolbar.setBackground(getDrawable(R.drawable.transparent_bg));

    }

    public void initRecycleMenu(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(Activity_Select.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle_menu.setLayoutManager(layoutManager);

        adapter_recycle=new Adapter_RecycleAdapter(dishlist,Activity_Select.this);
        recycle_menu.setAdapter(adapter_recycle);

        adapter_recycle.setmItemClickLitener(new RecyclerItemClickLitener());

        recycle_menu.addOnScrollListener(new MyRecyclerScrollListener() {
            @Override
            public void hideToolbar() {
                toolbar.animate()
                        .translationY(-toolbar.getHeight())
                        .setInterpolator(new AccelerateDecelerateInterpolator());
            }

            @Override
            public void ShowToolbar() {
                toolbar.animate()
                        .translationY(0)
                        .setInterpolator(new AccelerateDecelerateInterpolator());
            }
        });
    }

    public void initData(){
        dishlist=new ArrayList<>();
        dishlist= DataManages_Dish.getDish_List().get();
        Log.i("数据长度","软引用保存数据的长度"+dishlist.size());
    }

    class RecyclerItemClickLitener implements OnRecyclerItemClickListener{

        @Override
        public void onItemClick(View view, int position) {
            DB_Dish_Type willsendDish=dishlist.get(position);
            Toast.makeText(Activity_Select.this,willsendDish.getTitle()+willsendDish.isCollect(),Toast.LENGTH_SHORT).show();

            Intent intent=new Intent(Activity_Select.this,Activity_Dish.class);
            Bundle bundle=new Bundle();
            bundle.putParcelable("dishdata",willsendDish);
            intent.putExtra("dishdata",bundle);

            startActivity(intent);
        }
    }
}
