package com.example.hp.mark_01.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.mark_01.R;
import com.example.hp.mark_01.DB_Project_Type.Parent_DishStyle_Type;
import com.example.hp.mark_01.Util.OnRecyclerItemClickListener;

import java.util.List;

/**
 * Created by hp on 2016/8/19.
 */
public class Adapter_GridRecycle extends RecyclerView.Adapter<Adapter_GridRecycle.ViewHolder> {
    List<Parent_DishStyle_Type> list;
    Context context;

    OnRecyclerItemClickListener onItemClicklistener;

    public Adapter_GridRecycle(List<Parent_DishStyle_Type> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recommend_item,null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    public void setOnItemClicklistener(OnRecyclerItemClickListener onItemClicklistener){
        this.onItemClicklistener=onItemClicklistener;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Parent_DishStyle_Type dishtag=list.get(position);
        if (holder!=null){
            holder.titel.setText(dishtag.getName());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClicklistener.onItemClick(holder.itemView,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView showImage;
        TextView titel;

        public ViewHolder(View itemView) {
            super(itemView);
            this.showImage= (ImageView) itemView.findViewById(R.id.showImage);
            this.titel= (TextView) itemView.findViewById(R.id.title);
        }
    }
}
