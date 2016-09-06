package com.example.lanouhn.qumo.utils;

import com.example.lanouhn.qumo.constans.Funny;
import com.example.lanouhn.qumo.constans.Saylove;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 解析Json数据
 * 好笑不
 */
public class FunnyJson {

    public static List<Funny> getfunnyList(String json) {
        List<Funny> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.optJSONObject(i);
                JSONObject j = object.getJSONObject("group");


                Funny g = new Funny();

                g.setContent(j.optString("text"));


                JSONObject user = j.getJSONObject("user");
                Funny.user user1 = new Funny.user();
                user1.setAvatar_url(user.getString("avatar_url"));
                user1.setName(user.optString("name"));

                g.setUser(user1);

                if (j.has("large_image")) {
                    JSONObject url = j.optJSONObject("large_image");
                    Funny.large_image urllarge = new Funny.large_image();

                    JSONArray ul = url.optJSONArray("url_list");
                    List<Funny.large_image.list_url> list_urls = new ArrayList<>();

                    for (int z = 0; z < ul.length(); z++) {
                        Funny.large_image.list_url imageurl = new Funny.large_image.list_url();
                        JSONObject t = ul.optJSONObject(z);
                        imageurl.setUrl(t.optString("url"));

                        list_urls.add(imageurl);
                    }

                    urllarge.setList_url(list_urls);


                    g.setLarge_image(urllarge);
                }

                list.add(g);
            }





        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
