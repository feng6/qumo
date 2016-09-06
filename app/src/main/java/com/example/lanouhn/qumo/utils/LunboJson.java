package com.example.lanouhn.qumo.utils;

import com.example.lanouhn.qumo.constans.lunboImage;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


/**
 * 解析Json数据
 */
public class LunboJson {

    /**
     * @param json json字符串
     * @return 轮播图
     */
    public static List<lunboImage> getlunboList(String json) {
        List<lunboImage> list = new ArrayList<>();
        try {


            JSONObject jsonObject = new JSONObject(json);

            JSONObject jsonObject1= jsonObject.getJSONObject("data");
            JSONArray jsonArray = jsonObject1.getJSONArray("list");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject j = jsonArray.getJSONObject(i);

                lunboImage m = new lunboImage();
                m.setUrl(j.getString("url"));

                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
