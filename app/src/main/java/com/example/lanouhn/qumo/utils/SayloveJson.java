package com.example.lanouhn.qumo.utils;

import com.example.lanouhn.qumo.constans.Funny;
import com.example.lanouhn.qumo.constans.Goddes;
import com.example.lanouhn.qumo.constans.Saylove;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 解析Json数据
 * 表白N+1
 */
public class SayloveJson {

    public static List<Saylove> getsayloveList(String json) {
        List<Saylove> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            JSONArray jsonArray = jsonObject1.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.optJSONObject(i);
                JSONObject j = object.getJSONObject("group");


                Saylove g = new Saylove();

                g.setContent(j.optString("text"));
                g.setBury_count(j.optString("bury_count"));
                g.setDigg_count(j.optString("digg_count"));
                g.setComment_count(j.optString("comment_count"));
                g.setShare_count(j.optString("share_count"));

                if (j.has("large_image")) {
                    JSONObject url = j.optJSONObject("large_image");
                    Saylove.large_image urllarge = new Saylove.large_image();

//                JSONObject image=url.optJSONObject("url_list");
                    JSONArray ul = url.optJSONArray("url_list");
                    List<Saylove.large_image.list_url> list_urls = new ArrayList<>();

                    for (int z = 0; z < ul.length(); z++) {
                        Saylove.large_image.list_url imageurl = new Saylove.large_image.list_url();
                        JSONObject t = ul.optJSONObject(z);
                        imageurl.setUrl(t.optString("url"));

                        list_urls.add(imageurl);
                    }

                    urllarge.setList_url(list_urls);

                    g.setLarge_image(urllarge);
                }

                JSONObject user = j.getJSONObject("user");
                Saylove.user user1 = new Saylove.user();
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

}
