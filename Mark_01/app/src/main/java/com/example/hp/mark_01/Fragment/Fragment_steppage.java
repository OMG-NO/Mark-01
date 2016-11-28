package com.example.hp.mark_01.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hp.mark_01.R;
import com.example.hp.mark_01.Type.Steps_Type;

import org.w3c.dom.Text;

public class Fragment_steppage extends Fragment {
    TextView step_content;
    ImageView step_img;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_steppage, null);
        step_content = (TextView) view.findViewById(R.id.step_content);
        step_img = (ImageView) view.findViewById(R.id.step_img);

        Steps_Type step = getArguments().getParcelable("step");
        if (step != null) {
            Log.i("数据传递", step.getStep());
            step_content.setText(step.getStep());
        }
        Glide.with(getActivity())
                .load(step.getPicUrl())
                .crossFade()
                .placeholder(R.drawable.step)
                .fallback(R.mipmap.ic_launcher)
                .into(step_img);

        return view;
    }

}
