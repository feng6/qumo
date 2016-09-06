package com.example.lanouhn.qumo.utils;

import java.io.InputStream;

/**
 * Created by lanouhn on 2016/8/31.
 */
public class CommonUtil {
    /**
     * 解码GIF图片
     *
     * @param is
     * @return
     */
    public static GifHelper.GifFrame[] getGif(InputStream is) {
        GifHelper gifHelper = new GifHelper();
        if (GifHelper.STATUS_OK == gifHelper.read(is)) {
            return gifHelper.getFrames();
        }
        return null;
    }
    /**
     * 判断图片是否为GIF格式
     * @param is
     * @return
     */
    public static boolean isGif(InputStream is) {
        GifHelper gifHelper = new GifHelper();
        return gifHelper.isGif(is);
    }
}
