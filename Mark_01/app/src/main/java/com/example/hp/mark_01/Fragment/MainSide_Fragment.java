package com.example.hp.mark_01.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hp.mark_01.R;

/**
 * Created by hp on 2016/8/28.
 */
public class MainSide_Fragment extends Fragment implements View.OnClickListener {
    View view;
    ImageView search, collection, purchase;
    OnImageClickListener onImageClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_sidebar, null);
        initView();
        setClickListener();

        return view;
    }

    public void setOnImageClickListener(OnImageClickListener onImageClickListener){
        this.onImageClickListener=onImageClickListener;
    }

    private void initView(){
        search= (ImageView) view.findViewById(R.id.search);
        collection= (ImageView) view.findViewById(R.id.collection);
        purchase= (ImageView) view.findViewById(R.id.purchase);
    }

    private void setClickListener(){
        search.setOnClickListener(this);
        collection.setOnClickListener(this);
        purchase.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search:
                onImageClickListener.imageClickListener(0);
                break;
            case R.id.collection:
                onImageClickListener.imageClickListener(1);
                break;
            case R.id.purchase:
                onImageClickListener.imageClickListener(3);
                break;
        }
    }

    public  interface OnImageClickListener{
        public  void imageClickListener(int position);      //通过传递imageview的位置判断点击了哪一个
    }
}
