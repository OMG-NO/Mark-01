package com.example.hp.mark_01.Type;

import com.example.hp.mark_01.Application.Constants;

/**
 * Created by hp on 2016/8/26.
 */
//处理将要请求的网络地址 GET 方法
public class GetDishByNameURL {
    private String menu;
    private String rn = "3";
    private String pn = "1";


    public String getGETDishByNameURL() {
        String URL = Constants.QUERY_BYNAME_URL +
                "?key=" + Constants.key + "&menu=" + menu + "&rn=" + rn + "&pn=" + pn;
        return URL;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }
}
