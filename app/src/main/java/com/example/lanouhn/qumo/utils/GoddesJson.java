package com.example.lanouhn.qumo.utils;

import com.example.lanouhn.qumo.constans.Goddes;
import com.example.lanouhn.qumo.constans.lunboImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 解析Json数据
 * 女神
 */
public class GoddesJson {

    public static List<Goddes> getgodessList(String json) {
        List<Goddes> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            JSONArray jsonArray = jsonObject1.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.optJSONObject(i);
                JSONObject j = object.getJSONObject("group");


                Goddes g = new Goddes();

                g.setContent(j.optString("text"));
                g.setBury_count(j.optString("bury_count"));
                g.setDigg_count(j.optString("digg_count"));
                g.setComment_count(j.optString("comment_count"));
                g.setShare_count(j.optString("share_count"));
                g.setLarge_image(getLargeImage(j.getJSONObject("large_image")));

                JSONObject user = j.getJSONObject("user");
                Goddes.user user1 = new Goddes.user();
                user1.setAvatar_url(user.getString("avatar_url"));
                user1.setName(user.optString("name"));

                g.setUser(user1);

                list.add(g);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private static Goddes.large_image getLargeImage(JSONObject large_image) {
        Goddes.large_image l = new Goddes.large_image();

        try {
            JSONArray jsonArray = large_image.getJSONArray("url_list");

            JSONObject jo = jsonArray.getJSONObject(0);
            l.setUrl(jo.getString("url"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return l;
    }
}
