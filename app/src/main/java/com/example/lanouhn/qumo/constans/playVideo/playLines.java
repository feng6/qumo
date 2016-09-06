package com.example.lanouhn.qumo.constans.playVideo;

import java.util.List;

/**
 * Created by lanouhn on 16/8/22.
 */
public class playLines {
    private int lineType;
    private int playLiveStreamType;
    private List<urls> urls;

    public int getLineType() {
        return lineType;
    }

    public void setLineType(int lineType) {
        this.lineType = lineType;
    }

    public int getPlayLiveStreamType() {
        return playLiveStreamType;
    }

    public void setPlayLiveStreamType(int playLiveStreamType) {
        this.playLiveStreamType = playLiveStreamType;
    }

    public List<com.example.lanouhn.qumo.constans.playVideo.urls> getUrls() {
        return urls;
    }

    public void setUrls(List<com.example.lanouhn.qumo.constans.playVideo.urls> urls) {
        this.urls = urls;
    }
}
