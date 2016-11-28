package com.example.hp.mark_01.Adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.CalendarContract;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.mark_01.DB_Project_Type.Ingredient_Type;
import com.example.hp.mark_01.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Created by hp on 2016/8/29.
 */
public class Adapter_purchase extends SwipeMenuAdapter<Adapter_purchase.ViewHolder> {
    List<Ingredient_Type> ingredientlist;
    Context context;

    int RemovePosition;
    boolean isDelect = true;

    OnItemRemoveListener itemRemoveListener;

    public void setOnItemRemoveListner(OnItemRemoveListener itemRemoveListener) {
        this.itemRemoveListener = itemRemoveListener;
    }

    public Adapter_purchase(List<Ingredient_Type> ingredientlist, Context context) {
        this.ingredientlist = ingredientlist;
        this.context = context;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(context).inflate(R.layout.purchase_ingredient_item, null);
    }

    @Override
    public ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {

        return new ViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ingredient_Type in = ingredientlist.get(position);
        holder.dosage.setText(in.getDosage());
        holder.variety.setText(in.getVariety());

        int menu_buy = holder.tagImg.getResources().getColor(R.color.purchase_menu_buy);
        int menu_stress = holder.tagImg.getResources().getColor(R.color.purchase_menu_stress);
        int menu_default = holder.tagImg.getResources().getColor(R.color.purchase_menu_default);

        if (in.isbuy()) {
            holder.tagImg.setBackgroundColor(menu_buy);
        } else if (in.isStress()) {
            holder.tagImg.setBackgroundColor(menu_stress);
        } else {
            holder.tagImg.setBackgroundColor(menu_default);
        }
        if (itemRemoveListener != null && position == (RemovePosition) && isDelect) {
            Log.i("删除操作", "adapter删除Item位置" + RemovePosition + "   " + position);
            itemRemoveListener.onItemRemove(holder.itemView, position);
            isDelect=false;
        }
    }

    public void setRemoveItem(int RemovePosition) {
        this.RemovePosition = RemovePosition;
        isDelect=true;
    }

    @Override
    public int getItemCount() {
        return ingredientlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView variety;
        TextView dosage;
        ImageView tagImg;

        public ViewHolder(View itemView) {
            super(itemView);
            variety = (TextView) itemView.findViewById(R.id.variety);
            dosage = (TextView) itemView.findViewById(R.id.dosage);
            tagImg = (ImageView) itemView.findViewById(R.id.tagImg);
        }
    }

    public interface OnItemRemoveListener {
        void onItemRemove(View itemView, int itemPosition);
    }
}
