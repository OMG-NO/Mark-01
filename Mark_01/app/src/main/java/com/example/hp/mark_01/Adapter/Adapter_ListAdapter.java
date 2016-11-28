package com.example.hp.mark_01.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hp.mark_01.R;
import com.example.hp.mark_01.DB_Project_Type.Ingredient_Type;

import java.util.List;

/**
 * Created by hp on 2016/8/22.
 */
public class Adapter_ListAdapter extends BaseAdapter {
    List<Ingredient_Type> list;
    Context context;

    public Adapter_ListAdapter(List<Ingredient_Type> list,Context context) {
        this.list = list;
        this.context=context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater=LayoutInflater.from(context);
        ViewHolder viewHolder;
        Ingredient_Type in=list.get(i);
        if (view==null){
            viewHolder=new ViewHolder();
            view=inflater.inflate(R.layout.ingredient_item,null);
            viewHolder.title= (TextView) view.findViewById(R.id.ingredient);
            viewHolder.dosage= (TextView) view.findViewById(R.id.dosage);

            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.title.setText(in.getVariety());
        viewHolder.dosage.setText(in.getDosage());

        return view;
    }
}
class ViewHolder{
    TextView title;
    TextView dosage;
}
