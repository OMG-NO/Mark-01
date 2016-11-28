package com.example.hp.mark_01.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hp.mark_01.R;
import com.example.hp.mark_01.Type.Steps_Type;
import com.example.hp.mark_01.Util.OnRecyclerItemClickListener;

import java.util.List;

/**
 * Created by hp on 2016/8/23.
 */
public class Adapter_StepsItem extends RecyclerView.Adapter<Adapter_StepsItem.ViewHolder> {
    List<Steps_Type> list;
    Context context;

    private OnRecyclerItemClickListener itemClickListener;

    public Adapter_StepsItem(List<Steps_Type> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setItemClickListener(OnRecyclerItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }

    @Override
    public Adapter_StepsItem.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.steps_item,null);
        Adapter_StepsItem.ViewHolder viewHolder=new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener!=null){
                    itemClickListener.onItemClick(view, (Integer) view.getTag());
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Adapter_StepsItem.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        Steps_Type step=list.get(position);


        if (null!=list){
            Log.i("页面适配器","开始加载页面 step");
            holder.step.setText(step.getStep());

            Glide.with(context)
                    .load(step.getPicUrl())
                    .crossFade()
                    .placeholder(R.drawable.step)
                    .centerCrop()
                    .fallback(R.mipmap.ic_launcher)
                    .into(holder.img);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView step;
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            this.step= (TextView) itemView.findViewById(R.id.step);
            this.img= (ImageView) itemView.findViewById(R.id.img);
        }
    }
}
