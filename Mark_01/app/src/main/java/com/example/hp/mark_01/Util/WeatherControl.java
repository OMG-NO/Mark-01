package com.example.hp.mark_01.Util;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.hp.mark_01.Application.NewsApplication;
import com.example.hp.mark_01.R;

/**
 * Created by hp on 2016/9/3.
 */
public class WeatherControl {

    public static Drawable setWeatherType(String weather) {
        Context context=NewsApplication.getInstance();
        Drawable drawable=context.getDrawable(R.drawable.weatherde);
        if ("晴".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather0);
        } else if ("多云".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather1);
        } else if ("阴".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather2);
        } else if ("阵雨".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather3);
        } else if ("雷阵雨".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather4);
        } else if ("雷阵雨伴有冰雹".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather5);
        } else if ("雨夹雪".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather6);
        } else if ("小雨".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather7);
        } else if ("中雨".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather8);
        } else if ("大雨".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather9);
        } else if ("暴雨".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather10);
        } else if ("大暴雨".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather11);
        } else if ("特大暴雨".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather12);
        } else if ("阵雪".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather13);
        } else if ("小雪".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather14);
        } else if ("中雪".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather15);
        } else if ("大雪".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather16);
        } else if ("暴雪".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather17);
        } else if ("雾".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather18);
        } else if ("冻雨".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather19);
        } else if ("沙尘暴".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather20);
        } else if ("小到中雨".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather21);
        } else if ("中到大雨".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather22);
        } else if ("大到暴雨".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather23);
        } else if ("暴雨到大暴雨".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather24);
        } else if ("大暴雨到特大暴雨".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather25);
        } else if ("小到中雪".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather26);
        } else if ("中到大雪".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather27);
        } else if ("大到暴雪".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather28);
        } else if ("浮尘".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather29);
        } else if ("扬沙".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather30);
        } else if ("强沙尘暴".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather31);
        } else if ("霾".equals(weather)) {
            drawable=context.getDrawable(R.drawable.weather32);
        }

        return drawable;
    }
}

