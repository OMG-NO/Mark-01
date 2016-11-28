package com.example.hp.mark_01.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.example.hp.mark_01.Activity.Activity_Dish;
import com.example.hp.mark_01.Adapter.Adapter_RecycleAdapter;
import com.example.hp.mark_01.DB_Project_Type.DB_Dish_Type;
import com.example.hp.mark_01.R;
import com.example.hp.mark_01.Unit.MyRecyclerScrollListener;
import com.example.hp.mark_01.Util.OnRecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionFragment extends Fragment {
    View view;
    Toolbar toolbar;
    RecyclerView recycle_menu;

    List<DB_Dish_Type> collectDish_list;
    Adapter_RecycleAdapter adapter_recycle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_collection,null);
        initView();
        initDate();
        initRecycleMenu();
        return view;
    }

    private void initView(){
        toolbar= (Toolbar) view.findViewById(R.id.unit_toolbar);
        recycle_menu= (RecyclerView) view.findViewById(R.id.recycyle_menu);
    }

    private void initDate(){
        collectDish_list=new ArrayList<>();

    }

    public void initRecycleMenu(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle_menu.setLayoutManager(layoutManager);

        adapter_recycle=new Adapter_RecycleAdapter(collectDish_list,getContext());
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

    class RecyclerItemClickLitener implements OnRecyclerItemClickListener {

        @Override
        public void onItemClick(View view, int position) {
            Toast.makeText(getContext(),collectDish_list.get(position).getTitle(),Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getContext(),Activity_Dish.class);
            Bundle bundle=new Bundle();
            bundle.putParcelable("dishdata",collectDish_list.get(position));
            intent.putExtra("dishdata",bundle);
            startActivity(intent);
        }
    }
}
