package com.example.lanouhn.qumo.Appcation;

import android.app.Application;
import android.content.Context;

import com.example.lanouhn.qumo.constans.Url;
import com.example.lanouhn.qumo.im.LiveKit;

import io.rong.imlib.RongIMClient;
import io.vov.vitamio.utils.Log;

/**
 * Created by lanouhn on 2016/8/27.
 */
public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 开启融云消息
         */
        context = this;
        //直播聊天室
        LiveKit.init(context, "8brlm7ufrrfm3");

        LiveKit.connect(Url.TOKEN, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {

            }

            @Override
            public void onSuccess(String s) {

                Log.i("aaa", "sadsadasd");
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });

    }

    public static Context getContext() {
        return context;
    }

}