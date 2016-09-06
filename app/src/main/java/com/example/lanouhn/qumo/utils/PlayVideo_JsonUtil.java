package com.example.lanouhn.qumo.utils;



import com.example.lanouhn.qumo.constans.playVideo.PlayVideo;
import com.example.lanouhn.qumo.constans.playVideo.playLines;
import com.example.lanouhn.qumo.constans.playVideo.urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanouhn on 16/8/22.
 */
public class PlayVideo_JsonUtil {

    public static PlayVideo getData(String json){

        PlayVideo playVideo = new PlayVideo();

        try {
            JSONObject jsonObject = new JSONObject(json);

            playVideo.setLiveUrl(jsonObject.optString("liveUrl"));

            JSONArray playLinesArray = jsonObject.optJSONArray("playLines");
            List<playLines> playLinesList = new ArrayList<>();
            for (int i = 0; i < playLinesArray.length(); i++) {
                playLines playLines = new playLines();
                JSONObject object = playLinesArray.optJSONObject(i);

                playLines.setLineType(object.optInt("lineType"));
                playLines.setPlayLiveStreamType(object.optInt("playLiveStreamType"));

                JSONArray urlsArray = object.optJSONArray("urls");
                List<urls> urlsList = new ArrayList<>();
                for (int x = 0; x < urlsArray.length(); x++) {
                    urls urls = new urls();
                    JSONObject jbjt = urlsArray.optJSONObject(x);

                    urls.setSecurityUrl(jbjt.optString("securityUrl"));
                    urls.setResolution(jbjt.optString("resolution"));
                    urls.setExt(jbjt.optString("ext"));
                    urls.setRateLevel(jbjt.optInt("rateLevel"));

                    urlsList.add(urls);
                }
                playLines.setUrls(urlsList);

                playLinesList.add(playLines);
            }

            playVideo.setPlayLines(playLinesList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return playVideo;
    }
}
