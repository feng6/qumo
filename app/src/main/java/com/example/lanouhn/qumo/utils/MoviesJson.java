package com.example.lanouhn.qumo.utils;

import com.example.lanouhn.qumo.constans.Goddes;
import com.example.lanouhn.qumo.constans.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 解析Json数据
 * 情感视频
 */
public class MoviesJson {

    public static List<Movies> getmoviesList(String json) {
        List<Movies> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            JSONArray jsonArray = jsonObject1.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.optJSONObject(i);
                JSONObject j = object.getJSONObject("group");


                Movies g = new Movies();

                g.setContent(j.optString("text"));
                g.setBury_count(j.optString("bury_count"));
                g.setDigg_count(j.optString("digg_count"));
                g.setComment_count(j.optString("comment_count"));
                g.setShare_count(j.optString("share_count"));
                g.setUrl_mp4(j.optString("mp4_url"));
                g.setLarge_cover(getLargeCover(j.getJSONObject("large_cover")));

                JSONObject user = j.getJSONObject("user");
                Movies.user user1 = new Movies.user();
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

    private static Movies.large_cover getLargeCover(JSONObject large_cover) {
        Movies.large_cover l = new Movies.large_cover();

        try {
            JSONArray jsonArray = large_cover.getJSONArray("url_list");

            JSONObject jo = jsonArray.getJSONObject(0);
            l.setUrl(jo.getString("url"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return l;
    }
}
