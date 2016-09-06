package com.example.lanouhn.qumo.constans.playVideo;

import java.util.List;

/**
 * Created by lanouhn on 16/8/22.
 */
public class PlayVideo {

    private String liveUrl;
    private int inbandwidth;
    private int isTransfer;
    private int pushLiveStreamType;
    private int liveSourceType;
    private int defaultLine;
    private int defaultRateLevel;

    private List<playLines> playLines;

    public String getLiveUrl() {
        return liveUrl;
    }

    public void setLiveUrl(String liveUrl) {
        this.liveUrl = liveUrl;
    }

    public int getInbandwidth() {
        return inbandwidth;
    }

    public void setInbandwidth(int inbandwidth) {
        this.inbandwidth = inbandwidth;
    }

    public int getIsTransfer() {
        return isTransfer;
    }

    public void setIsTransfer(int isTransfer) {
        this.isTransfer = isTransfer;
    }

    public int getPushLiveStreamType() {
        return pushLiveStreamType;
    }

    public void setPushLiveStreamType(int pushLiveStreamType) {
        this.pushLiveStreamType = pushLiveStreamType;
    }

    public int getLiveSourceType() {
        return liveSourceType;
    }

    public void setLiveSourceType(int liveSourceType) {
        this.liveSourceType = liveSourceType;
    }

    public int getDefaultLine() {
        return defaultLine;
    }

    public void setDefaultLine(int defaultLine) {
        this.defaultLine = defaultLine;
    }

    public int getDefaultRateLevel() {
        return defaultRateLevel;
    }

    public void setDefaultRateLevel(int defaultRateLevel) {
        this.defaultRateLevel = defaultRateLevel;
    }

    public List<com.example.lanouhn.qumo.constans.playVideo.playLines> getPlayLines() {
        return playLines;
    }

    public void setPlayLines(List<com.example.lanouhn.qumo.constans.playVideo.playLines> playLines) {
        this.playLines = playLines;
    }
}
