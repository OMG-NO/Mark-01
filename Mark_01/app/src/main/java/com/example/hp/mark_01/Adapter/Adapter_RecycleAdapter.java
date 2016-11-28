package com.example.hp.mark_01.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hp.mark_01.DB_Project_Type.DB_Dish_Type;
import com.example.hp.mark_01.R;
import com.example.hp.mark_01.Util.OnRecyclerItemClickListener;
import com.example.hp.mark_01.Util.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by hp on 2016/8/18.
 */
public class Adapter_RecycleAdapter extends RecyclerView.Adapter<Adapter_RecycleAdapter.ViewHolder> implements View.OnClickListener {
    List<DB_Dish_Type> list;
    Context context;

    int lastPosition = -1;

    private OnRecyclerItemClickListener mItemClickLitener = null;

    public Adapter_RecycleAdapter(List<DB_Dish_Type> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public Adapter_RecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_menu_item, null);
        Adapter_RecycleAdapter.ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Adapter_RecycleAdapter.ViewHolder holder, int position) {
        DB_Dish_Type dish = list.get(position);
        holder.itemView.setTag(position);

        if (null != list) {
            holder.title.setText(dish.getTitle());
            holder.tags.setText(dish.getTags());
            holder.imtro.setText(dish.getImtro());
            //!!!!!!!!!
            try {
                Glide.with(context)
                        .load(StringUtil.getAlbumsString(new JSONArray(dish.getAlbums())))
                        .crossFade()
                        .placeholder(R.drawable.hongshaorou)
                        .centerCrop()
                        .fallback(R.mipmap.ic_launcher)
                        .into(holder.albums);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.i("收藏","收藏"+dish.isCollect());
            if (dish.isCollect()){
                holder.collect_indicator.setImageResource(R.drawable.collected);
            }
        }
        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Log.i("item动画","进入动画");
            Animation anim = AnimationUtils.loadAnimation(context, R.anim.recycle_item_enter_anim);
            viewToAnimate.startAnimation(anim);
            lastPosition=position;
        }
    }

    @Override
    public void onClick(View view) {
        if (mItemClickLitener != null) {
            mItemClickLitener.onItemClick(view, (Integer) view.getTag());
        }
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    public void setmItemClickLitener(OnRecyclerItemClickListener itemClickLitener) {
        this.mItemClickLitener = itemClickLitener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView colortags;
        TextView title;
        TextView tags;
        TextView imtro;
        ImageView albums;

        ImageView collect_indicator;

        public ViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.tags = (TextView) itemView.findViewById(R.id.tags);
            this.imtro = (TextView) itemView.findViewById(R.id.imtro);
            this.albums = (ImageView) itemView.findViewById(R.id.albums);
            this.collect_indicator= (ImageView) itemView.findViewById(R.id.iscollect);
        }

    }
}
