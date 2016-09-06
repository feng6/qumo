package com.example.lanouhn.qumo.utils;

import android.util.Log;

import com.example.lanouhn.qumo.constans.Funny;
import com.example.lanouhn.qumo.constans.Zhibo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanouhn on 2016/8/24.
 * 直播解析
 */
public class ZhiboJson {

    public static List<Zhibo> getzhiboList(String json) {
        List<Zhibo> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonObject1=jsonObject.getJSONObject("data");
            JSONObject jsonObject2=jsonObject1.getJSONObject("streams");
            JSONArray jarr = jsonObject2.getJSONArray("items");

            Log.e("aaaaa",jarr.length()+"");
            for (int i = 0; i < jarr.length(); i++) {
              JSONObject j= jarr.getJSONObject(i);
                Zhibo g = new Zhibo();
                g.setPreview(j.getString("preview"));
                g.setViewers(j.getString("viewers"));

                //channel
                JSONObject channel = j.getJSONObject("channel");
                Zhibo.channel channelq = new Zhibo.channel();
                channelq.setId(channel.getInt("id"));
                channelq.setAvatar(channel.getString("avatar"));
                channelq.setName(channel.getString("name"));
                g.setChannel(channelq);


                //user
                JSONObject user = j.getJSONObject("user");
                Zhibo.user user1 = new Zhibo.user();
                user1.setAvatar(user.getString("avatar"));
                user1.setName(user.getString("name"));

                g.setUser(user1);

                //tag
                JSONObject tog = j.getJSONObject("tag");
                Zhibo.tag tog1 = new Zhibo.tag();
                tog1.setTag(tog.getString("tag"));
                g.setTag(tog1);

                list.add(g);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
