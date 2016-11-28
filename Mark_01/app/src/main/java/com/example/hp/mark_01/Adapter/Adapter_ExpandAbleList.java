package com.example.hp.mark_01.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.hp.mark_01.DB_Project_Type.DB_DishStyle_Type;
import com.example.hp.mark_01.DB_Project_Type.Parent_DishStyle_Type;
import com.example.hp.mark_01.R;

import java.util.List;

/**
 * Created by hp on 2016/8/26.
 */
public class Adapter_ExpandAbleList extends BaseExpandableListAdapter {
    List<List<DB_DishStyle_Type>> childlistgroup;
    List<Parent_DishStyle_Type> parentlist;
    Context context;

    public Adapter_ExpandAbleList(List<List<DB_DishStyle_Type>> childlistgroup, List<Parent_DishStyle_Type> parentlist, Context context) {
        this.childlistgroup = childlistgroup;
        this.parentlist = parentlist;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return parentlist.size();
    }

    @Override
    public int getChildrenCount(int i) {
        List<DB_DishStyle_Type> childgroup=childlistgroup.get(i);
        return childgroup.size();
    }

    @Override
    public Object getGroup(int i) {
        return childlistgroup.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return childlistgroup.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        parentHolder parentHolder;
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.expandable_parent_item,null);
            parentHolder=new parentHolder();
            parentHolder.title= (TextView) view.findViewById(R.id.title);

            view.setTag(parentHolder);
        }else{
            parentHolder= (Adapter_ExpandAbleList.parentHolder) view.getTag();
        }
        Parent_DishStyle_Type parentdish=parentlist.get(i);
        parentHolder.title.setText(parentdish.getName());
//        parentHolder.parentId.setText(parentdish.getID());
        Log.i("数据监测","(parentdish.getID())"+(parentdish.getID()));

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        List<DB_DishStyle_Type> dishlist =childlistgroup.get(i);

        parentHolder parentHolder;
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.expandable_parent_item,null);
            parentHolder=new parentHolder();
            parentHolder.title= (TextView) view.findViewById(R.id.title);

            view.setTag(parentHolder);
        }else{
            parentHolder= (Adapter_ExpandAbleList.parentHolder) view.getTag();
        }

        DB_DishStyle_Type dishstyle=dishlist.get(i1);
        parentHolder.title.setText(dishstyle.getTitle());
//        parentHolder.parentId.setText(dishstyle.getParentid());
        Log.i("数据监测","(parentdish.getID())"+(dishstyle.getId()));
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }


    class parentHolder{
        TextView title;
        TextView parentId;
    }
}
