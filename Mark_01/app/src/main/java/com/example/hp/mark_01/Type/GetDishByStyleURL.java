package com.example.hp.mark_01.Type;

import com.example.hp.mark_01.Application.Constants;

/**
 * Created by hp on 2016/8/26.
 */
public class GetDishByStyleURL {
    private int cid;
    private String rn = "3";
    private String pn = "1";


    public String getGETDishByNameURL() {
        String URL = Constants.QUERY_BYSTYLE_URL +
                "?key=" + Constants.key + "&cid=" + cid + "&rn=" + rn + "&pn=" + pn;
        return URL;
    }

    public void setCid(int menu) {
        this.cid = menu;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }
}
