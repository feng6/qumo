package com.example.lanouhn.qumo.im.utils;


import com.example.lanouhn.qumo.Appcation.App;

public class CommonUtil {

    public static int dip2px(float dpValue) {
        float scale = App.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
